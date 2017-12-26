#include <stdio.h>
#include <vector>
using namespace std;
FILE *f;
bool used[31];
int ucnt(int k){
    int res = 0;
    for (int i = 1; i < k; i++)
        if (used[i])
            res++;
    return res;
}
 
unsigned long long cnt(int n, int k)
 {
     if(n < k || n < 1 || k < 1)
         return 1;
     if(n==k)
         return 1;
     if(k==1)
         return n;
     return cnt(n-1, k-1) + cnt(n-1, k);
 }
 
void main()
{
    int n, k, m, i, j;
    unsigned long long num;
    vector<int> s;
    f = fopen("choose2num.in", "rt");
    fscanf(f, "%d%d%\n", &n, &k);
    s.push_back(0);
    for (i = 0; i < k; i++)
    {
        fscanf(f, "%d", &m);
        s.push_back(m);
    }
    fclose(f);
    num = 0;
    for (i = 1; i <= n; i++)
        used[i] = false;
    for (i = 1; i <= k; i++)
    {
        for (j = 1; j <= s[i] - 1; j++)
            if ((used[j] == false) && (j > s[i-1]))
            {
                num += cnt(n - i + 1 - (j - ucnt(j)), k - i);
            }
        used[s[i]] = true;
    }
    f = fopen("choose2num.out", "wt");
    fprintf(f,"%llu", num);
    fclose(f);
}