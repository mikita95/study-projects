#include "reverse_iterator.h"

reverse_iterator::reverse_iterator(node *ptr) {
    ptr_ = ptr;
}

reverse_iterator reverse_iterator::operator++() {
    ptr_ = ptr_->prev;
    return *this;
}

reverse_iterator reverse_iterator::operator++(int a) {
    reverse_iterator r = *this;
    ++*this;
    return r;
}

reverse_iterator reverse_iterator::operator--() {
    ptr_ = ptr_->next;
    return *this;
}

reverse_iterator reverse_iterator::operator--(int a) {
    reverse_iterator r = *this;
    --*this;
    return r;
}

int& reverse_iterator::operator*() const {
    return ptr_->prev->data;
}

bool reverse_iterator::operator==(const reverse_iterator& rhs) const {
    return ptr_ == rhs.ptr_;
}

bool reverse_iterator::operator!=(const reverse_iterator& rhs) const {
    return ptr_ != rhs.ptr_;
}
