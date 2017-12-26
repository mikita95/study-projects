#include <iostream>
#include <cstdlib>

const int CUTOFF = 10;
const int MAX_DEPTH = 50;

void merge(int*, int*, int, int, int);
void mSort(int*, int*, int, int);
void mergeSort(int*, int, int);

std::pair<int, int> threeWayPartition(int*, int, int);
void qsort(int*, int, int, int);
void quickSort(int*, int, int);

void insertionSort(int*, int, int);
int myrand();
bool testing();

void mergeSort(int* data, int left, int right) {
    int tmp[right];
    mSort(data, tmp, left, right);
}

void mSort(int *a, int* b, int left, int right) {
    if(left < right) {
        int q = (left + right) / 2;
        mSort(a, b, left, q);
        mSort(a, b, q + 1, right);
        merge(a, b, left, q, right);
    }
}

void merge(int *a, int *b, int left, int q, int right) {
    int h = left;
    int i = left;
    int j = q + 1;
    while((h <= q) && (j <= right)) {
        if(a[h] <= a[j]) {
            b[i] = a[h++];
        } else {
            b[i] = a[j++];
        }
        i++;
    }
    if(h > q) {
        for(int k = j; k <= right; ++k) {
            b[i++] = a[k];
        }
    } else {
        for(int k = h; k <= q; ++k) {
            b[i++] = a[k];
        }
    }
    for(int k = left; k <= right; ++k)
        a[k] = b[k];
}

std::pair<int, int> threeWayPartition(int* data, int left, int right) {
    int lt = left, gt = right;
    int v = data[left];
    int i = left;
    while (i <= gt) {
        if (data[i] < v)
            std::swap(data[lt++], data[i++]);
        else
            if (data[i] > v)
                std::swap(data[i], data[gt--]);
            else
                i++;
    }
    return std::make_pair(lt, gt);
}

void qsort(int *data, int left, int right, int depth)
{
    if (left >= right)
        return;
    if (depth > MAX_DEPTH) {
        mergeSort(data, left, right);
        return;
    }
    while (right - left >= CUTOFF) {
        std::pair<int, int> q = threeWayPartition(data, left, right);
        if (q.first - left < right - q.second) {
            if (q.first - left > CUTOFF)
                qsort(data, left, q.first - 1, depth + 1);
            left = q.second + 1;
        } else {
            if (right - q.second > CUTOFF)
                qsort(data, q.second + 1, right, depth + 1);
            right = q.first - 1;
        }
    }
}

void quickSort(int *data, int left, int right) {
    qsort(data, left, right, 0);
    insertionSort(data, left, right);
}

void insertionSort(int* data, int left, int right) {
    for (int i = left; i <= right; ++i) {
        int j = i - 1;
        while (j >= 0 && data[j] > data[j + 1] ) {
            std::swap(data[j], data[j + 1]);
            j--;
        }
    }
}

int myrand() {
    int val = rand() - RAND_MAX / 2;
    if (val != 0)
        return val;
    else
        return 1;
}

bool testing() {
    const int NUMBER = 100000;
    int test[NUMBER];
    for (int i = 0; i < NUMBER; ++i) {
        test[i] = myrand();
    }
    quickSort(test, 0, NUMBER - 1);
    for (int i = 0; i < NUMBER - 1; ++i) {
        if (test[i + 1] < test[i])
            return false;
    }
    return true;
}

int main()
{
    std::cout << "testing... ";
    if (testing())
        std::cout << "OK";
    else
        std::cout << "FAIL";
    return 0;
}
