#include "big_integer.h"
#include <iostream>

int main() {
    big_integer a;
    std::cin >> a;
    a++;
    a *= 2;
    std::cout << a << std::endl;
    big_integer b(333);
    big_integer c(b);
    std::cout << c << std::endl << b;
    big_integer r("112j");
}
