#include <stdio.h>
#include <string>
#include <math.h>
using namespace std;
 
bool check(char c1,char c2)
{
    if (c2=='(' || c2=='[')
        return true;
    if ((c1=='(' && c2==')') || (c1=='[' && c2==']'))
        return true;
    return false;
 
}
void main()
{
    FILE *f;
    char tmt[100];
    f=fopen("brackets2num2.in","rt");
    fscanf(f,"%s",tmt);
    fclose(f);
    string s=tmt;
    unsigned long long num=0,p;
    int d=0,n,i,k,j,fl=0,y;
    double w;
    n = s.length()/2;
    unsigned long long mas[45][45];
    for (i=0;i<45;i++)
        for (j=0;j<45;j++)
            mas[i][j]=0;
    mas[0][0]=1;
    for (i=0; i<n*2; i++)
        for (j=0; j<=n; j++)
        {
            if (j+1 <= n)
                mas[i+1][j+1] += mas[i][j];
            if (j > 0)
                mas[i+1][j-1] += mas[i][j];
        }
    char st[45];
    int stsize = 0;
    y=0;
    f=fopen("brackets2num2.out","wt");
    /*for (i=0;i<n;i++)
        if (s[i]=='(')
            y++;
    if (y==n)
    {
        fprintf(f,"0");
        return;
    }*/
    for (i=0;i<2*n;i++)
    {
        if (s[i]=='(')
        {
            d++;
            st[stsize++]='(';
        }
        if (s[i]==')')
        {
            //if (stsize!=0)
                //if (check(st[stsize-1],'(')) 
            //  {
                    w=pow(2,(double)(2*n-i-1-d-1)/2);
                    num+=mas[2*n-i-1][d+1]*w;
            //  }
            stsize--;
            d--;
        }
        if (s[i]=='[')
        {
             
            if (stsize!=0)
                if (check(st[stsize-1],')')) 
                {
                    w=pow(2,(double)(2*n-i-1-d+1)/2);
                    num+=mas[2*n-i-1][d-1]*w;
                }
            //if (stsize!=0)
                //if (check(st[stsize-1],'(')) 
            //  {
                    w=pow(2,(double)(2*n-i-1-d-1)/2);
                    num+=mas[2*n-i-1][d+1]*w;
            //  }
            d++;
            st[stsize++]='[';
        }
        if (s[i]==']')
        {   
            //if (stsize!=0)
                //if (check(st[stsize-1],'[')) 
            //  {
                    w=pow(2,(double)(2*n-i-1-d-1)/2);
                    //p= ceil(w);
                    num+= mas[2*n-i-1][d+1]*w;
            //  }
            if (stsize!=0)
                if (check(st[stsize-1],')')) 
                {
                    w=pow(2,(double)(2*n-i-1-d+1)/2);
                    //p= unsigned long long (w);
                    num+=mas[2*n-i-1][d-1]*w;
                }
            //if (stsize!=0)
                //if (check(st[stsize-1],'(')) 
            //  {
 
                    w=pow(2,(double)(2*n-i-1-d-1)/2);
                    //p= ceil(w);
                    num+=mas[2*n-i-1][d+1]*w;
            //  } 
            d--;
            stsize--;
        }
 
    }
    fprintf(f,"%llu",num);
 
}