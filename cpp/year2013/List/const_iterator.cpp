#include "const_iterator.h"

const_iterator::const_iterator(node *ptr) {
    ptr_ = ptr;
}

const_iterator const_iterator::operator++() {
    ptr_ = ptr_->next;
    return *this;
}

const_iterator const_iterator::operator++(int a) {
    const_iterator r = *this;
    ++*this;
    return r;
}

const_iterator const_iterator::operator--() {
    ptr_ = ptr_->prev;
    return *this;
}

const_iterator const_iterator::operator--(int a) {
    const_iterator r = *this;
    --*this;
    return r;
}

const int& const_iterator::operator*() const {
    return ptr_->data;
}

bool const_iterator::operator==(const const_iterator& rhs) const {
    return ptr_ == rhs.ptr_;
}

bool const_iterator::operator!=(const const_iterator& rhs) const {
    return ptr_ != rhs.ptr_;
}
