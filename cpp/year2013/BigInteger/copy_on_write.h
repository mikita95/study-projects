#ifndef COPY_ON_WRITE_H
#define COPY_ON_WRITE_H

#include <stdint.h>
#include "container.h"

class copy_on_write {
    container* data;
public:
    copy_on_write();
    copy_on_write(unsigned* link);
    void set(unsigned* other);
    void set_const(unsigned* other);
    void reset();
    unsigned* get() const;
    copy_on_write& operator=(copy_on_write const& other);
    unsigned& operator[](int i);
    unsigned operator[](int i) const;
    size_t get_count();
};

#endif // COPY_ON_WRITE_H
