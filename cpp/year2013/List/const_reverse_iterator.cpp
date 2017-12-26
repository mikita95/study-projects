#include "const_reverse_iterator.h"

const_reverse_iterator::const_reverse_iterator(node *ptr) {
    ptr_ = ptr;
}

const_reverse_iterator const_reverse_iterator::operator++() {
    ptr_ = ptr_->prev;
    return *this;
}

const_reverse_iterator const_reverse_iterator::operator++(int a) {
    const_reverse_iterator r = *this;
    ++*this;
    return r;
}

const_reverse_iterator const_reverse_iterator::operator--() {
    ptr_ = ptr_->next;
    return *this;
}

const_reverse_iterator const_reverse_iterator::operator--(int a) {
    const_reverse_iterator r = *this;
    --*this;
    return r;
}

const int& const_reverse_iterator::operator*() const {
    return ptr_->prev->data;
}

bool const_reverse_iterator::operator==(const const_reverse_iterator& rhs) const {
    return ptr_ == rhs.ptr_;
}

bool const_reverse_iterator::operator!=(const const_reverse_iterator& rhs) const {
    return ptr_ != rhs.ptr_;
}
