#include "helpers.h"
#include <sys/types.h>
#include <sys/uio.h>
#include <sys/wait.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>

struct execargs_t* execargs_t_new(char const* str, int length) {
    struct execargs_t* result = malloc(sizeof(struct execargs_t));
    int count = 0, i, k = 0;
    for (i = 0; i < length; i++) {
        if (str[i] == ' ' && k < i) {
            k = i + 1;
            count++;
        }
    }
    count++;
    if (length > k) count++;
    result->args_ = calloc(count, sizeof(char*));
    result->args_count_ = count;
    
    k = 0, count = 0;
    for (i = 0; i < length; i++){
        if (str[i] == ' ') {
            if (k < i) {
                result->args_[count] = calloc((i + 1 - k), sizeof(char));		
                memcpy(result->args_[count], str + k, i - k);
                result->args_[count][i + 1 - k] = 0;
                count++;
            } 
            k = i + 1;
        }   
    }
	
    if (k != length) {
        result->args_[count] = calloc(length + 1 - k, sizeof(char));
        memcpy(result->args_[count], str + k, length - k);
        count++;
    }
    result->args_[count] = 0;
    return result;
}

void execargs_t_delete(struct execargs_t* e) {
    int i;
    for (i = 0; i < e->args_count_; i++)    
        free(e->args_[i]);
    free(e->args_);
    free(e);
}

int exec(struct execargs_t* args) {
    execvp(*(args->args_), args->args_);
    return -1;
}

void parent_handler(int signal) {
    if (signal == SIGINT) kill(0, SIGUSR1);
}

void child_handler(int signal) {
    if (signal == SIGUSR1) {
        pid_t p = getpid();
        kill(p, SIGINT);
    }
}

int runpiped(struct execargs_t** programs, size_t n) {
    struct sigaction action;
    sigset_t set;
    memset(&action, 0, sizeof(action));
    action.sa_handler = parent_handler;
    sigemptyset(&set);
    sigaddset(&set, SIGINT);
    sigaddset(&set, SIGUSR1);

    action.sa_mask = set;
    sigaction(SIGINT, &action, 0);
    sigaction(SIGUSR1, &action, 0);
    pid_t childs[n];
    int fd[n + 1][2], i, k;
    pid_t f;

    fd[n][1] = STDOUT_FILENO;
    fd[0][0] = STDIN_FILENO;
    for (i = 0; i < n; i++) {
        if (i < n - 1)
            if (pipe(fd[i + 1]) < 0)
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

                for (k = 0; k < i; k++) 
                    // kill(childs[k], SIGKILL);
                    kill(childs[k], SIGUSR1);
                return -1;
            }
            if (i > 0) {
                if (close(fd[i][0]) < 0)
                    return -1;
                if (close(fd[i][1]) < 0)
                    return -1;
            }
			
        } else {
            struct sigaction action1;
            sigset_t set1;
            memset(&action1, 0, sizeof(action1));
            action1.sa_handler = child_handler;
            sigemptyset(&set1);
            sigaddset(&set1, SIGUSR1);
            action1.sa_mask = set1;
            sigaction(SIGUSR1, &action1, 0);
            if (i > 0)
                if (close(fd[i][1]) < 0)
                    exit(1);
            if (dup2(fd[i][0], STDIN_FILENO) < 0)
                exit(1);
            if (i > 0)
                if (close(fd[i][0]) < 0)
                    exit(1);
            if (i < n - 1)
                if (close(fd[i + 1][0]) < 0)
                    exit(1);
            if (dup2(fd[i + 1][1], STDOUT_FILENO) < 0)
                exit(1);
            if (i < n - 1)
                close(fd[i + 1][1]);

            exec(programs[i]);
            exit(1);
        }   
    }
    
    for (i = 0; i < n; i++) {   
        if (waitpid(childs[i], NULL, 0) == -1) {
    //        write_(STDERR_FILENO, strerror(errno), sizeof(char) * strlen(strerror(errno)));
    //            return -1;      
        }
    }
    return 0;
}

ssize_t read_(int fd, void *buf, size_t count) {
    size_t result = 0;
    size_t length = 0;
    if (count == 0)
        return read(fd, buf, 0);
    while (1) {
    
        if ((length = read(fd, buf + result, count)) == -1)
            return -1;
       
        result += length;
        count -= length;
        if (count <= 0)
            return result;
        if (length <= 0)
            return result;
    }
}

ssize_t write_(int fd, const void *buf, size_t count) {
    size_t result = 0;
    size_t length = 0;
    do {
        if ((length = write(fd, buf + result, count)) == -1)
            return -1;
        count -= length;
        result += length;
    } while (length > 0 && count > 0);
    return result;
}

ssize_t read_until(int fd, void * buf, size_t count, char delimiter) {
    size_t result = 0;
    size_t length = 0;
    if (count == 0)
        return read(fd, buf, 0);    
    while(1) {
        if ((length = read(fd, buf + result, count)) == -1) {
            return -1;
        }
        int i;
        for (i = 0; i < length; i++) {
            if (((char*) buf)[result + i] == delimiter) {
                result += length;
                count -= length;
                return result;
            }
        }
        result += length;
        count -= length;
        if (count <= 0)
            return result;
        if (length <= 0)
            return result;
    }

    return result;
}

int spawn(const char *file, char *const argv []) {
	pid_t pid = fork();
	if (pid == -1) {
		write_(STDERR_FILENO, strerror(errno), sizeof(char) * strlen(strerror(errno)));
		return -1;
	}	
	if (pid == 0) {
		if (execvp(file, argv) == -1) {
			write_(STDERR_FILENO, strerror(errno), sizeof(char) * strlen(strerror(errno)));
    	    return -1;
		}
	} else {
		int status;
		if (waitpid(pid, &status, 0) == -1) {
			write_(STDERR_FILENO, strerror(errno), sizeof(char) * strlen(strerror(errno)));
    	    return -1;
		}
		if (WIFEXITED(status))
			return WEXITSTATUS(status);
		else return -1;
	}
	return -1;	
}
