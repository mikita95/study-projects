#include <fstream>
#include <string>
#include <vector>
using namespace std;
ifstream cin("priorityqueue.in");
ofstream cout("priorityqueue.out");
 
const int maxn = 1000000;
 
int *data;
int heapSize = 0;
int arraySize = 0;
int ncnt = 0;
 
int getLeftChildIndex(int nodeIndex)
{
    return 2 * nodeIndex;
}
 
int getRightChildIndex(int nodeIndex)
{
    return 2 * nodeIndex + 1;
}
 
int getParentIndex(int nodeIndex)
{
    return (nodeIndex) / 2;
}
 
int getIndex(int element)
{
    for (int i = 1; i < heapSize; i++)
    {
        if (data[i] == element)
        {
            return i;
        }
    }
}
 
void BinaryMinHeap(int size)
{
    data = new int[size];
    heapSize = 1;
}
 
int siftDown(int nodeIndex)
{
    int tmp;
    while (getLeftChildIndex(nodeIndex) < heapSize)
    {
        if (getRightChildIndex(nodeIndex) < heapSize && data[getRightChildIndex(nodeIndex)] < data[getLeftChildIndex(nodeIndex)])
            tmp = getRightChildIndex(nodeIndex);
        else
            tmp = getLeftChildIndex(nodeIndex);
 
        if (data[tmp] < data[nodeIndex])
        {
            int tmp1 = data[nodeIndex];
            data[nodeIndex] = data[tmp];
            data[tmp] = tmp1;
            nodeIndex = tmp;
        }
        else
            break;
    }
    return nodeIndex;
}
 
void siftUp(int nodeIndex)
{
    while (nodeIndex - 1 > 0)
    {
        if (data[getParentIndex(nodeIndex)] > data[nodeIndex])
        {
            int tmp = data[getParentIndex(nodeIndex)];
            data[getParentIndex(nodeIndex)] = data[nodeIndex];
            data[nodeIndex] = tmp;
            nodeIndex = getParentIndex(nodeIndex);
        }
        else
            break;
    }
}
 
void Insert(int value)
{
    data[heapSize] = value;
    siftUp(heapSize);
    heapSize++;
}
 
void extractMin()
{
    if (heapSize > ncnt + 1)
    {
        int t = data[1];
        data[1] = ~0U >> 1;
        siftDown(1);
        ncnt++;
        cout << t << endl;
    }
    else
        cout << "*" << endl;
}
 
void decreaseKey(int key, int value)
{
    int t = getIndex(key);
    data[t] = value;
    siftUp(t);
}
 
string st;
vector<int> mas;
 
void Scanner(int cc)
{
    mas.push_back(cc);
    while (cin >> st)
    {
        if (st == "push")
        {
            int a;
            cin >> a;
            Insert(a);
            mas.push_back(a);
        } else
        if (st == "extract-min")
        {
            extractMin();
            mas.push_back(0);
        } else
        if (st == "decrease-key")
        {
            int t1, t2;
            cin >> t1 >> t2;
            decreaseKey(mas[t1], t2);
            mas[t1] = t2;
            mas.push_back(0);
        }
    }
}
 
int main()
{
    BinaryMinHeap(maxn);
    Scanner(0);
    return 0;
}