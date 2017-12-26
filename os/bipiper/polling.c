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
#include <poll.h>

const int CLIENTS_COUNT = 127;
const int BUF_SIZE = 4096;
const int TIMEOUT = -1; // infinity
int clients;

void error_message(const char* message) {
    perror(message);
    exit(1);
}

struct pollfd pollfds[256];
struct buf_t* buffs[254];


int make_server(int port) {
    /* Create an endpoint. sock - descriptor
     * AF_INET - domain for IPv4. 
     * SOCK_STREAM - type of socket, two_way byte streams
     * IPPROTO_TCP - protocol
     * */
    int sock = socket(AF_INET, SOCK_NONBLOCK | SOCK_STREAM, IPPROTO_TCP);
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
    if (getaddrinfo("0.0.0.0", 0, &hints, &res) != 0)
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

int make_client(int sock) {
    char buf[BUF_SIZE];
    int i = 0;
    for (i = 0; i < 2; i++) {
        if (pollfds[i].fd == sock && clients < CLIENTS_COUNT)
            pollfds[i].events = POLLIN;
        else pollfds[i].events = 0;
    }
    while (1) {
        for (i = 0; i < clients; i++) {
            if (buffs[2 * i]->size == BUF_SIZE)
                pollfds[2 * i + 2].events = 0;
            else pollfds[2 * i + 2].events = POLLIN;
            if (buffs[2 * i + 1]->size == 0)
                pollfds[2 * i + 2].events |= 0;
            else pollfds[2 * i + 2].events |= POLLOUT;
            if (buffs[2 * i]->size == 0)
                pollfds[2 * i + 3].events = 0;
            else pollfds[2 * i + 3].events = POLLOUT;
            if (buffs[2 * i + 1]->size == BUF_SIZE)
                pollfds[2 * i + 3].events |= 0;
            else pollfds[2 * i + 3].events |= POLLIN;
        }
        
        /* Wait event of fd*/
        if (poll(pollfds, 2 * (clients + 1), TIMEOUT) < 0)
            return -1;

        for (i = 0; i < 2 * (clients + 1); i++) {
            size_t k = i ^ 1;
            /*  For input data */
            if (POLLIN & pollfds[i].revents) {
                if (pollfds[i].fd != sock) {
                    /* Recieve message from socket */
                    size_t length = recv(pollfds[i].fd, &buf, BUF_SIZE - buffs[i - 2]->size, 0);
                    if (length > 0) {
                        memcpy(buffs[i - 2]->data + buffs[i - 2]->size, buf, length);
                        buffs[i - 2]->size += length;
                    }
                } else {
                    return accept(sock, 0, 0);
                }   
            }
            /* For output data */
            if (POLLOUT & pollfds[i].revents) {
                if (send(pollfds[i].fd, buffs[k - 2]->data, buffs[k - 2]->size, 0) > 0)
                    buffs[k - 2]->size = 0;
            }   
            /* Hang up */
            if (POLLHUP & pollfds[i].revents)
                return -1;
        }
    }
}

int main(int argc, char* argv[]) {
    int sock1 = make_server(atoi(argv[1]));
    int sock2 = make_server(atoi(argv[2]));
    if (sock1 < 0 || sock2 < 0)
        exit(1);
    clients = 0;
    int client1, client2;
    pollfds[0].fd = sock1;
    pollfds[1].fd = sock2;
    while (1) {
       client1 = make_client(sock1);
       if (check_fd(client1) == 1)
           break;
       client2 = make_client(sock2);
       if (check_fd(client2) == 1)
           break;
       if (client1 >= 0 && client2 >= 0) {
           int c = clients * 2;

           buffs[c] = buf_new(BUF_SIZE);
           buffs[c + 1] = buf_new(BUF_SIZE);

           pollfds[c + 2].fd = client1;
           pollfds[c + 3].fd = client2;

           clients++;
       } else {
           int index = 0;

           if (client1 < 0)
               index = -client1;
           else index = -client2;
           
           shutdown(pollfds[index].fd, SHUT_RD);
           shutdown(pollfds[index + 1].fd, SHUT_WR);
           int c = clients * 2;

           buffs[index - 2] = buffs[c - 2];
           buffs[index - 1] = buffs[c - 1];

           pollfds[index].fd = pollfds[c].fd;
           pollfds[index].revents = pollfds[c].revents;

           pollfds[index + 1].fd = pollfds[c + 1].fd;
           pollfds[index + 1].revents = pollfds[c + 1].revents;
           clients--;
       }
    }
    return 0;
}
