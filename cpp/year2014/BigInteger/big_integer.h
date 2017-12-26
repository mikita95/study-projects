#ifndef BIG_INTEGER_H
#define BIG_INTEGER_H

#include <algorithm>
#include <string>
#include <iosfwd>
#include <stdint.h>
#include "my_vector.h"

class big_integer {
public:
    big_integer();
    big_integer(big_integer&& other);
    big_integer(big_integer const& other);
    big_integer(int a);
    explicit big_integer(std::string const& str);
    ~big_integer();

    big_integer& operator=(big_integer const& other);

    big_integer& operator+=(big_integer const& b);
    big_integer& operator-=(big_integer const& b);
    big_integer& operator*=(big_integer const& b);
    big_integer& operator/=(big_integer const& b);
    big_integer& operator%=(big_integer const& b);

    big_integer& operator&=(big_integer const& b);
    big_integer& operator|=(big_integer const& b);
    big_integer& operator^=(big_integer const& b);

    big_integer& operator<<=(unsigned b);
    big_integer& operator>>=(unsigned b);

    big_integer operator+() const;
    big_integer operator-() const;
    big_integer operator~() const;

    big_integer& operator++();
    big_integer operator++(int);

    big_integer& operator--();
    big_integer operator--(int);

    friend bool operator==(big_integer const& a, big_integer const& b);
    friend bool operator!=(big_integer const& a, big_integer const& b);
    friend bool operator<(big_integer const& a, big_integer const& b);
    friend bool operator>(big_integer const& a, big_integer const& b);
    friend bool operator<=(big_integer const& a, big_integer const& b);
    friend bool operator>=(big_integer const& a, big_integer const& b);

    operator std::string () const;

private:
    big_integer& normalize();
    big_integer& mul(uint32_t b);
    big_integer& div(uint32_t b);
    big_integer& add(big_integer const& b);
    big_integer& sub(big_integer const& b, int sh);
    uint32_t mod(uint32_t b);
    static int abs_compare(big_integer const &a, big_integer const& b, int sh);
    static int compare(big_integer const &a, big_integer const& b);
    big_integer& neg();
    big_integer& unneg();

    std::vector<uint32_t> data;
    bool isNegative;
};

big_integer operator+(big_integer a, big_integer const& b);
big_integer operator-(big_integer a, big_integer const& b);
big_integer operator*(big_integer a, big_integer const& b);
big_integer operator/(big_integer a, big_integer const& b);
big_integer operator%(big_integer a, big_integer const& b);

big_integer operator&(big_integer a, big_integer const& b);
big_integer operator|(big_integer a, big_integer const& b);
big_integer operator^(big_integer a, big_integer const& b);

big_integer operator<<(big_integer a, int b);
big_integer operator>>(big_integer a, int b);

bool operator==(big_integer const& a, big_integer const& b);
bool operator!=(big_integer const& a, big_integer const& b);
bool operator<(big_integer const& a, big_integer const& b);
bool operator>(big_integer const& a, big_integer const& b);
bool operator<=(big_integer const& a, big_integer const& b);
bool operator>=(big_integer const& a, big_integer const& b);

std::string to_string(big_integer const& a);
std::ostream& operator<<(std::ostream&, big_integer const&);
std::istream& operator>>(std::istream&, big_integer&);

#endif // BIG_INTEGER_H
