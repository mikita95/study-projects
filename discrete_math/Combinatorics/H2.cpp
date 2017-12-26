
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
    s.empty();
    f = fopen("num2brackets2.in", "rt");
    fscanf(f, "%d%llu", &n, &num);
    fclose(f);
    f = fopen("num2brackets2.out", "wt");
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
 
    char st[45];
    int stsize = 0;
    num++;
    unsigned long long c;
    for (int i=n*2-1; i>=0; i--)
    {
        if (d+1 <= n)
            c=  mas[i][d+1] << ( (i-d-1)/2 );
        else
            c=0;
        if (c>=num)
        {
            s += '(';
            st[stsize++] = '(';
            d++;
            continue;
        }
        num-=c;
        if (stsize > 0 && st[stsize-1] == '(' && d-1 >= 0)
            c = mas[i][d-1] << ( (i-d+1)/2 );
        else
            c = 0;
        if (c>= num) 
        {
            s += ')';
            stsize--;
            d--;
            continue;
        }
        num-=c; 
        if (d+1 <= n)
            c = mas[i][d+1] << ( (i-d-1)/2 );
        else
            c = 0;
        if (c>=num) 
        {
            s += '[';
            st[stsize++] = '[';
            d++;
            continue;
        }
        num-=c;
        s += ']';
        stsize--;
        d--;
    }
    fprintf(f,"%s",s.c_str());
    fclose(f);
}