#include <fstream>
#include <string>
#include <vector>
 
using namespace std;
string s1,s2;
ifstream cin("levenshtein.in");
ofstream cout("levenshtein.out");
int MyMin(int a,int b,int c)
{
    return min(c,min(a,b));
}
 
int MySw(char c1,char c2)
{
    if (c1==c2)
        return 0;
    else
        return 1;
}
 
int main()
{
    int i,j,k;
    cin>>s1;
    cin>>s2;
    int d[5002],d1[5002];
    for (j=1;j<=s2.length();j++)
        d1[j]=d1[j-1]+1;
    d1[0]=0;
    for (i=1;i<=s1.length();i++)
    {
        d[0]=d1[0]+1;
        for (j=1;j<=s2.length();j++)
            d[j]=MyMin(d1[j]+1,d[j-1]+1,d1[j-1]+MySw(s1[i-1],s2[j-1]));
        for (j=0;j<=s2.length();j++)
            {
                d1[j]=d[j];
                d[j]=0;
            }
 
    }
    cout<<d1[s2.length()];
}