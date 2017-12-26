#include <stdio.h>
#include <vector>
using namespace std;
FILE *f;
 
unsigned long long fact(unsigned long long x){
    if (x == 0) 
        return 1;
    if (x == 1) 
       return 1;
      else
          return (x * fact(x - 1));
}
 
void main()
{
    unsigned long long n, j, k, i, t, num;
    f = fopen("num2perm.in", "rt");
    fscanf(f, "%llu%llu", &n, &num);
    fclose(f);
    num++;
    f = fopen("num2perm.out", "wt");
    vector<int> res(n);
    vector<bool> used(n+1,false);
    for (int i = 0; i < n; i++) {
        t = (num - 1) / fact(n - i - 1) + 1;
        unsigned long long p=0;
        for (j = 1; j < used.size(); j++) 
        {
          if (!used[j]) p++;
          if (t == p)
           break;
        }
        res[i] = j;
        used[j] = true;
        num = (num - 1) % fact(n - i - 1) + 1;
    }
    for (i=0;i<res.size();i++)
        fprintf(f,"%d ",res[i]);
}