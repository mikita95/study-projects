#include <fstream>
#include <string>
 
using namespace std;
ifstream fin("dsu.in");
ofstream fout("dsu.out");
const int maxn = 100002;
using namespace std;
int n;
struct tmt
{
    int mymin;
    int mymax;
    int cnt;
};
int s[maxn];
int myrank[maxn];
tmt gt[maxn];
 
int setOf(int x) {
    return x == s[x] ? x : (s[x] = setOf(s[x]));
}
 
void update(int i, int j)
{
    if (gt[i].mymax < gt[j].mymax)
        gt[i].mymax = gt[j].mymax;
    if (gt[i].mymin > gt[j].mymin)
        gt[i].mymin = gt[j].mymin;
    gt[i].cnt += gt[j].cnt;
}
 
void myunion(int i, int j) {
    if ((i = setOf(i)) == (j = setOf(j)))
        return;
    if (myrank[i] > myrank[j]) {
        s[j] = i;
        update(i, j);
    }
    else
    {
        s[i] = j;
        update(j, i);
        if (myrank[i] == myrank[j])
            myrank[j]++;
    }
    return;
}
 
void get(int x)
{
    fout << gt[setOf(x)].mymin << ' ' << gt[setOf(x)].mymax << ' ' << gt[setOf(x)].cnt << endl;
}
 
int main()
{
    fin >> n;
    for (int i = 1; i <= n; i++)
        {
            s[i] = i;
            gt[i].mymin = i;
            gt[i].mymax = i;
            gt[i].cnt = 1;
        }
    string p;
    while (fin >> p)
    {
        if (p == "union")
        {
            int a,b;
            fin >> a >> b;
            myunion(a, b);
        }
        else
        {
            int a;
            fin >> a;
            get(a);
        }
    }
}