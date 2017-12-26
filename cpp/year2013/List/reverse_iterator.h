#ifndef REVERSE_ITERATOR_H
#define REVERSE_ITERATOR_H
#include "node.h"

struct reverse_iterator {
    node *ptr_;
    reverse_iterator(node*);
    reverse_iterator operator++();
    reverse_iterator operator++(int);
    reverse_iterator operator--();
    reverse_iterator operator--(int);
    int& operator*() const;
    bool operator==(const reverse_iterator&) const;
    bool operator!=(const reverse_iterator&) const;
};

#endif // REVERSE_ITERATOR_H
