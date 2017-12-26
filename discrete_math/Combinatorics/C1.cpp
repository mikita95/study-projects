
#include <stdio.h>
#include <string>
using namespace std;
int n,k;
FILE *f;
 
void gen(string s,int t, int p)
{  int c;
  string r;
if (p==k){
  fprintf(f,"%s\n",s.c_str());
     }
    else
    {  for (c=1+t;c<=n;c++){
        r.clear();
          if (c>9)
              r.push_back(c/10+'0');
          r.push_back(c % 10+'0');
          gen(s+r+' ',c,p+1);}
    }
}
 
void main(){
    f=fopen("choose.in","rt");
    fscanf(f,"%d%d",&n,&k);
    fclose(f);
    f=fopen("choose.out","wt");
    gen("",0,0);
}