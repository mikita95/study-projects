#ifndef CONST_REVERSE_ITERATOR_H
#define CONST_REVERSE_ITERATOR_H
#include "node.h"

struct const_reverse_iterator {
    node *ptr_;
    const_reverse_iterator(node*);
    const_reverse_iterator operator++();
    const_reverse_iterator operator++(int);
    const_reverse_iterator operator--();
    const_reverse_iterator operator--(int);
    const int& operator*() const;
    bool operator==(const const_reverse_iterator&) const;
    bool operator!=(const const_reverse_iterator&) const;
};

#endif // CONST_REVERSE_ITERATOR_H
