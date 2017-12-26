#include <stdio.h>
#include <vector>
using namespace std;
int n, k;
FILE *f;
vector<int> s;
void gen(int t, int p)
{  
    int c, i;
    if (p == 0)
    {
       fprintf(f, "%d", s[0]);
       for (c = 1; c < s.size(); c++)
           fprintf(f, "+%d", s[c]);
       fprintf(f, "\n");
     }
    else
    {  for (c = t; c <= p; c++)
       {
          s.push_back(c);
          gen(c, p - c);
          s.pop_back();
       }
    }
}
 
void main()
{
    f = fopen("partition.in", "rt");
    fscanf(f, "%d", &n);
    fclose(f);
    f = fopen("partition.out", "wt");
    gen(1, n);
}