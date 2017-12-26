#include <fstream>
#include <iomanip>
 
using namespace std;
ifstream cin("exam.in");
ofstream cout("exam.out");
 
int main()
{
    int n,k,i,p,m;
    double ans=0;
    cin>>k>>n;
    for (i = 0;i < k; i++)
    {
        cin>>p>>m;
        ans+=m*p;
    }
    cout<<setprecision(7)<<ans/(n*100);
    return 0;
}