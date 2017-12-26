#include "iterator.h"
#include <stdlib.h>

iterator::iterator() {
    ptr_ = NULL;
}

iterator::iterator(node* ptr) {
    ptr_ = ptr;
}

iterator iterator::operator++() {
    ptr_ = ptr_->next;
    return *this;
}

iterator iterator::operator++(int a) {
    iterator r = *this;
    ++*this;
    return r;
}

iterator iterator::operator--() {
    ptr_ = ptr_->prev;
    return *this;
}

iterator iterator::operator--(int a) {
    iterator r = *this;
    --*this;
    return r;
}

int& iterator::operator*() const {
    return ptr_->data;
}

bool iterator::operator==(const iterator& rhs) const {
    return ptr_ == rhs.ptr_;
}

bool iterator::operator!=(const iterator& rhs) const {
    return ptr_ != rhs.ptr_;
}

