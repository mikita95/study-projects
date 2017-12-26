#include "container.h"

container::container(unsigned* link, bool cnst) : link(link), cnst(cnst), count(1) {}

bool container::my_delete() {
    --count;
    if (count <= 0) {
        if (!cnst) {
            delete[] link;
        }
        return true;
    } else {
        return false;
    }
}
