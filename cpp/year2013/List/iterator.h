#ifndef ITERATOR_H
#define ITERATOR_H
#include "node.h"

struct iterator {
    node* ptr_;
    iterator();
    iterator(node*);
    iterator operator++();
    iterator operator++(int);
    iterator operator--();
    iterator operator--(int);
    int& operator*() const;
    bool operator==(const iterator&) const;
    bool operator!=(const iterator&) const;
};

#endif // ITERATOR_H
