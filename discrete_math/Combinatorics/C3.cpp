
#include <stdio.h>
#include <vector>
using namespace std;
FILE *f;
 
void main()
{   vector<int> s,r,p;
    int i,n,k,t,min;
    f = fopen("nextchoose.in", "rt");
    fscanf(f, "%d%d\n", &n,&k);
    r.push_back(10001);
    for (i=0;i<k;i++)
    {
        fscanf(f,"%d",&t);
        r.push_back(t);
    }
    fclose(f);
    f = fopen("nextchoose.out", "wt");
    t=-1;
    r.push_back(n+1);
    for (i=k;i>0;i--)
    {
        if ((r[i]+1<=n) && (r[i+1]!=r[i]+1))
        {
            t=i;
            break;
        }
    }
    if (t==-1)
        fprintf(f,"%d",-1);
    else
    {
        r[t]++;
        for (i=t+1;i<=k;i++)
            r[i]=r[i-1]+1;
        for (i=1;i<=k;i++)
            fprintf(f,"%d ",r[i]);
    }
    fclose(f);
}