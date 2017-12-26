#include <stdio.h>
#include <vector>
#include <algorithm>
using namespace std;
FILE *f;
int n,m;
int p[20];
void myprint(int block[100])
{
    int tmt[100];
    int i,j,c=0,l;
    for (i=1;i<=n;i++)
        tmt[i]=block[i];
     
    sort(tmt+1,tmt+n+1);
    for (i=2;i<=n;i++)
        if (tmt[i-1]!=tmt[i])
            c++;
    c++;
    int flag;
     
    if (c==m)
    {
        for (i=1;i<=n;i++)
        {
            flag=0;
            for (j=1;j<=n;j++)
                if (block[j]==i)
                {
                    flag=1;
                    fprintf(f,"%d ",j);
                }
            if (flag==1)
                fprintf(f,"\n");
        }
        fprintf(f,"\n");
    }
 
}
 
 
void main()
{   int block[100],next[100],prev[100];
    bool tow[100];
    int i,k,t,j,min;
    f = fopen("part2sets.in", "rt");
    fscanf(f, "%d%d", &n,&m);
    fclose(f);
    f = fopen("part2sets.out", "wt");
    for (i=1;i<=n;i++)
    {
        block[i]=1;
        tow[i]=true;
    }
    next[1]=0;
    myprint(block);
    j=n;
    while (j>1){
        k=block[j];
        if (tow[j])
        {
            if (next[k]==0)
            {
                next[k]=j;
                prev[j]=k;
                next[j]=0;
            }
            if (next[k]>j)
            {
                prev[j]=k;
                next[j]=next[k];
                prev[next[j]]=j;
                next[k]=j;
            }
            block[j]=next[k];
        }
        else
        {
            block[j]=prev[k];
            if (k==j)
            {
                if (next[k]==0)
                    next[prev[k]]=0;
                else
                {
                    next[prev[k]]=next[k];
                    prev[next[k]]=prev[k];
                }
            }
        }
        myprint(block);
        j=n;
        while ((j>1) && ((tow[j] && (block[j]==j)) || (!tow[j]&&(block[j]==1))))
        {
            tow[j]=!tow[j];
            j=j-1;
        }
    }
 
    fclose(f);
 
}