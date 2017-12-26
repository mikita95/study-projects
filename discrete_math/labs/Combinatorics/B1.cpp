#include <stdio.h>
#include <string>
using namespace std;
int n;
FILE *f;
 
void gen(string s, int p)
{  char c;
if (p==n){
  fprintf(f,"%s\n",s.c_str());
     }
    else
    {  for (c='1';c<=n+'0';c++){
        if (s.find(c)==s.npos)
          gen(s+c+' ',p+1);}
    }
}
 
void main(){
    int a,b,c,i,k,t;
    f=fopen("permutations.in","rt");
    fscanf(f,"%d",&n);
    fclose(f);
    a=1;
    b=1;
    f=fopen("permutations.out","wt");
    gen("",0);
}