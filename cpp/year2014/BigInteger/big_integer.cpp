#include "big_integer.h"
#include <sstream>
#include <iostream>
#include <stdexcept>

//const uint32_t UINT32_MAX = 4294967295;
const short bits = 32;

big_integer::big_integer() {
    data.push_back(0);
}

big_integer::big_integer(big_integer const& other) {
    *this = other;
}

big_integer::big_integer(big_integer&& other) {
    isNegative = other.isNegative;
    data = std::move(other.data);
}

big_integer::big_integer(int a) {
    if (a < 0)
        isNegative = true;
    else
        isNegative = false;

    if (a < 0)
        data.push_back(-a);
    else
        data.push_back(a);
}

const big_integer ZERO = big_integer(0);

big_integer::big_integer(std::string const& str) {
    try {
        *this = ZERO;
        for (int i = 0 + (str[0] == '-'); i < (int) str.size(); i++)
            if (str[i] >= '0' && str[i] <= '9' )
                *this = *this * 10 + (str[i] - '0');
            else
                throw i;

        if (str[0] == '-')
            isNegative = true;
     } catch (int e) {
            std::cerr << std::endl << "symbol " << e + 1 << " is invalid ";
            throw std::invalid_argument("");
     }
}

big_integer::operator std::string() const {

    big_integer value = (*this);
    value.isNegative = false;
    std::string result;
    if (value == 0) {
        return "0";

    }
    while (value > 0)
    {
        big_integer m = (value % 10);
        uint32_t x = m.data.back();
        value /= 10;
        std::stringstream ss;
        ss << x;
        result += ss.str();
    }
    if (isNegative) {
        result += '-';
    }
    std::reverse(result.begin(), result.end());
    return result;
}

big_integer::~big_integer() {
}

big_integer& big_integer::normalize() {
    int newSize = data.size();
    int i = newSize - 1;
    while (i >= 0) {
        if (data[i] == 0) {
            newSize--;
        } else {
            break;
        }
        i--;
    }

    if (newSize == 0) {
        *this = ZERO;
    } else {
        data.resize(newSize);
    }
    return *this;
}


big_integer& big_integer::operator=(big_integer const& other) {
    data = other.data;
    isNegative = other.isNegative;
    return *this;
}

big_integer& big_integer::operator<<=(unsigned b) {
    int before = 0, after;
    int k = b % bits;
    int t = b / bits;
    data.resize(data.size() + (t) + 1);
    for (int i = 0; i < (int) (*this).data.size(); i++) {
        after = before;
        before = (( (unsigned) (-1) << (bits - k)) & data[i]) >> (bits - k);
        data[t + i] = (data[i] << k) | after;
    }
    if (k)
        data[data.size() + t] |= before;
    if (isNegative )
        data.back() |= ((unsigned) (-1) << (k));
    return normalize();

}

big_integer& big_integer::neg() {
    for (int i = 0; i < (int) data.size(); i++) {
        data[i] = ~data[i];
    }
    *this = *this - 1;
    return *this;
}

big_integer& big_integer::unneg() {
    if (data.back() >> (bits - 1)) {
        isNegative = true;
        neg();
    }
    return *this;
}

big_integer& big_integer::add(big_integer const& b) {
    uint64_t carry = 0, value;
    int newSize = std::max(data.size(), b.data.size());
    data.resize(newSize);
    for (int i = 0; i < newSize; i++) {
        value = (uint64_t)data[i] + (uint64_t)b.data[i] + carry;
        carry = value >> bits;
        data[i] = value;
    }
    if (carry != 0) {
        data.push_back(1);
    }
    return *this;
}

big_integer& big_integer::sub(big_integer const& b, int sh) {
    isNegative = false;
    int64_t carry = 0, value;
    int newSize = b.data.size();
    for (int i = 0; i < newSize; i++) {
        value = (uint64_t)data[i + sh] - carry - (uint64_t)b.data[i];
        carry = (uint64_t)value >> 63;
        value += carry << bits;
        data[i + sh] = value;
    }
    if (carry != 0) {
        data[newSize + sh]--;
    }
    return *this;
}

big_integer& big_integer::operator+=(big_integer const& b) {
    big_integer right = b;
    if (compare(*this, 0) <= 0 && compare(b, 0) <= 0) {
        isNegative = false;
        right.isNegative = false;
        add(right);
        isNegative = true;
    } else
        if (compare(*this, 0) >= 0 && compare(b, 0) >= 0) {
            add(b);
        }
        else
            if (abs_compare(*this, b, 0) <= 0 && compare(*this, 0) <= 0) {
                *this = right.sub(*this, 0);
            }
            else
                if (abs_compare(*this, b, 0) <= 0 && compare(*this, 0) >= 0) {
                    right.isNegative = false;
                    right.sub(*this, 0);
                    right.isNegative = true;
                    *this = right;
                }
                else
                    if (abs_compare(*this, b, 0) >= 0 && compare(*this, 0) <= 0) {
                        sub(b, 0);
                        isNegative = false;
                    }
                    else
                    {
                        sub(b, 0);
                    }
    return normalize();
}

