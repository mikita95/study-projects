#include <sys/socket.h>
#include <sys/epoll.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <helpers.h>
#include <netdb.h>

#define MAX_SIZE    4096

#define handle_error(msg) \
           do { perror(msg); exit(EXIT_FAILURE); } while (0)


struct epoll_event ev, events[20];
int epfd;

int runpiped2(struct execargs_t **programs, size_t n, int sockfd) {
    pid_t t = fork();
    if (t == 0) {
        pid_t childs[n];
        int fd[n + 1][2], i, k;
        pid_t f;

        fd[n][1] = sockfd;
        fd[0][0] = sockfd;
        for (i = 0; i < n; i++) {
            if (i < n - 1) if (pipe(fd[i + 1]) < 0)
                return -1;
            f = fork();
            childs[i] = f;
            if (f != 0) {
                if (childs[i] < 0) {
                    if (i > 0) {
                        close(fd[i][0]);
                        close(fd[i][1]);
                    }
                    if (i < n - 1) {
                        close(fd[i + 1][0]);
                        close(fd[i + 1][1]);
                    }

                    return -1;
                }
                if (i > 0) {
                    if (close(fd[i][0]) < 0)
                        return -1;
                    if (close(fd[i][1]) < 0)
                        return -1;
                }

            } else {
                if (i > 0) if (close(fd[i][1]) < 0)
                    exit(1);
                if (dup2(fd[i][0], STDIN_FILENO) < 0)
                    exit(EXIT_FAILURE);
                if (i > 0) if (close(fd[i][0]) < 0)
                    exit(EXIT_FAILURE);
                if (i < n - 1) if (close(fd[i + 1][0]) < 0)
                    exit(EXIT_FAILURE);
                if (dup2(fd[i + 1][1], STDOUT_FILENO) < 0)
                    exit(EXIT_FAILURE);
                if (i < n - 1)
                    close(fd[i + 1][1]);
                exec(programs[i]);
                exit(EXIT_FAILURE);
            }
        }


        for (i = 0; i < n; i++) {
            if (waitpid(childs[i], NULL, 0) == -1) {
                //          write_(STDERR_FILENO, strerror(errno), sizeof(char) * strlen(strerror(errno)));
                //         return -1;      
            }
        }
        ev.data.fd = sockfd;
        ev.events = EPOLLOUT;
        if (epoll_ctl(epfd, EPOLL_CTL_MOD, sockfd, &ev) == -1) {
            perror("epoll_ctl: sockfd");
            exit(EXIT_FAILURE);
        }
    }
    return 0;
}

int create_server(int port) {
    char port_str[10];
    sprintf(port_str, "%d", port);
    int status;
    struct addrinfo hints;
    struct addrinfo *resinfo;
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    status = getaddrinfo("localhost", port_str, &hints, &resinfo);
    if (status == -1)
        handle_error("getaddrinfo");

    int sockfd = 0;
    struct addrinfo *i;
    for (i = resinfo; i != NULL; i = i->ai_next) {
        sockfd = socket(i->ai_family, i->ai_socktype, i->ai_protocol);
        if (sockfd == -1) continue;
        int yes = 1;
        status = setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int));
        if (status == -1) continue;
        status = bind(sockfd, i->ai_addr, i->ai_addrlen);
        if (status == 0) break;
        close(sockfd);
    }
    if (i == NULL) return EXIT_FAILURE;
    freeaddrinfo(resinfo);
    return sockfd;
}

void make_daemon(const char *pid_file) {
    pid_t pid = fork();
    if (pid == -1) return;
    if (pid != 0) handle_error("fork");
    if (setsid() < 0) return;
    pid = fork();
    if (pid == -1) return;
    if (pid != 0) handle_error("fork");
    int pid_fd = open(pid_file, O_RDWR | O_CREAT | O_EXCL);
    if (pid_fd == -1) {
        unlink(pid_file);
        return;
    }
    int i;
    for (i = (int) sysconf(_SC_OPEN_MAX); i >= 0; i--) {
        if (i != pid_fd) {
            close(i);
        }
    }
    int stdio_fd = open("/dev/null", O_RDWR);
    dup(stdio_fd);
    dup(stdio_fd);
    if (pid_fd < 0) return;
    dprintf(pid_fd, "%d\n", getpid());
    close(pid_fd);
    int pgrp = setpgrp();
    if (pgrp == -1) {
       unlink(pid_file);
       return;
    }
}


