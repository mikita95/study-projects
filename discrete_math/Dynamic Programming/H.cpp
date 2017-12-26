#include <fstream>
#include <vector>
using namespace std;
ifstream cin("matching.in");
ofstream cout("matching.out");
const int maxn = 100003;
pair <int,int> my;
vector<pair<int,int> > g[maxn];
long long a[maxn], c[maxn], b[maxn], used[maxn];
int n;
 
void dfs(int x)
{
    int i;
    used[x] = 1;
    for (i = 0; i < g[x].size(); i++)
        if (used[g[x][i].first] == 0)
        {
            dfs(g[x][i].first);
            a[x] = max(a[x], b[g[x][i].first] + g[x][i].second - c[g[x][i].first]);
            b[x] += c[g[x][i].first];
        }
    a[x] += b[x];
    c[x] = max(a[x], b[x]);
}
int main()
{
    int t, m, y, i, j;
    cin >> n;
    for (i = 0;i < n - 1; i++)
    {
        cin >> t >> m >> y;
        my.first = m - 1;
        my.second = y;
        g[t - 1].push_back(my);
        my.first = t - 1;
        g[m - 1].push_back(my);
        used[t - 1] = 0;
        used[m - 1] = 0;
    }
   //used[n]=0;
  // used[0]=1;
    dfs(0);
    cout<<c[0];
}