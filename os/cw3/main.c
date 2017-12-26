#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/wait.h>

const int MAX_SIZE = 4096;
#define NO_DELIMITER_FOUND -1
#define READ_UNTIL_EOF     -2
#define READ_ERROR         -3

struct execargs_t {
    char **args_;
    size_t args_count_;
};

struct buf_t {
    size_t size;
    size_t capacity;
    char *data;
};

struct buf_t *buf_new(size_t capacity) {
    struct buf_t *buf = malloc(sizeof(struct buf_t));
    if (buf == 0)
        return 0;
    buf->data = malloc(capacity);
    buf->capacity = capacity;
    buf->size = 0;
    return buf;
}

void buf_free(struct buf_t *buf) {
    free(buf->data);
    free(buf);
}

struct execargs_t *execargs_t_new(char const *str, int length) {
    struct execargs_t *result = malloc(sizeof(struct execargs_t));
    size_t count = 0, i, k = 0;
    for (i = 0; i < length; i++) {
        if (str[i] == ' ' && k < i) {
            k = i + 1;
            count++;
        }
    }
    count++;
    if (length > k) count++;
    result->args_ = calloc(count, sizeof(char *));
    result->args_count_ = count;

    k = 0, count = 0;
    for (i = 0; i < length; i++) {
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

void execargs_t_delete(struct execargs_t *e) {
    int i;
    for (i = 0; i < e->args_count_; i++)
        free(e->args_[i]);
    free(e->args_);
    free(e);
}

int exec(struct execargs_t *args) {
    execvp(*(args->args_), args->args_);
    return 1;
}

int run_piped(struct execargs_t *program, int result, int last) {
    int fd[2];
    pid_t f;
    if (pipe(fd) < 0)
        return -1;
    f = fork();
    if (f == 0) {
        dup2(result, STDIN_FILENO);
        if (last == 0) dup2(fd[1], STDOUT_FILENO);
        _exit(exec(program));
    }
    if (f < 0)
        return -1;
    waitpid(f, NULL, 0);
    if (last == 1) close(fd[0]);
    close(fd[1]);
    close(result);
    return fd[0];
}

ssize_t delimiter_lookup(char *buffer, int len, char delimiter) {
    size_t i;
    for (i = 0; i < len; i++) {
        if (buffer[i] == 0)
            break;
        if (buffer[i] == delimiter)
            return i;
    }
    return NO_DELIMITER_FOUND;
}

ssize_t buf_read_until(int fd, struct buf_t *buf, char delimiter) {
    ssize_t pos;
    int res = 0;
    while ((pos = delimiter_lookup(buf->data, (int) buf->size, delimiter)) < 0) {
        res = (int) read(fd, buf->data + buf->size, buf->capacity - buf->size);
        if (res == 0)
            break;
        if (res > 0)
            buf->size += res;
        else
            return READ_ERROR;

    }
    if (res == 0)
        return READ_UNTIL_EOF;
    return pos;
}

int main() {
    ssize_t length = 0;
    struct execargs_t *program;
    int result = open("/dev/null", O_RDWR);
    struct buf_t *buffer = buf_new(MAX_SIZE);
    while (1) {
        length = buf_read_until(STDIN_FILENO, buffer, '\n');
        char *current = buffer->data;
        current[buffer->size] = '\0';
        if (length == READ_UNTIL_EOF) {
            program = execargs_t_new(current, (int) buffer->size);
            if (run_piped(program, result, 1) < 0) {
                buf_free(buffer);
                execargs_t_delete(program);
                _exit(1);
            }
            buf_free(buffer);
            execargs_t_delete(program);
            return 0;
        }
        if (length == READ_ERROR) {
            buffer->size = 0;
            continue;
        }
        program = execargs_t_new(current, (int) length);
        if ((result = run_piped(program, result, 0)) < 0) {
            buf_free(buffer);
            execargs_t_delete(program);
            _exit(1);
        }
        execargs_t_delete(program);
        buffer->size = 0;
    }
}
