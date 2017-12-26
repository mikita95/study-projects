#include "bufio.h"
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

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
    #ifdef DEBUG
    if (buf == 0)
        abort();
    #endif
    free(buf->data);
    free(buf);
}

size_t buf_capacity(struct buf_t *buf) {
    #ifdef DEBUG
    if (buf == 0)
        abort();
    #endif
    return buf->capacity;
}

size_t buf_size(struct buf_t *buf) {
    #ifdef DEBUG
    if (buf == 0)
        abort();
    #endif
    return buf->size;
}

ssize_t buf_fill(int fd, struct buf_t *buf, size_t required) {
    #ifdef DEBUG
    if (buf == 0 || buf->capacity < required)
        abort();
    #endif
    while (1) {
        ssize_t length = read(fd, buf->size + buf->data, buf->capacity - buf->size);
        
        if (length == -1)
            return -1;
        buf->size += length;
        if (buf->size >= required || length <= 0)
            break;
    }
    return buf->size;
}

ssize_t buf_flush(int fd, struct buf_t *buf, size_t required) {
    #ifdef DEBUG
    if (buf == 0)
        abort();
    #endif
    size_t flush = 0;
    if (required > buf->size)
        flush = buf->size;
    else
        flush = required;
    size_t pos = 0;
    int success = 1;
    while(1) {
        ssize_t length = write(fd, buf->data + pos, buf->size);
        if (length == -1) {
            success = 0;
            break;
        }
        pos += length;
        buf->size -= length;
        if (pos >= flush || length <= 0)
            break;
    }
    memcpy(buf->data, buf->data + pos, buf->size);
    if (success == 0)
        return -1;
    return buf->size;
}
