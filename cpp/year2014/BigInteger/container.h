#ifndef CONTAINER_H
#define CONTAINER_H

#include <stdint.h>
#include <iosfwd>

struct container {
    struct copy_on_write;
    unsigned* link;
    bool cnst;
    size_t count;
    public:
        container(unsigned* link, bool cnst);
        bool my_delete();
};

#endif // CONTAINER_H
