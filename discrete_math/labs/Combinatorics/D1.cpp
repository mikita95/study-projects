#include <stdio.h>
#include <string>
using namespace std;
int n,k;
FILE *f;
 
bool check(string s){
    int balance,i;
    balance=0;
    for (i=0;i<s.length();i++){
        if (s[i]=='(')
            balance+=1;
        if (s[i]==')')
            balance-=1;
        if (balance<0)
            return false;
    }
    if (balance!=0)
        return false;
    return true;
}
 
void gen(string s,int p)
{  int c;
   char j;
  string r;
if (p==n){
    if (check(s))
      fprintf(f,"%s\n",s.c_str());
     }
    else
    {  for (c=1;c<=2;c++){
          if (c==1)
              j='(';
          else
              j=')';
          gen(s+j,p+1);}
    }
}
 
void main(){
    f=fopen("brackets.in","rt");
    fscanf(f,"%d",&n);
    fclose(f);
    n=2*n;
    f=fopen("brackets.out","wt");
    gen("",0);
}