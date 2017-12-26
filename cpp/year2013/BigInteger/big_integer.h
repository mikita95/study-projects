#ifndef BIG_INTEGER_H
#define BIG_INTEGER_H

#include <algorithm>
#include <string>
#include <iosfwd>
#include <stdint.h>

#include "my_vector.h"

struct big_integer
{
    big_integer();
    big_integer(big_integer const&);
    big_integer(int a);
    explicit big_integer(std::string const&);
    ~big_integer();

    big_integer(big_integer&& other)
        : data()
        , isNegative(false)
    {
        data = other.data;
        isNegative = other.isNegative;
        other.data.resize(0);
        other.isNegative = false;
    }

    big_integer& operator=(big_integer const&);

    big_integer& operator+=(big_integer const&);
    big_integer& operator-=(big_integer const&);
    big_integer& operator*=(big_integer const&);
    big_integer& operator/=(big_integer const&);
    big_integer& operator%=(big_integer const&);

    big_integer& operator&=(big_integer const&);
    big_integer& operator|=(big_integer const&);
    big_integer& operator^=(big_integer const&);

    big_integer& operator<<=(unsigned);
    big_integer& operator>>=(unsigned);

    big_integer operator+() const;
    big_integer operator-() const;
    big_integer operator~() const;

    big_integer& operator++();
    big_integer operator++(int);

    big_integer& operator--();
    big_integer operator--(int);

    friend bool operator==(big_integer const&, big_integer const&);
    friend bool operator!=(big_integer const&, big_integer const&);
    friend bool operator<(big_integer const&, big_integer const&);
    friend bool operator>(big_integer const&, big_integer const&);
    friend bool operator<=(big_integer const&, big_integer const&);
    friend bool operator>=(big_integer const&, big_integer const&);

    friend std::string to_string(big_integer const&);

    static int abs_compare(big_integer const&, big_integer const&, int);
    static int compare(big_integer const&, big_integer const&);

    big_integer& normalize();
    big_integer& mul(uint32_t);
    big_integer& div(uint32_t);
    big_integer& add(big_integer const&);
    big_integer& sub(big_integer const&, int);
    uint32_t mod(uint32_t);

private:

    big_integer& neg();
    big_integer& unneg();

    my_vector data;
    bool isNegative;
};

big_integer operator+(big_integer, big_integer const&);
big_integer operator-(big_integer, big_integer const&);
big_integer operator*(big_integer, big_integer const&);
big_integer operator/(big_integer, big_integer const&);
big_integer operator%(big_integer, big_integer const&);

big_integer operator&(big_integer, big_integer const&);
big_integer operator|(big_integer, big_integer const&);
big_integer operator^(big_integer, big_integer const&);

big_integer operator<<(big_integer, int);
big_integer operator>>(big_integer, int);

bool operator==(big_integer const&, big_integer const&);
bool operator!=(big_integer const&, big_integer const&);
bool operator<(big_integer const&, big_integer const&);
bool operator>(big_integer const&, big_integer const&);
bool operator<=(big_integer const&, big_integer const&);
bool operator>=(big_integer const&, big_integer const&);

std::string to_string(big_integer const&);
std::ostream& operator<<(std::ostream&, big_integer const&);
std::istream& operator>>(std::istream&, big_integer&);

#endif // BIG_INTEGER_H
