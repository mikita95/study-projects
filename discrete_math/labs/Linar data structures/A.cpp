#include <fstream>
 
using namespace std;
ifstream cin("stack1.in");
ofstream cout("stack1.out");
const int size_start = 10;
int n = 0, mysize = size_start;
int *mas = new int [size_start];
 
void push(int x)
{
    if (n == mysize - 1)
    {
        int *newmas = new int [2 * mysize];
        for (int i = 0; i < n; i++)
            newmas[i] = mas[i];
        delete [] mas;
        mas = newmas;
        mysize = mysize * 2;
    }
    n++;
    mas[n - 1] = x;
}
 
int pop()
{
    int t = n;
    n--;
    if (n < mysize / 4)
    {
        int *newmas = new int [mysize / 2];
        for (int i = 0; i < mysize / 4; i++)
            newmas[i] = mas[i];
        delete [] mas;
        mas = newmas;
        mysize  = mysize / 2;
    }
    return mas[t - 1];
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
        }
        if (ch == '-')
            cout << pop() << endl;
    }
    return 0;
}