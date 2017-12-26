#include <stdio.h>
using namespace std;
int i,k,n,m,t;
int main()
{
    int d[200002];
    FILE *f;
    f=fopen("countpaths.in","rt");
    fscanf(f,"%d%d\n",&n,&m);
    d[1]=1;
    for (i=0;i<m;i++)
    {
        fscanf(f,"%d%d\n",&k,&t);
        d[t]=(d[k]+d[t])%1000000007;
    }
    fclose(f);
    f=fopen("countpaths.out","wt");
    fprintf(f,"%d",d[n]);
    return 0;
}