big_integer& big_integer::operator-=(big_integer const& b) {
    return *this += -b;
}

big_integer& big_integer::mul(uint32_t b) {
    uint64_t carry = 0;
    for (size_t i = 0; i < data.size(); i++) {
        uint64_t result = (uint64_t)data[i] * (uint64_t)b + carry;
        carry = result >> bits;
        data[i] = result;
    }
    if (carry != 0) {
        data.push_back(carry);
    }
    return normalize();
}

big_integer& big_integer::operator*=(big_integer const& b) {
    big_integer source = *this;
    *this = ZERO;
    isNegative = source.isNegative ^ b.isNegative;
    uint64_t carry, new_data;
    size_t as = source.data.size(), bs = b.data.size();
    data.resize(as + bs);
    for (size_t i = 0; i < as; i++) {
        carry = 0;
        for (size_t j = 0; j < bs; j++) {
            new_data = (uint64_t)source.data[i] * (uint64_t)b.data[j] + (uint64_t)data[i + j] + carry;
            carry = new_data >> bits;
            data[i + j] = new_data;
        }
        data[i + bs] = carry;
    }
    return normalize();
}

big_integer& big_integer::div(uint32_t b) {
    uint64_t carry = 0;
    for (int64_t i = data.size() - 1; i >= 0; i--) {
        uint64_t new_data = (carry << bits) + data[i];
        data[i] = new_data / b;
        carry = new_data % b;
    }
    return normalize();
}


big_integer& big_integer::operator/=(big_integer const& b) {
    if (b.data.size() == 1) {
        isNegative =isNegative ^ b.isNegative;
        div(b.data[0]);
        return *this;
    }
    big_integer value = *this;
    big_integer right = b;
    *this = ZERO;
    isNegative = value.isNegative ^ right.isNegative;
    right.isNegative = value.isNegative = false;
    if (value.data.size() <= right.data.size() - 1 ) {
        *this = ZERO;
        return *this;
    }
    data.resize(value.data.size() - right.data.size() + 1);
    big_integer tmp;
    unsigned c = 1LL << (bits - 1);
    unsigned long long c1 = 1LL << bits;
    if (right.data[right.data.size() - 1] < c) {
        unsigned d = (c1) / (right.data[right.data.size() - 1] + 1);
        value *= d;
        right *= d;
    }
    int result = 0;
    for (int i = value.data.size() - right.data.size() + 1 - 1; i >= 0; i--) {
        int vsize = value.data.size(), rsize = right.data.size();
        unsigned q = 0;
        unsigned u1 = value.data[vsize - 1];
        unsigned u2 = value.data[vsize - 2];
        unsigned u3 = value.data[vsize - 3];
        unsigned v1 = right.data[rsize - 1], v2 = right.data[rsize - 2];
        unsigned long long u;
        if (u1 > v1)
            u = u1;
        else
            u = ((unsigned long long)u1 << bits) + u2;
        unsigned long long stq = std::min(u / v1, (unsigned long long)UINT32_MAX);
        unsigned long long str = u % v1;
        int it = 0;
        do {
            if (stq == c1 || stq * v2 > (str << bits) + u3) {
                stq--;
                str += v1;
            }
        } while(str < c1 && ++it < 2);
        q = stq;
        tmp = right;
        tmp.mul(q);
        result = abs_compare(value, tmp, i);
        if (result >= 0) {
            value.sub(tmp, i);
            value.normalize();
            data[i] = q;
            if (result == 0) {
                break;
            }
        } else {
            data[i] = 0;
        }
    }
    return normalize();
}


big_integer& big_integer::operator%=(big_integer const& b) {
    return *this -= *this / b * b;
}

big_integer& big_integer::operator&=(big_integer const& b) {
    bool flag = false;
    big_integer right = b;

    int size = std::max(data.size(), right.data.size());

    data.resize(size);
    right.data.resize(size);
    if (isNegative) {
        flag = true;
        neg();
    }
    if (right.isNegative) {
        flag = true;
        right.neg();
    }
    for (int i = 0; i < size; i++) {
        data[i] &= right.data[i];
    }
    if (isNegative)
        neg();
    if (flag)
        unneg();
    return normalize();
}

big_integer& big_integer::operator|=(big_integer const& b) {
    bool flag = false;
    big_integer right = b;
    int size = std::max(data.size(), right.data.size());
    data.resize(size);
    right.data.resize(size);
    if (isNegative) {
        flag = true;
        neg();
    }
    if (right.isNegative) {
        flag = true;
        right.neg();
    }
    for (int i = 0; i < size; i++) {
        data[i] |= right.data[i];
    }
    if (isNegative)
        neg();
    if (flag)
        unneg();
    return normalize();
}