void setnonblocking(int sock) {
    int opts;
    opts = fcntl(sock, F_GETFL);
    if (opts < 0)
        handle_error("fcntl(sock, F_GETFL)");
    opts = opts | O_NONBLOCK;
    if (fcntl(sock, F_SETFL, opts) < 0)
        handle_error("fcntl(sock, SETFL, opts)");
}

int main(int argc, char *argv[]) {
    const char *pid_file = "/tmp/netsh.pid";
    make_daemon(pid_file);
    int i, connfd, sockfd, nfds;
    socklen_t clilen;
    struct sockaddr_in clientaddr;
    int listenfd = create_server(atoi(argv[1]));
    setnonblocking(listenfd);
    if (listen(listenfd, SOMAXCONN) == -1)
        handle_error("listen");
    epfd = epoll_create1(0);
    if (epfd == -1)
        handle_error("epoll_create");
    ev.data.fd = listenfd;
    ev.events = EPOLLIN | EPOLLET;
    if (epoll_ctl(epfd, EPOLL_CTL_ADD, listenfd, &ev) == -1)
        handle_error("epoll_ctl: listen_sock");
    for (; ;) {
        nfds = epoll_wait(epfd, events, 20, -1);
        if (nfds == -1)
            handle_error("epoll_pwait");
        for (i = 0; i < nfds; ++i) {
            if ((events[i].events & EPOLLERR) || (events[i].events & EPOLLHUP)) {
                close(events[i].data.fd);
                continue;
            }
            if (events[i].data.fd == listenfd) {
                printf("accept connection, fd is %d\n", listenfd);
                while ((connfd = accept(listenfd, (struct sockaddr *) &clientaddr, &clilen)) > 0) {
                    //if (connfd < 0) handle_error("accept");
                    setnonblocking(connfd);
                    char *str = inet_ntoa(clientaddr.sin_addr);
                    printf("connect from %s\n", str);
                    ev.data.fd = connfd;
                    ev.events = EPOLLIN | EPOLLET;
                    if (epoll_ctl(epfd, EPOLL_CTL_ADD, connfd, &ev) == -1)
                        handle_error("epoll_ctl: connfd");
                }
                continue;
            }
            else if (events[i].events & EPOLLIN) {
                if ((sockfd = events[i].data.fd) < 0) continue;
                char buffer[MAX_SIZE];
                int count = 0, length, f, k;
                struct execargs_t *programs[MAX_SIZE];
                length = read_until(sockfd, buffer, MAX_SIZE, '\n');
                if (length < 0) {
                    if (errno == ECONNRESET) {
                        close(sockfd);
                        events[i].data.fd = -1;
                    } else {
                        printf("readline error");
                    }
                } else if (length == 0) {
                    close(sockfd);
                    events[i].data.fd = -1;
                }
                f = 0;
                for (i = 0; i < length; i++) {
                    if (buffer[i] == '|' || buffer[i] == '\n') {
                        if (f != i) {
                            programs[count] = execargs_t_new(buffer + f, i - f);
                            count++;
                        }
                        if (buffer[i] == '\n') {
                            if (runpiped2(programs, (size_t) count, sockfd) < 0)
                                exit(1);
                            for (k = 0; k < count; k++)
                                execargs_t_delete(programs[k]);
                            count = 0;
                        }
                        f = i + 1;
                    }
                }

            } else if (events[i].events & EPOLLOUT) {
                printf("data written: %s\n", "");
                ev.data.fd = sockfd;
                if (epoll_ctl(epfd, EPOLL_CTL_DEL, sockfd, &ev) == -1)
                    handle_error("epoll_ctl: sockfd");
                if (shutdown(sockfd, SHUT_RDWR) == -1)
                    perror("shutdown");
                close(sockfd);
            }

        }
    }
    close(listenfd);
    unlink(pid_file);
}
