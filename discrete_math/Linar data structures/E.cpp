#include <string>
#include <fstream>
using namespace std;
ifstream cin("brackets.in");
ofstream cout("brackets.out");
const int size_start = 10;
int n = 0, mysize = size_start;
char *mas = new char [size_start];
 
void push(char x)
{
    if (n == mysize - 1)
    {
        char *newmas = new char [2 * mysize];
        for (int i = 0; i < n; i++)
            newmas[i] = mas[i];
        delete [] mas;
        mas = newmas;
        mysize = mysize * 2;
    }
    n++;
    mas[n - 1] = x;
}
 
char pop()
{
    int t = n;
    n--;
    if (n < mysize / 4)
    {
        char *newmas = new char [mysize / 2];
        for (int i = 0; i < mysize / 4; i++)
            newmas[i] = mas[i];
        delete [] mas;
        mas = newmas;
        mysize  = mysize / 2;
    }
    return mas[t - 1];
}
 
bool isEmpty()
{
    return n == 0;
}
 
void Init()
{
    n = 0;
}
 
bool Check(string s)
{
    for (int i = 0; i < s.length(); i++)
    {
        if (s[i] == '(' || s[i] == '[')
            push(s[i]);
        else
        {
            if (!isEmpty())
            {
                char t = pop();
                if (s[i]==')' && ((t == '(' || t==')')) || (s[i]==']' && (t == '[' || t==']')))
                    continue;
                else
                    return false;
            }
            else
                return false;
 
        }
    }
    return isEmpty();
}
int main()
{
    string s;
    while (cin >> s)
    {
        if (Check(s))
            cout << "YES" << endl;
        else
            cout << "NO" << endl;
        Init();
    }
 
    return 0;
}