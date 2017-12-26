#include "set.h"
#include <cassert>
#include <cstdlib>
#include <set>
#include <vector>

set::set() {
    root = NULL;
    count = 0;
}

set::~set() {
    clear();
}

void set::swap(set& other) {
    std::swap(root, other.root);
    std::swap(count, other.count);
}

set::set(set const& other) {
    try {
        root = NULL;
        count = 0;
        for (iterator it = other.begin(); it != other.end(); it++) {
            insert(*it);
        }
    } catch(std::exception &e) {
        clear();
        throw e;
    }
}

void set::clear() {
    if (root == NULL)
        return;
/*while (count > 0)
        erase(begin());*/

}

set& set::operator=(set const& other) {
   if(this != &other)
        set(other).swap(*this);
    return *this;
}

node* findMinInSet(node* const x) {
    node* y = x;
    while(y != NULL && y->left != NULL)
        y = y->left;
    return y;
}

node* findMaxInSet(node* const x) {
    node* y = x;
    while(y != NULL && y->right != NULL)
        y = y->right;
    return y;
}

iterator set::begin() const {
    if (root == NULL)
        return iterator(NULL);
    return iterator(findMinInSet(root));
}

iterator set::end() const {
    if (root == NULL)
        return iterator(NULL);
    return iterator(root->parent);
}

node* findNodeInSet(node* n, const int& value) {
    if (n == NULL || value == n->getData())
        return n;
    if (value > n->getData())
        return findNodeInSet(n->right, value);
    else
        return findNodeInSet(n->left, value);
}

iterator set::find(int value) const {
    node* n = findNodeInSet(root, value);
    if (n == NULL)
        return end();
    return iterator(n);
}

iterator set::insert(int value) {
    node* s = findNodeInSet(root, value);
    if (s != NULL) {
        return iterator(s);
    }
    count++;
    node* n = new node(value);
    node* p1 = NULL;
    node* p = root;
    while(p != NULL) {
        p1 = p;
        if(value < p->getData())
            p = p->left;
        else
            p = p->right;
    }
    n->parent = p1;
    if (p1 == NULL) {
        root = n;
        root->parent = new node(0);
        root->parent->left = root;
    } else {
        if(value < p1->getData())
            p1->left = n;
        else
            p1->right = n;
    }
    return iterator(n);
}

node* nextInSet(node* x) {
    node* y;
    if(x == NULL)
        return NULL;
    if (x->right != NULL)
        return findMinInSet(x->right);
    y = x->parent;
    while(y != 0 && x == y->right) {
        x = y;
        y = y->parent;
    }
    return y;
}

bool set::deleteNode(iterator pos) {
    node* temp = pos.ptr;
    node* delNode;
    node* delParent = NULL;
    if (temp == root) {
        delNode = root;
        delParent = NULL;
    } else {
        delNode = temp;
        delParent = temp->parent;
    }

    if (delNode->right == NULL) {
        if (delParent == NULL) {
            root = delNode->left;
            delete delNode;
            return true;
        } else {
            if (delParent->left == delNode)
                delParent->left = delNode->left;
            else
                delParent->right = delNode->left;
            delete delNode;
            return true;
        }
    } else {
        if (delNode->left == NULL) {
            if (delParent == NULL) {
                root = delNode->right;
                delete delNode;
                return true;
            } else {
                if(delParent->left == delNode)
                    delParent->left = delNode->right;
                else
                    delParent->right = delNode->right;
                delete delNode;
                return true;
            }
        } else {
            temp = delNode->left;
            node* back = delNode;
            while(temp->right != NULL)
            {
                back = temp;
                temp = temp->right;
            }
            delNode->data = temp->data;
            if(back == delNode)
                back->left = temp->left;
            else
                back->right = temp->left;
            delete temp;
            return true;
        }
    }
}



iterator set::erase(iterator pos) {
    assert (pos != end());
    count--;
    root->parent = NULL;
    //root->parent->left = NULL;
    delete root->parent;
    node* n = nextInSet(pos.ptr);
    deleteNode(pos);
    if (root != NULL) {
        root->parent = new node(0);
        root->parent->left = root;
    }
    return iterator(n);
}

size_t set::size() const {
    return count;
}

bool set::empty() const {
    return (count == 0);
}
