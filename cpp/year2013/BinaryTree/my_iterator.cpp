#include "iterator.h"
#include <cassert>

iterator::iterator() {
    ptr = NULL;
}

iterator::iterator(node* t) {
    ptr = t;
}

iterator::~iterator() {
}

node* findMax(node* x)
{
    while(x->right != NULL)
        x = x->right;
    return x;
}

node* findMin(node* x)
{
    while(x->left != NULL)
        x = x->left;
    return x;
}

node* next(node* x)
{
    node* y;
    if(x == NULL)
        return NULL;
    if (x->right != NULL)
        return findMin(x->right);
    y = x->parent;
    while(y != 0 && x == y->right) {
        x = y;
        y = y->parent;
    }
    return y;
}

node* prev(node* x) {
    node* y;
    if(x == NULL)
        return NULL;
    if (x->left != NULL)
        return findMax(x->left);
    y = x->parent;
    while(y != NULL && x == y->left) {
        x = y;
        y = y->parent;
    }
    return y;
}

node* findNode(node* n, const int& val)
{
    if (n == NULL || val == n->getData())
        return n;
    if (val > n->getData())
        return findNode(n->right, val);
    else
        return findNode(n->left, val);
}

iterator& iterator::operator++() {
    ptr = next(ptr);
    return *this;
}

iterator& iterator::operator--() {
    ptr = prev(ptr);
    return *this;
}

bool iterator::operator!=(iterator const& other) {
    if (ptr != other.ptr)
        return true;

    return false;
}
bool iterator::operator==(iterator const& other) {
    if (ptr == other.ptr)
        return true;
    return false;
}

int const& iterator::operator*() const {
    assert (ptr != NULL);
    return ptr->getData();
}

iterator iterator::operator++(int a) {
    iterator r = *this;
    ++*this;
    return r;
}

iterator iterator::operator--(int a) {
    iterator r = *this;
    --*this;
    return r;
}
