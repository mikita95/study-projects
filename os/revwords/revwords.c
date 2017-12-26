#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <helpers.h>

void reverse_word(char* word, int pos) {
    int i;
    for(i = 0; i < pos / 2; i++) {
        char ch = word[i];
        word[i] = word[pos - i - 1];
        word[pos - i - 1] = ch;
    }
}

int main() {
    char buffer[4096];
    char word[4096];
    size_t pos = 0;
    size_t length;
    while(1) {
        if ((length = read_until(STDIN_FILENO, buffer, sizeof(buffer), ' ')) == -1) {
            fprintf(stderr, "%s\n", strerror(errno));
            return 1;
        }
        int i;
        for (i = 0; i < length; i++)
            if (buffer[i] == ' ') {
                if (pos != 0) {
                    reverse_word(word, pos);
                    write_(STDOUT_FILENO, word, pos);
                }
                char ch = ' ';
                write_(STDOUT_FILENO, &ch, (size_t)1);
                pos = 0;                   
            } else {
                word[pos] = buffer[i];
                pos++;
            }
        if (length <= 0) {
           if (pos != 0) {
               reverse_word(word, pos);
               write_(STDOUT_FILENO, word, (size_t)pos);
           }           
           return 0;
        }   
    }
    return 0;
    
}
