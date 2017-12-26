#include <stdio.h>
#include <vector>
using namespace std;
FILE *f;
 
void main()
{   vector<int> s,r,p;
    int i,n,k,t,min;
    f = fopen("nextperm.in", "rt");
    fscanf(f, "%d\n", &n);
    for (i=0;i<n;i++)
    {
        fscanf(f,"%d",&k);
        r.push_back(k);
    }
    fclose(f);
    f = fopen("nextperm.out", "wt");
//предыдущая//////////////////////////////
    p.clear();
    s=r;
    k=-1;
    for (i=n-2;i>=0;i--)
        if (s[i+1]<s[i])
        {
            k=i;
            break;
        }
    if (k==-1)
    {
        for (i=0;i<n;i++)
            s[i]=0;
    }
    else
    {
        int t,tmt;
        for (i=n-1;i>=0;i--)
            if (s[i]<s[k])
            {
                t=i;
                break;
            }
        tmt=s[k];
        s[k]=s[t];
        s[t]=tmt;
        for (i=n-1;i>=k+1;i--)
            p.push_back(s[i]);
        for (i=k+1;i<n;i++)
            s[i]=p[i-k-1];
    }
    for (i=0;i<s.size();i++)
        fprintf(f,"%d ",s[i]);
    fprintf(f,"\n");
//следующая//////////////////////////////
    p.clear();
    s=r;
    k=-1;
    for (i=n-2;i>=0;i--)
        if (s[i+1]>s[i])
        {
            k=i;
            break;
        }
    if (k==-1)
    {
        for (i=0;i<n;i++)
            s[i]=0;
    }
    else
    {
        int t,tmt;
        for (i=n-1;i>=0;i--)
            if (s[i]>s[k])
            {
                t=i;
                break;
            }
        tmt=s[k];
        s[k]=s[t];
        s[t]=tmt;
        for (i=n-1;i>=k+1;i--)
            p.push_back(s[i]);
        for (i=k+1;i<n;i++)
            s[i]=p[i-k-1];
    }
    for (i=0;i<s.size();i++)
        fprintf(f,"%d ",s[i]);
//////////////////////////////////////////////////
    fclose(f);
}