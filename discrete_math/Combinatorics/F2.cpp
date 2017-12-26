#include <stdio.h>
#include <string>
using namespace std;
FILE *f;
int n;
void main()
{
    unsigned long long num,t,d=0;
    int i,j;
    string s;
    char stmt[42];
    f = fopen("brackets2num.in", "rt");
    fscanf(f, "%s", stmt);
    s=stmt;
    fclose(f);
    f = fopen("brackets2num.out", "wt");
    unsigned long long mas[45][45];
    for (i=0;i<45;i++)
        for (j=0;j<45;j++)
            mas[i][j]=0;
    mas[0][0]=1;
    n=s.length()/2;
    for (int i=0; i<n*2; i++)
        for (int j=0; j<=n; j++)
        {
            if (j+1 <= n)
                mas[i+1][j+1] += mas[i][j];
            if (j > 0)
                mas[i+1][j-1] += mas[i][j];
        }
    num=0;
    for (i=0;i<=2*n-1;i++)
    { 
        if (s[i]=='(')
        {
            d++;
        }
        else
        {   
            num+=mas[2*n-i-1][d+1];
            d--;
        }
    }
    fprintf(f,"%llu",num);
    fclose(f);
}