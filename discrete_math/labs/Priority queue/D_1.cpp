#include <fstream>
#include <string>
#include <vector>
#include <math.h>
using namespace std;
ifstream cin("parking.in");
ofstream cout("parking.out");
 
const int maxn = 270000;
int n, n1;
pair<int, int> data[maxn];
pair<int, int> tree[2 * maxn];
 
pair<int, int>  opn(pair<int, int>  a, pair<int, int>  b)
{
    if (a.second == 1 && b.second == 1)
        {if (a.first <= b.first)
            return a;
        else
            return b;}
    if (a.second == 0 && b.second == 1)
        return a;
    if (a.second == 1 && b.second == 0)
        return b;
    if (a.second == 0 && b.second == 0)
        {if (a.first <= b.first)
            return a;
        else
            return b;}
}
 
void build()
{
    n = (int)(log(n - 1) / log(2)) + 1;
    n = 1 << n;
 
    for (int  i = 0; i < n1; ++i)
        tree[i + n] = data[i];
 
    for (int i = n1; i < 2 * n; ++i)
        tree[i + n] = make_pair(i + 1, 1);
 
    for (int i = n - 1; i > 0; --i)
        tree[i] = opn(tree[2 * i], tree[2 * i + 1]);
 
}
 
pair<int, int>  getopn(int l, int r)
{
    pair<int, int>  res;
    l = l + n - 1;
    r = n + r - 1;
    res = make_pair(n + 1, 1);
    while (l <= r)
    {
        if (l % 2 == 1)
            res  = opn(res, tree[l]);
        if (r % 2 == 0)
            res  = opn(res, tree[r]);
        l = (l + 1) / 2;
        r = (r - 1) / 2;
    }
    return res;
}
 
void modify(int i, pair<int, int> v)
{
    i = i + n - 1;
    tree[i] = v;
    while (i /= 2)
    {
        tree[i] = opn(tree[2 * i], tree[2 * i + 1]);
    }
}
 
string st;
int m, a;
int main()
{
    cin >> n1 >> m;
    for (int i = 0; i < n1; i++)
        data[i] = make_pair(i + 1, 0);
    n = n1;
    build();
    for (int i = 0; i < m; i++)
    {
        cin >> st >> a;
        if (st == "exit")
        {
            modify(a, make_pair(a, 0));
        } else
        {
            pair<int, int>  c = getopn(a, n1);
            if (c.second == 0)
            {
                cout << c.first << endl;
                modify(c.first, make_pair(c.first, 1));
            }
            else
                {
                    c  = getopn(1, a - 1);
                    cout << c.first << endl;
                    modify(c.first, make_pair(c.first, 1));
                }
        }
    }
 
    return 0;
}