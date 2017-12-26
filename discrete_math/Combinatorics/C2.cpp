#include <stdio.h>
#include <vector>
using namespace std;
FILE *f;
bool used[31];
int ucnt(int k){
    int res=0;
    for (int i=1;i<k;i++)
        if (used[i])
            res++;
    return res;
}
 
unsigned long long cnt(int n, int k)
 {
     if(n<k || n<1 || k<1)
         return 1;
     if(n==k)
         return 1;
     if(k==1)
         return n;
     return cnt(n-1, k-1) + cnt(n-1, k);
 }
 
void main()
{
    int n,k,m,i,was,j;
    unsigned long long num,r,t;
     
    vector<int> res;
    f = fopen("num2choose.in", "rt");
    fscanf(f, "%d%d%llu", &n,&k,&num);
    for (i=1;i<=n;i++)
        used[i]=false;
    res.push_back(0);
    for (i=1;i<=k;i++)
        {
            for (j=1;j<=n;j++)
            { 
                if ((used[j]==false) && (res[i-1]<j))
                    {   t=cnt(n-i+1-(j-ucnt(j)),k-i);
                        if (num>=t)
                            num-=t;
                        else
                        {
                            res.push_back(j);
                            used[j]=true;
                            break;
                        }               
                    }
            }
        }
    
    f = fopen("num2choose.out", "wt");
    for (i=1;i<res.size();i++)
        fprintf(f,"%d ",res[i]);
}