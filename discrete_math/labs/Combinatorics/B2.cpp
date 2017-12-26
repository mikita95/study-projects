#include <stdio.h>
#include <vector>
using namespace std;
FILE *f;
 
unsigned long long fact(int x){
    if (x == 0) 
        return 1;
    if (x == 1) 
       return 1;
      else
          return (x * fact(x - 1));
}
 
void main()
{
    int n,k,i;
    unsigned long long num;
     
    f = fopen("perm2num.in", "rt");
    fscanf(f, "%d", &n);
    int s[19];
    bool used[19];
    for (i=1;i<=n;i++)
    {
        fscanf(f, "%d", &k);
        s[i]=k;
        used[i]=false;
    }
    fclose(f);
    num=0;
    for (i=1;i<=n;i++){
        for (k=1;k<=s[i]-1;k++)
            if (used[k]==false)
            {
                num += fact(n-i);
            }
        used[s[i]]=true;
    }
    f = fopen("perm2num.out", "wt");
    fprintf(f,"%llu",num);
     
}