big_integer& big_integer::operator^=(big_integer const& b) {
    bool flag = false;
    big_integer right = b;
    int size = std::max(data.size(), right.data.size());
    data.resize(size);
    right.data.resize(size);
    if (isNegative) {
        flag = true;
        neg();
    }
    if (right.isNegative) {
        flag = true;
        right.neg();
    }
    for (int i = 0; i < size; i++) {
        data[i] ^= right.data[i];
    }
    if (isNegative)
        neg();
    if (flag)
        unneg();
    return normalize();
}

big_integer& big_integer::operator>>=(unsigned b) {
    int before = 0, after;
    bool flag = false;
    if (isNegative)
        flag = true;
    int k = b % bits;
    int t = b / bits;
    data.resize(data.size() - (t));
    for (int i = data.size() - 1; i >= 0; i--) {
        after = before;
        before = (( (unsigned) (-1) >> (bits - k)) & data[i]) << (bits - k);
        data[t - i] = (data[i] >> k) | after;
    }
    if (k)
        data[data.size() - t] |= before;
    if (flag)
        (*this)--;
    return normalize();

}

big_integer big_integer::operator+() const {
    return *this;
}

big_integer big_integer::operator-() const {
    big_integer ans = *this;
    ans.isNegative = !isNegative;
    return ans;
}

big_integer big_integer::operator~() const {
    big_integer ans = -*this;
    --ans;
    return ans;
}

big_integer& big_integer::operator++() {
    *this += 1;
    return *this;
}

big_integer big_integer::operator++(int) {
    big_integer r = *this;
    ++*this;
    return r;
}

big_integer& big_integer::operator--() {
    *this -= 1;
    return *this;
}

big_integer big_integer::operator--(int) {
    big_integer r = *this;
    --*this;
    return r;
}

big_integer operator+(big_integer a, big_integer const& b) {
    return a += b;
}

big_integer operator-(big_integer a, big_integer const& b) {
    return a -= b;
}

big_integer operator*(big_integer a, big_integer const& b) {
    return a *= b;
}

big_integer operator/(big_integer a, big_integer const& b) {
    return a /= b;
}

uint32_t big_integer::mod(uint32_t x) {
    big_integer tmp = *this;
    tmp.div(x);
    tmp.mul(x);
    return (*this - tmp).data[0];
}

big_integer operator%(big_integer a, big_integer const& b) {
    return a %= b;
}

big_integer operator&(big_integer a, big_integer const& b) {
    return a &= b;
}

big_integer operator|(big_integer a, big_integer const& b) {
    return a |= b;
}

big_integer operator^(big_integer a, big_integer const& b) {
    return a ^= b;
}

big_integer operator<<(big_integer a, int b) {
    return a <<= b;
}

big_integer operator>>(big_integer a, int b) {
    return a >>= b;
}

int big_integer::abs_compare(big_integer const& a, big_integer const& b, int sh) {
    if (a.data.size() > b.data.size() + sh) {
        return 1;
    }
    if (a.data.size() < b.data.size() + sh) {
        return -1;
    }
    for (int i = a.data.size() - 1; i >= sh; i--) {
        if (a.data[i] > b.data[i - sh]) {
            return 1;
        }
        if (a.data[i] < b.data[i - sh]) {
            return -1;
        }
    }
    for (int i = sh - 1; i >= 0; i--) {
        if (a.data[i] > 0) {
            return 1;
        }
    }
    return 0;
}

int big_integer::compare(big_integer const& a, big_integer const& b) {
    int result = abs_compare(a, b, 0);
    if ((a.isNegative && b.isNegative) || (result > 0 && a.isNegative) || (result < 0 && b.isNegative)) {
        return -result;
    } else {
        return result;
    }
}

bool operator==(big_integer const& a, big_integer const& b) {
    return big_integer::compare(a, b) == 0;
}

bool operator!=(big_integer const& a, big_integer const& b) {
    return big_integer::compare(a, b) != 0;
}

bool operator<(big_integer const& a, big_integer const& b) {
    return big_integer::compare(a, b) < 0;
}

bool operator>(big_integer const& a, big_integer const& b) {
    return big_integer::compare(a, b) > 0;
}

bool operator<=(big_integer const& a, big_integer const& b) {
    return big_integer::compare(a, b) <= 0;
}

bool operator>=(big_integer const& a, big_integer const& b) {
    return big_integer::compare(a, b) >= 0;
}

std::ostream& operator<<(std::ostream& s, big_integer const& a) {
    return s << (std::string)a;
}

std::istream& operator>>(std::istream &is, big_integer &a) {
    std::string s;
    is >> s;
    a = big_integer(s);
    return is;
}

