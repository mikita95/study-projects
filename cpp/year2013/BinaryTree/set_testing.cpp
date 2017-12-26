#include "set.h"
#include <set>
#include <vector>
#include <iostream>
#include <cstdlib>
#include <algorithm>


int myrand() {
    int val = rand() - RAND_MAX / 2;
    if (val != 0)
        return val;
    else
        return 1;
}

bool testConstructor1() {
    set tree;
    return (tree.size() == 0);
}

bool testConstructor2() {
    try {
        set tree;
        set tree1(tree);
        set tree2 = tree;
        return true;
    } catch(...) {
        return false;
    }
}

bool testSelfAssigment() {
    try {
        set l;
        l = l;
        l.insert(1);
        l = l;
        return true;
    } catch(...) {
        return false;
    }
}

bool testBegin() {
    set tree;
    tree.insert(5);
    tree.insert(0);
    tree.insert(-10);
    tree.insert(90);
    tree.insert(-3);
    return (*tree.begin() == -10);
}

bool testEnd() {
    set tree;
    tree.insert(5);
    tree.insert(0);
    tree.insert(-10);
    tree.insert(90);
    tree.insert(-3);
    iterator it = tree.end();
    it--; it--;
    it++; it++;
    it--; it--;
    return (*it == 5);
}

bool testFind() {
    set tree;
    tree.insert(5);
    tree.insert(0);
    tree.insert(-10);
    tree.insert(90);
    tree.insert(-3);
    return (*tree.find(0) == 0 && tree.find(33) == tree.end());
}

bool testInsert() {
    set tree;
    std::set<int> test;
    int number = 1000;
    for (int i = 0;  i < number; i++) {
        int tmp = myrand();
        tree.insert(tmp);
        test.insert(tmp);
    }
    iterator it1 = tree.begin();
    std::set<int>::iterator it2 = test.begin();
    while (it1 != tree.end()) {
        if (*it1 != *it2)
            return false;
        it1++;
        it2++;
    }
    return true;
}

bool testErase() {
    set tree;
    std::set<int> test;
    std::vector<int> mas;
    int number = 10;
    for (int i = 0;  i < number; i++) {
            tree.insert(i);
            test.insert(i);

    }
    for (int i = 0; i < number; i++) {
        if (i % 2 == 0) {
            tree.erase(tree.find(i));
            test.erase(test.find(i));
        }

    }

    iterator it1 = tree.begin();
    std::set<int>::iterator it2 = test.begin();
    while (it1 != tree.end()) {
        if (*it1 != *it2)
            return false;
        it1++;
        it2++;
    }
    return true;
}

int main() {
    std::cout << "Constructor1 is testing... ";
    if (testConstructor1())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";

    std::cout << "Constructor2 is testing... ";
    if (testConstructor2())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";

    std::cout << "SelfAssigment is testing... ";
    if (testSelfAssigment())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";


    std::cout << "Begin is testing... ";
    if (testBegin())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";

    std::cout << "End is testing... ";
    if (testEnd())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";

    std::cout << "Find is testing... ";
    if (testFind())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";

    std::cout << "Insert is testing... ";
    if (testInsert())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";

    std::cout << "Erase is testing... ";
    if (testErase())
        std::cout << "OK\n";
    else
        std::cout << "FAIL\n";



    return 0;
}
