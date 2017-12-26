#include <fstream>
 
using namespace std;
ifstream cin("queue1.in");
ofstream cout("queue1.out");
 
const int size_start = 1;
int n = 0, mysize = size_start, head = 0, tail = 0;
int *mas = new int [size_start];
 
void push(int x)
{
    if (n == mysize)
    {
        int *newmas = new int [mysize * 2];
        for (int i = 0; i < tail; i++)
            newmas[i] = mas[i];
        delete [] mas;
        mas = newmas;
        mysize = mysize * 2;
        n++;
        mas[tail] = x;
        tail++;
        return;
    }
    int *newmas = new int [mysize];
    for (int i = head; i < tail; i++)
        newmas[i - head] = mas[i];
    delete [] mas;
    mas = newmas;
    head = 0;
    tail = n;
    n++;
    mas[tail] = x;
    tail++;
    return;
}
 
int pop()
{
    n--;
    if (n < mysize / 2)
    {
        int t = mas[head];
 
        int *newmas = new int [mysize / 2];
        for (int i = head + 1 ; i < tail; i++)
            newmas[i - head - 1] = mas[i];
        delete [] mas;
        mas = newmas;
        mysize = mysize / 2;
        head = 0;
        tail = n;
        return t;
    }
    head++;
    return mas[head - 1];
}
 
int main()
{
    char ch;
    int m;
    int k;
    cin >> m;
    for (int i = 0; i < m; i++)
    {
        cin >> ch;
        if (ch == '+')
        {
            cin >> k;
            push(k);
            //cout << mysize << " " << n <<" " << head << " " << tail << endl;
        }
        if (ch == '-')
            cout << pop() << endl;
            //cout << mysize << " " << n <<" " << head << " " << tail << endl;}
    }
    return 0;
}