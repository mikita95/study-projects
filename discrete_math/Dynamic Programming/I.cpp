#include <stdio.h>
#include <fstream>
#include <math.h>
#include <vector>
 
using namespace std;
FILE *f,*g;
const long long inf = 1E11;
const long long maxn = 20;
int r, n, i, j, k, m;
//vector<pair<int,int> > gr[maxn][maxn];
int grath[maxn][maxn];
long long dinam[ 1 << maxn ][ maxn ];
long long ans = 0;
pair<int,int> my;
 
void MyScanf()
{
    f = fopen("salesman.in","rt");
    g = fopen("salesman.out","wt");
    fscanf(f, "%d%d\n", &n, &r);
    for (i = 0; i <= n + 1; i++)
        for(i = 0;i <= n + 1; i++)
            grath[i][j]=0;
    for(i = 0; i < r ; i++)
    {
        int t1, t2, t3;
        fscanf(f,"%d%d%d\n",&t1, &t2, &t3);
        grath[t1][t2] = t3;
        grath[t2][t1] = t3;
    }
}
 
void Init()
{
    n++;
    dinam[1][0] = 0;
    m = 1 << n;
    for(i = 1; i <= n; i++)
    {
        grath[i][0] = 1;
        grath[0][i] = 1;
    }
}
 
void MySearch()
{
    int mask;
    for(i = 1; i < m; i += 2)
    {
        int pt;
        if (i == 1)
            pt = 1;
        else
            pt = 0;
        for (j = pt; j < n; ++j)
        {
            dinam[i][j] = inf;
            if (j > 0 && (i & (1 << j )) != 0)
            {
                mask = i^(1<<j);
                for (k = 0; k < n; ++k)
                    if ((i & (1 << k )) != 0 && grath[j][k] > 0)
                        dinam[i][j] = min( dinam[i][j], grath[j][k] + dinam[mask][k]);
            }
        }
    }
    for (ans = inf, j = 1 ;j < n; ++j)
        if (grath[0][j] > 0)
            ans = min(ans, dinam[m - 1][j] + grath[0][j] );
}
 
void MyPrintf()
{
    if (ans == inf)
        fprintf(g, "-1");
    else
    {
        ans = ans - 2;
        fprintf(g, "%lld", ans);
    }
    fclose(f);
    fclose(g);
}
 
int main()
{
    MyScanf();
    Init();
    MySearch();
    MyPrintf();
    return 0;
}