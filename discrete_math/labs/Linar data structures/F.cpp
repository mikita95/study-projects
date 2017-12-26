
#include <fstream>
 
using namespace std;
ifstream cin("postfix.in");
ofstream cout("postfix.out");
 
const int size_start = 1;
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
    while (cin >> ch)
    {
        if (ch >= '0' && ch <= '9')
          push(ch - '0');
        else
        {
            int t1 = pop();
            int t2 = pop();
            if (ch == '+')
                push(t1 + t2);
            if (ch == '-')
                push(t2 - t1);
            if (ch == '*')
                push(t1 * t2);
        }
    }
    cout << pop();
    return 0;
}