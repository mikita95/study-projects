#include <helpers.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <signal.h>

const size_t MAX_SIZE = 4096;

void set_handler(int signal) {
    if (signal == SIGINT)
        kill(0, SIGUSR1);
}

void hello() {
    if (write_(STDIN_FILENO, "$", 1) < 0)
        exit(1);
}

int main() {
    char buffer[MAX_SIZE];
    int count = 0, pos = 0, length, i, f, k;
    struct execargs_t* programs[MAX_SIZE];
    struct sigaction action;
    sigset_t set;
	
    memset(&action, 0, sizeof(action));

    action.sa_handler = set_handler;
    sigemptyset(&set);
    sigaddset(&set, SIGINT);
    sigaddset(&set, SIGUSR1);
    action.sa_mask = set;
    sigaction(SIGINT, &action, 0);
    sigaction(SIGUSR1, &action, 0);
	
    hello();
	
    while (1) {
        length = read_until(STDIN_FILENO, buffer + pos, MAX_SIZE - pos, '\n');
        if (length == 0)
            break;
        if (length < 0) {
            if (errno != EINTR) 
                break;
            continue;
        }
        f = 0;
        for (i = 0; i < length + pos; i++) {
            if (buffer[i] == '|' || buffer[i] == '\n') {
                if (f != i) {
                    programs[count] = execargs_t_new(buffer + f, i - f);
                    count++;
                }
                if (buffer[i] == '\n') {
				
                    if (runpiped(programs, count) < 0)
                        exit(1);

                    for (k = 0; k < count; k++)
                        execargs_t_delete(programs[k]);

                    count = 0;
                    hello();
                }
                f = i + 1;
            }
        }
		
        memmove(buffer, buffer + f, length - f);
        pos = length - f;
	}
	
    for (k = 0; k < count; k++)
        execargs_t_delete(programs[k]);
		
    if (length < 0) exit(1);
	
    return 0;
}
