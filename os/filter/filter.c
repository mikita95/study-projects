#include <helpers.h>
#include <string.h>
#include <stdlib.h>

const int MAX_SIZE = 4096;

int main(int argc, char * argv []) {
    
    char buffer[MAX_SIZE];
    char str[MAX_SIZE];
    size_t length = 0, pos = 0;
    char ** args = malloc(sizeof(char *) * (argc + 1));
    int i;
    for (i = 0; i < argc - 1; ++i)
        args[i] = argv[i + 1];
    args[argc] = 0;
    args[argc - 1] = str;
    while(1) {
        length = read_until(STDIN_FILENO, buffer, sizeof(buffer), '\n');
        for (i = 0; i < length; ++i) {
            if (buffer[i] == '\n') {
                str[pos] = 0;
                int spwn = spawn(args[0], args);
                if (spwn == 0) {
                    write(STDOUT_FILENO, str, strlen(str));
                    char ch = '\n';
                    write(STDOUT_FILENO, &ch, 1);
                }
                pos = 0;
            } else {
                str[pos] = buffer[i];
                pos++;
            }
        }
        if (length <= 0) {
            if (pos > 0) {
                str[pos] = 0;
                int spwn = spawn(args[0], args);
                if (spwn == 0) {
                    write(STDOUT_FILENO, str, strlen(str));
                    char ch = '\n';
                    write(STDOUT_FILENO, &ch, 1);
                }
            }
            break;
        }
    }
    free(args);
    return 0;
}
