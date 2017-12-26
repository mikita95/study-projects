#include "list.h"
#include "iterator.h"
#include "const_iterator.h"
#include "reverse_iterator.h"
#include "const_reverse_iterator.h"
#include <assert.h>
#include <iostream>

list::list() {
    head = tail = new node(0, NULL, NULL);
    head->next = head;
    head->prev = head;
}

list::list(const list& other) {
    try {
        head = tail = new node(0, NULL, NULL);
        head->next = head;
        head->prev = head;
        for (const_iterator it = other.begin(); it != other.end(); it++) {
            push_back(*it);
        }
    } catch(std::exception &e) {
        clear();
        throw e;
    }
}

list::~list() {
    tail->next = NULL;
    node *curr = head, *old;
    while (curr != NULL) {
        old = curr;
        curr = curr->next;
        delete old;
    }
}

void list::swap(list &other) {
    std::swap(head, other.head);
    std::swap(tail, other.tail);
}

list& list::operator=(list other) {
    swap(other);
    return *this;
}

void list::clear() {
    tail->next = NULL;
    node *curr = head, *old;
    while (curr != NULL) {
        old = curr;
        curr = curr->next;
        delete old;
    }
}

bool list::empty() const {
    return (head == tail);
}

void list::push_back(int x) {
    if (head == tail) {
        node *tmp = new node(x, tail, tail);
        head = tmp;
        head->next = tail;
        head->prev = tail;
        tail->next = head;
        tail->prev = head;
    }
    else {
        node* tmp = new node(x, tail->prev, tail);
        tail->prev = tmp;
        if (tail->prev->prev != tail)
            tail->prev->prev->next = tmp;
    }
}

void list::pop_back() {
    assert(!empty());
    node *curr = tail->prev;
    if (curr != tail) {
        if (curr->next == curr->prev) {
            tail->prev = tail;
            head = tail;
        }
        else {
            curr->prev->next = curr->next;
            curr->next->prev = curr->prev;
        }
        delete curr;
    }
}

int& list::back() {
    assert(!empty());
    return tail->prev->data;
}

int const& list::back() const {
    assert(!empty());
    return tail->prev->data;
}

void list::push_front(int x) {
    node *tmp = new node(x, tail, head);
    head->prev = tmp;
    tail->next = tmp;
    head = tmp;
}

void list::pop_front() {
    assert(!empty());
    if (head != tail) {
        node *oldHead = head;
        tail->next = head->next;
        head = head->next;
        head->prev = tail;
        delete oldHead;
    }
}

int& list::front() {
    assert(!empty());
    return head->data;
}

int const& list::front() const {
    assert(!empty());
    return head->data;
}

iterator list::begin() {
    return iterator(head);
}

const_iterator list::begin() const {
    return const_iterator(head);
}

iterator list::end() {
    return iterator(tail);
}

const_iterator list::end() const {
    return const_iterator(tail);
}

reverse_iterator list::rbegin() {
    return reverse_iterator(tail);
}

const_reverse_iterator list::rbegin() const {
    return const_reverse_iterator(tail);
}

reverse_iterator list::rend() {
    return reverse_iterator(head);
}

const_reverse_iterator list::rend() const {
    return const_reverse_iterator(head);
}

void list::insert(iterator pos, int x) {
    if (pos.ptr_ == head) {
        node *temp = new node(x, pos.ptr_->prev, pos.ptr_);
        pos.ptr_->prev->next = temp;
        head = temp;
    }
    else {
        node *temp = new node(x);
        temp->prev = pos.ptr_->prev;
        pos.ptr_->prev->next = temp;
        temp->next = pos.ptr_;
        pos.ptr_->prev = temp;
    }
}

void list::erase(iterator pos) {
    assert(!empty() && pos != end() );
    if (pos != end()) {
        node *curr = pos.ptr_;
        if (curr == head) {
            head->prev->next = head->next;
            head->next->prev = head->prev;
            head = head->next;
        }
        else {
            curr->prev->next = curr->next;
            curr->next->prev = curr->prev;
        }
        delete curr;
    }
}

void list::splice(iterator pos, list& l, iterator first, iterator last) {
    if (pos == head)
        head = first.ptr_;
    if (first == l.head)
        l.head = last.ptr_;
    first.ptr_->prev->next = last.ptr_;
    node *oldPrev = last.ptr_->prev;
    last.ptr_->prev = first.ptr_->prev;
    pos.ptr_->prev->next = first.ptr_;
    first.ptr_->prev = pos.ptr_;
    oldPrev->next = pos.ptr_;
    pos.ptr_->prev = oldPrev;
}
