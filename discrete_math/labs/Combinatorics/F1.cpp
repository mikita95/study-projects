#include <stdio.h>
#include <set>
using namespace std;
int n, k;
FILE *f;
set<int> s;
void gen(int p,int pr)
{  
    int c, i;
    char j;
    for(set<int>::const_iterator it = s.begin(); 
                            it != s.end(); it++) {
                                fprintf(f,"%d ",*it);
    }
    fprintf(f,"\n");
    for (c = pr; c <= n; c++)
    {  if (s.find(c)==s.end())
        { 
            s.insert(c);
            gen(p+1,c);
            s.erase(s.find(c));
        }
    }
     
}
 
void main()
{
    f = fopen("subsets.in", "rt");
    fscanf(f, "%d", &n);
    fclose(f);
    f = fopen("subsets.out", "wt");
    gen(1,1);
}