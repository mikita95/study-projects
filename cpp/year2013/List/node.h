#ifndef NODE_H
#define NODE_H

struct node {
    int data;
    node *next, *prev;
    node() {}
    node(int);
    node(int, node* , node*);
};

#endif // NODE_H
