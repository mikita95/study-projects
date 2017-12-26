#include <helpers.h>
#include <bufio.h>
int main() {
    struct buf_t *buffer = buf_new(4096);
    size_t length = 0;
    do {
        if ((length = buf_fill(STDIN_FILENO, buffer, 4096)) == -1) {
            return -1;
        }
        if (buf_flush(STDOUT_FILENO, buffer, 4096) == -1) {
            return -1;
        }

    } while (!(length < 4096));
    buf_free(buffer);
    return 0;
}

