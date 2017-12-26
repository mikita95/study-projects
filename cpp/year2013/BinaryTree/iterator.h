#ifndef ITERATOR_H
#define ITERATOR_H
#include "node.h"

struct iterator {
    node* ptr;
    iterator();
    ~iterator();
    iterator(node* t);
    iterator& operator++();
    iterator operator++(int a);
    iterator& operator--();
    iterator operator--(int a);
    bool operator!=(iterator const& other);
    bool operator==(iterator const& other);
    int const& operator*() const;
};

#endif // iterator_H
