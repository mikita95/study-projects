#ifndef NODE_H
#define NODE_H

#include <stdint.h>
#include <iosfwd>

struct node {
public:
    node(const int& x);
    node();
    int getData() const;
    int data;
    node* left;
    node* right;
    node* parent;
};

#endif // NODE_H
