#ifndef CONTAINER_H
#define CONTAINER_H

#include <stdint.h>
#include <iosfwd>

class container {
    struct copy_on_write;
    bool cnst;
public:
    unsigned* link;
    size_t count;
    container(unsigned* link, bool cnst);
    bool my_delete();
};

#endif // CONTAINER_H
