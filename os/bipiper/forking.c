#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <bufio.h>
#include <netdb.h>
#include <errno.h>
#include <sys/stat.h>
#include <string.h>

void error_message(const char* message) {
    perror(message);
    exit(1);
}

const int BUF_SIZE = 4096;

int make_server(int port) {
    /* Create an endpoint. sock - descriptor
     * AF_INET - domain for IPv4. 
     * SOCK_STREAM - type of socket, two_way byte streams
     * IPPROTO_TCP - protocol
     * */
    int sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (sock == -1)
        error_message("socket");

    int one = 1;
    /* Set options for the socket
     * SOL_SOCKET - API level, SO_REUSEADDR - optname, 
     * optval - should be nonzero, optlen - size of the buffer
     * */
    if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &one, sizeof(int)) == -1)
        error_message("setsockopt");

    /*
     * struct addrinfo { 
     *  ai_flags, ai_family, ai_socktype, ai_protocol, ai_addrlen,
     *  *ai_addr, *ai_canonname, *ai_next
     * }
     * */
    struct addrinfo* res;
    /* The hints specifies criteria for selecting the socket address
     * structures returned in the list pointes to by res
     * */
    struct addrinfo hints;

    memset(&hints, 0, sizeof(struct addrinfo));

    hints.ai_flags = AI_V4MAPPED | AI_ADDRCONFIG;
    hints.ai_family = AF_INET;
    hints.ai_protocol = SOCK_STREAM;
    hints.ai_addrlen = 0;
    hints.ai_addr = 0;
    hints.ai_canonname = 0;
    hints.ai_next = 0;
    /* Node - localhost, service - NULL
     * */
    if (getaddrinfo("localhost", 0, &hints, &res) != 0)
        error_message("getaddrinfo");

    struct sockaddr_in addr;
    memset(&addr, 0, sizeof(struct sockaddr_in));
    memcpy(&addr, res->ai_addr, res->ai_addrlen);
    freeaddrinfo(res);
    /* Convert host byte order to network byte order */
    addr.sin_port = htons(port);

    /* Assign the address to the socket*/
    if (bind(sock, (struct sockaddr*)&addr, sizeof(addr)) == -1)
        error_message("bind");

    /* Mark the socket as a passive socket */
    if (listen(sock, 1) == -1)
        error_message("listen");
    return sock;
}

void run_client(int src, int dest) {
    struct buf_t* buf = buf_new(BUF_SIZE);
    ssize_t nr = 0;
    ssize_t nw = 0;
    while(1) {
        /* Send message */
        nr = buf_fill(src, buf, 1);
        nw = buf_flush(dest, buf, buf_size(buf));
        if (nw < 0 || nr < 0)
            break;
        if (nr <= 0)
            break;
    }
    exit(0);
}

int check_fd(int fd) {
    if (fd < 0) {
        if (errno == EINTR)
            return 1;
        else error_message("accept");
    }
    return 0;
}

int main(int argc, char* argv[]) {
    int sock1 = make_server(atoi(argv[1]));
    int sock2 = make_server(atoi(argv[2]));
    if (sock1 < 0 || sock2 < 0)
        exit(1);
    struct sockaddr_in client1, client2;
    socklen_t s = sizeof(struct sockaddr_in);
    while(1) {
        /* Extract the first connection request on the queue for 
         * the listening socket, create a new connected socket 
         * with a new discriptor fd */
        int fd1 = accept(sock1, (struct sockaddr*)&client1, &s);
        if (check_fd(fd1) == 1)
            break;
        int fd2 = accept(sock2, (struct sockaddr*)&client2, &s);
        if (check_fd(fd2) == 1)
            break;
        /* Fork for everybody */
        pid_t f1 = fork();
        if (f1 == 0) {
            /*int file = open(argv[2], O_RDONLY);
            if (file < 0)
                exit(0);*/
            run_client(fd1, fd2);
        }
        pid_t f2 = fork();
        if (f2 == 0) {
            run_client(fd2, fd1);
        }
        /*if (close(fd1) < 0)
            return 1;
        if (close(fd2) < 0)
            return 1;*/
        close(fd1);
        close(fd2);
    }
    return 0;
}
