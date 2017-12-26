
#include <stdio.h>
#include <vector>
#include <algorithm>
using namespace std;
 
vector<int> used;
vector<int> a[210];
int g;
bool check(int i,int j)
{
    int x;
    if (used.size()==0 || a[i].size()<=1 || j==0)
        return false;
    sort(used.begin(),used.end());
    for (x=0;x<used.size();x++)
    {
        if (a[i][j-1]<used[x] && a[i][j]<used[x] && j<a[i].size()-1 && a[i][j+1]>used[x])
        {
            g=x;
            return true;
        }
        if (a[i][j-1]<used[x] && a[i][j]<used[x] && j==a[i].size()-1)
        {
            g=x;
            return true;
        }
    }
    return false;
}
 
bool mcheck(int i)
{
    int x;
    if (used.size()==0)
        return false;
    sort(used.begin(),used.end());
    for (x=0;x<used.size();x++)
    {
        if (a[i][a[i].size()-1]<used[x])
        {
            g=x;
            return true;
        }
    }
    return false;
 
}
 
 
void main()
{
    FILE *f,*q;
    bool fl;
     
    int n,k,i,j,t,p;
    f=fopen("nextsetpartition.in","rt");
    n=1;
    q=fopen("nextsetpartition.out","wt");
    while (n!=0 && k!=0){
    fscanf(f,"%d%d\n",&n,&k);
    if (n==0 && k==0)
        break;
    used.clear();
    for (i=0;i<207;i++)
        a[i].clear();
    p=0;
    for (i=0;i<k;i++)
    {
        char ch=0;
        while (ch!='\n' && ch!=13 && p!=n)
        {
            fscanf(f,"%d%c",&t,&ch);
            a[i].push_back(t);
            p++;
        }
    }
     
    fl=false;
    for (i=k-1;i>=0;i--)
    {
        if (mcheck(i))
        {
            a[i].push_back(used[g]);
            used.erase(find(used.begin(),used.end(),used[g]));
            break;
        }
        for (j=a[i].size()-1;j>=0;j--)
            if (check(i,j))
            {
                int z=a[i][j];
                a[i][j]=used[g];
                used.erase(find(used.begin(),used.end(),used[g]));
                fl=true;
                used.push_back(z);
                break;
            }
            else
            {
                used.push_back(a[i][j]);
                a[i].erase(find(a[i].begin(),a[i].end(),a[i][j]));
            }
        if (fl)
            break;
    }
    i=0;
    int cnt=0;
    while(a[i].size()!=0)
    {
        cnt++;
        i++;
    }
    cnt+=used.size();
    fprintf(q,"%d %d\n",n,cnt);
    i=0;
    while(a[i].size()!=0)
    {
        for (j=0;j<a[i].size();j++)
        {
            fprintf(q,"%d ",a[i][j]);
        }
        fprintf(q,"\n");
        i++;
    }
    sort(used.begin(),used.end());
    for (i=0;i<used.size();i++)
        fprintf(q,"%d\n",used[i]);
    fprintf(q,"\n");
    }
}