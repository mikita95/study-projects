#ifndef CONST_ITERATOR_H
#define CONST_ITERATOR_H
#include "node.h"

struct const_iterator {
    node *ptr_;
    const_iterator(node*);
    const_iterator operator++();
    const_iterator operator++(int);
    const_iterator operator--();
    const_iterator operator--(int);
    const int& operator*() const;
    bool operator==(const const_iterator&) const;
    bool operator!=(const const_iterator&) const;
};

#endif // CONST_ITERATOR_H
