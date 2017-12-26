#ifndef HELPERS_H
#define HELPERS_H

#include <unistd.h>

struct execargs_t {
    char** args_;
    size_t args_count_;
};

struct execargs_t* execargs_t_new(char const* line, int length);
void execargs_t_delete(struct execargs_t* e);

int exec(struct execargs_t* args);
int runpiped(struct execargs_t** programs, size_t n);

ssize_t read_(int fd, void *buf, size_t count);
ssize_t write_(int fd, const void *buf, size_t count);
ssize_t read_until(int fd, void *buf, size_t count, char delimiter);
int spawn(const char *file, char *const argv []);
#endif
