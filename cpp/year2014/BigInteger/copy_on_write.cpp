#include "copy_on_write.h"

copy_on_write::copy_on_write() {
    data = NULL;
}

copy_on_write::copy_on_write(unsigned* link) {
    set(link);
}

void copy_on_write::reset() {
    if (data && data->my_delete()) {
        delete data;
    } else {
        data = NULL;
    }
}

void copy_on_write::set(unsigned* other) {
    data = new container(other, false);
}

void copy_on_write::set_const(unsigned* other) {
    data = new container(other, true);
}

unsigned* copy_on_write::get() const {
    return data->link;
}

copy_on_write& copy_on_write::operator=(copy_on_write const& other) {
    data = other.data;
    ++data->count;
    return *this;
}

unsigned& copy_on_write::operator[](int i) {
    return data->link[i];
}

unsigned copy_on_write::operator[](int i) const {
    return data->link[i];
}

size_t copy_on_write::get_count() {
    return data->count;
}
