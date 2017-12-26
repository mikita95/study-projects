#include <stdio.h>
#include <string>
using namespace std;
FILE *f;
int n,i,k=-1;
void main()
{   char stmt[200001];
    string s;
    f = fopen("nextvector.in", "rt");
    fscanf(f, "%s", stmt);
    s=stmt;
    fclose(f);
    f = fopen("nextvector.out", "wt");
    string r;
    //предыдущий
    for (i=s.length()-1;i>=0;i--)
        if (s[i]=='1')
        {
            k=i;
            break;
        }
    if (k==-1)
        fprintf(f,"%c\n",'-');
    else
    {
        r=s;
        r[k]='0';
        for (i=k+1;i<r.length();i++)
            r[i]='1';
        fprintf(f,"%s\n",r.c_str());
    }
    //следующий
    k=-1;
    for (i=s.length()-1;i>=0;i--)
        if (s[i]=='0')
        {
            k=i;
            break;
        }
    if (k==-1)
        fprintf(f,"%c",'-');
    else
    {
        r=s;
        r[k]='1';
        for (i=k+1;i<r.length();i++)
            r[i]='0';
        fprintf(f,"%s",r.c_str());
    }
    fclose(f);
}