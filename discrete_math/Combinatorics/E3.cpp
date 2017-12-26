#include <stdio.h>
#include <string>
using namespace std;
void main()
{
    FILE *f;
    string s;
    int i,d,op,cl;
    f=fopen("nextbrackets.in","rt");
    char tmt[200050];
    fscanf(f,"%s",tmt);
    fclose(f);
    f=fopen("nextbrackets.out","wt");
    s=tmt;
    string ans = "-";
    int n = s.length();
    d=0;
    for (i=n-1; i>=0; i--)
    {
        if (s[i] == '(')
            d--;
        else
            d++;
        if ((s[i]=='(') && (d > 0)) 
        {
            d--;
            op = (n-i-1 - d)/2;
            cl = n-i-op-1;
            ans = s.substr(0,i) + ')';
            for (int t=0;t<op;t++)
                ans+='(';
            for (int t=0;t<cl;t++)
                ans+=')';
            break;
        }
    }
   fprintf(f,"%s",ans.c_str());
}