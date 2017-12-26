#ifndef SET_H
#define SET_H
#include "node.h"
#include "iterator.h"

#include <cassert>
#include <stdint.h>
#include <iosfwd>

struct set {
    set();
    ~set();
    set(set const&);
    set& operator=(set const&);
    iterator begin() const;
    iterator end() const;
    iterator find(int) const;//if no then end
    iterator insert(int);
    iterator erase(iterator);//to next*/
    size_t size() const;
    bool empty() const;
private:
    void swap(set&);
    void clear();
    size_t count;
    node* root;
    bool deleteNode(iterator);

};




#endif // SET_H
