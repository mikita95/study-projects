#include <stdio.h>
#include <string>
#include <math.h>
using namespace std;
 
int strtoint(string s)
{
    int res=0,p;
    double d,x;
    for (int i=s.size()-1;i>=0;i--)
    {
        x=s.size()-i-1;
        d=pow(10,x);
        res+=d*(s[i]-'0');
    }
    return res;
}
 
void main()
{
    FILE *f;
    int mas[100050];
    char tmt[200050];
    f=fopen("nextpartition.in","rt");
    fscanf(f,"%s",tmt);
    fclose(f);
    string s,r,p;
    s=tmt;
    int i;
    for (i=0;i<s.size();i++)
    {
        p+=s[i];
        if (s[i]=='=')
            break;
    }
    s.erase(0,i+1);
    r.empty();
    int k=0;
    int sum=0;
    for (i=0;i<s.size();i++)
    {
        if (s[i]!='+')
            r+=s[i];
        else
        {
            mas[k]=strtoint(r);
            sum+=mas[k];
            r.clear();
            k++;
        }
    }
    mas[k]=strtoint(r);
    r.empty();
    sum+=mas[k];
    k++;
    f=fopen("nextpartition.out","wt");
 
    if (k==1)
        fprintf(f,"No solution");
    else
    {
        int h=0,j,x,t,c,v,fl=0;
        for (i=k-1;i>=k-2;i--)
            h+=mas[i];
        i++;
        h=sum-h;
        for (j=mas[i]+1;j<=sum-h;j++)
        {
            if (sum-h-j==0)
            {
                mas[i]=j;
                fprintf(f,"%s",p.c_str());
                fprintf(f,"%d",mas[0]);
                for (x=1;x<=i;x++)
                    fprintf(f,"+%d",mas[x]);
                return;
            }
            if (sum-h-j>=j)
                break;
        }
        mas[i]=j;
        h+=j;
        i++;
        for (t=i;t<=sum;t++)
        {
            for (j=mas[t-1];j<=sum-h;j++)
                {
                    if (sum-h-j==0)
                        {
                            mas[t]=j;
                            fprintf(f,"%s",p.c_str());
                            fprintf(f,"%d",mas[0]);
                            for (x=1;x<=t;x++)
                                fprintf(f,"+%d",mas[x]);
                            return;
                        }
                    if (sum-h-j>=j)
                        break;
                }
            mas[t]=j;
            h+=j;
        }
        fprintf(f,"%s",p.c_str());
        fprintf(f,"%d",mas[0]);
        for (x=1;x<=t;x++)
            fprintf(f,"+%d",mas[x]);
        return;
 
    }
     
}