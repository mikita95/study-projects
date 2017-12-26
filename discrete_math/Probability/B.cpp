#include <fstream>
#include <iomanip>
#include <math.h>
#include <stdio.h>
 
using namespace std;
ifstream cin("shooter.in");
ofstream cout("shooter.out");
 
int main()
{
    int n,k,i,m;
    long double p,ans=0,t;
    cin>>n>>m>>k;
    for (i = 0;i < n; i++)
    {
        cin>>p;
        if (i + 1 == k)
            t = p;
        ans += powl(1-p,m);
    }
    cout<<setprecision(14)<<powl(1-t,m)/ans;
    return 0;
}