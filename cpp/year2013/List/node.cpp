#include "node.h"
#include <stdlib.h>

node::node(int x) {
    data = x;
    next = NULL;
    prev = NULL;
}

node::node(int x, node* a, node* b) {
    data = x;
    prev = a;
    next = b;
}
