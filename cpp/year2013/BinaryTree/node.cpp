#include "node.h"

node::node(const int& x) {
    data = x;
    left = NULL;
    right = NULL;
    parent = NULL;
}

node::node() {
    data = 0;
    left = NULL;
    right = NULL;
    parent = NULL;
}

int node::getData() const {
    return data;
}
