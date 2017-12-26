#include "my_vector.h"

my_vector::my_vector() {

    in_vector = false;

    data.set_const(small_data);
    data_size = SMALL_SIZE;
    memset(data.get(), 0, data_size * sizeof(unsigned));
    left = 0;
    right = 0;
}

my_vector::my_vector(my_vector const& other) {
    *this = other;
}

my_vector::~my_vector() {
    data.reset();
}

void my_vector::cow_fork() {
    if (data.get_count() > 1) {
        unsigned* forked_data = new unsigned[data_size];
        memcpy(forked_data, data.get(), data_size * sizeof(unsigned));
        data.reset();
        data.set(forked_data);
    }
}

my_vector& my_vector::operator=(my_vector const& other) {
    if (this != &other) {
        left = other.left;
        right = other.right;
        data_size = other.data_size;
        data.reset();
        if (other.in_vector) {
            data = other.data;
        } else {
            memcpy(small_data, other.small_data, data_size * sizeof(unsigned));
            data.set_const(small_data);
        }
        in_vector = other.in_vector;
    }
    return *this;
}

void my_vector::resize(size_t desired_size) {
    size_t new_size = 0, cur_size = size();
    if (!in_vector && left + desired_size > SMALL_SIZE) {
        data_size = desired_size << 1;
        data.reset();
        data.set(new unsigned[data_size]);
        memcpy(data.get(), &small_data[left], cur_size * sizeof(unsigned));
        memset(&data[cur_size], 0, (data_size - cur_size) * sizeof(unsigned));
        left = 0;
        right = desired_size;
        in_vector = true;
        return;
    } else if (in_vector && left + desired_size > data_size) {
        new_size = desired_size + (desired_size >> 2) + 1;
        right = cur_size;
    } else if (in_vector && desired_size <= SMALL_SIZE) {
        data_size = SMALL_SIZE;
        size_t new_size = std::min(cur_size, desired_size);
        memcpy(small_data, &data[left], new_size * sizeof(unsigned));
        memset(&small_data[new_size], 0, (SMALL_SIZE - new_size) * sizeof(unsigned));
        data.reset();
        data.set_const(small_data);
        left = 0;
        right = desired_size;
        in_vector = false;
        return;
    } else if (in_vector && desired_size << 2 <= data_size) {
        new_size = data_size >> 1;
        right = std::min(cur_size, new_size);
    } else {
        right = left + desired_size;
        return;
    }
    unsigned* new_data = new unsigned[new_size];
    memcpy(new_data, &data[left], right * sizeof(unsigned));
    memset(&new_data[right], 0, (new_size - right) * sizeof(unsigned));
    left = 0;
    right = desired_size;
    data.reset();
    data.set(new_data);
    data_size = new_size;
}

unsigned& my_vector::operator[](int i) {
    cow_fork();
    return data[left + i];
}

unsigned my_vector::operator[](int i) const {

    return data[left + i];
}

unsigned& my_vector::front() {
    return data[left];
}

void my_vector::pop_front(int count) {
    cow_fork();
    left = std::min(left + count, right);
    resize(size());
}

void my_vector::push_front(int count, unsigned x) {
    size_t old_right = right;
    cow_fork();
    resize(size() + count);
    for (int i = old_right - 1; i >= (int)left; i--) {
        data[i + count] = data[i];
    }
    for (int i = 0; i < count; i++) {
        data[i + left] = x;
    }
}

unsigned& my_vector::back() {
    return data[right - 1];
}

void my_vector::push_back(unsigned x) {
    resize(size() + 1);
    back() = x;
}

size_t my_vector::size() const {
    return right - left;
}
