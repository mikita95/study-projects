#ifndef MY_VECTOR_H
#define MY_VECTOR_H

#include <algorithm>
#include <cstring>
#include <memory>
#include "copy_on_write.h"
#include <stdint.h>

class my_vector {
    bool in_vector; //true - big; false - small;
    unsigned left;
    size_t right;
    size_t data_size;
    copy_on_write data;
    static const size_t SMALL_SIZE = 32;
    unsigned small_data[SMALL_SIZE];
public:
    my_vector();
    my_vector(my_vector const& other);
    ~my_vector();
    void resize(size_t desired_size);
    my_vector& operator=(my_vector const& other);
    void cow_fork();
    unsigned& operator[](int i);
    unsigned operator[](int i) const;
    unsigned& front();
    void pop_front(int count);
    void push_front(int count, unsigned x);
    unsigned& back();
    void push_back(unsigned x);
    size_t size() const;
};

#endif // MY_VECTOR_H
