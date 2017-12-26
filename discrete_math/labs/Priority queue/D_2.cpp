#include <fstream>
#include <string>
#include <vector>
using namespace std;
ifstream cin("parking.in");
ofstream cout("parking.out");
const int maxn = 300002;
using namespace std;
int n, a;
struct tmt
{
    int next_e;
    int back_e;
    int head;
};
tmt s[maxn];
int myrank[maxn];

int setOf(int x) {
    return x == s[x].head ? x : (s[x].head = setOf(s[x].head));
}

void update(int i)
{
    s[s[i].back_e].next_e = s[i].next_e;
    s[s[i].next_e].back_e = s[i].back_e;

}

void myunion(int i, int j) {
    if ((i = setOf(i)) == (j = setOf(j)))
        return;
    if (myrank[i] > myrank[j]) {
        s[j].head = i;
        update(j);
    }
    else
    {
        s[i].head = j;
        update(i);
        if (myrank[i] == myrank[j])
            myrank[j]++;
    }
    return;
}

int main()
{
    cin >> n;
    for (int i = 1; i <= n; i++)
    {
        s[i].head = i;
        if (i != n)
            s[i].next_e = i + 1;
        else
            s[i].next_e = 1;
        if (i != 1)
            s[i].back_e = i - 1;
        else
            s[i].back_e = n;
    }
    s[0].head = 0;
    s[0].back_e = 0;
    s[0].next_e = 0;
    for (int i = 0; i < n; i++)
    {
        cin >> a;
        if (setOf(a) != 0)
        {
            cout << a << ' ';
            myunion(a, 0);
        }
        else
        {
            int k1 = s[a].back_e;
            int k2 = s[a].next_e;
            while (setOf(k1) == 0 && setOf(k2) == 0)
                {
                    k1 = s[k1].back_e;
                    k2 = s[k2].next_e;
                }
            if (setOf(k1) != 0)
            {
                cout << s[k1].next_e << ' ';
                myunion(s[k1].next_e, 0);
            }
            else
            {
                cout << k2 << ' ';
                myunion(k2, 0);
            }

        }

    }
    return 0;
}
