
#include <stdio.h>
#include <string>
using namespace std;
FILE *f;
int n;
void main()
{
    unsigned long long num,t,d=0;
    int i,j;
    f = fopen("num2brackets.in", "rt");
    fscanf(f, "%d%llu", &n, &num);
    fclose(f);
    f = fopen("num2brackets.out", "wt");
    string s;
    s.clear();
    unsigned long long mas[45][45];
    for (i=0;i<45;i++)
        for (j=0;j<45;j++)
            mas[i][j]=0;
    mas[0][0]=1;
    for (int i=0; i<n*2; i++)
        for (int j=0; j<=n; j++)
        {
            if (j+1 <= n)
                mas[i+1][j+1] += mas[i][j];
            if (j > 0)
                mas[i+1][j-1] += mas[i][j];
        }
    num++;
    for (i=2*n-1;i>=0;i--)
    {  t=mas[i][d+1];
        if ((d+1<=n) && (t >= num))
        {
            s += '(';
            d++;
        }
        else
        {   
            if (d+1 <= n)
              num -= t;
            s += ')';
            d--;
        }
    }
    fprintf(f,"%s",s.c_str());
    fclose(f);
}