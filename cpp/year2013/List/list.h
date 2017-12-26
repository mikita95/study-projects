#ifndef LIST_H
#define LIST_H

#include <stdlib.h>
#include "node.h"
#include "iterator.h"
#include "const_iterator.h"
#include "reverse_iterator.h"
#include "const_reverse_iterator.h"

struct list {
public:
    list();
    list(list const&);
    list& operator=(list);
    ~list();
    bool empty() const;
    void push_back(int);
    void pop_back();
    int& back();
    int const& back() const;
    void push_front(int);
    void pop_front();
    int& front();
    int const& front() const;
    iterator begin();
    const_iterator begin() const;
    iterator end();
    const_iterator end() const;
    reverse_iterator rbegin();
    const_reverse_iterator rbegin() const;
    reverse_iterator rend();
    const_reverse_iterator rend() const;
    void insert(iterator, int);
    void erase(iterator);
    void splice(iterator, list&, iterator, iterator);

private:
    void clear();
    void swap(list&);
    node* head;
    node* tail;
};

#endif // LIST_H
