
#include <fstream>
 
using namespace std;
ifstream cin("lottery.in");
ofstream cout("lottery.out");
 
int main()
{
    int n,i,m,a,b,lb=0;
    double p,ans=0,t,la=1;
    cin>>n>>m;
 
    for (i = 0;i < m; i++)
    {
        cin >> a >> b;
        la =  la / a;
//        t= t / (a * la);
        ans += la * (b-lb);
        lb = b;
        //la = a * la;
    }
    cout.precision(9);
    cout << n - ans;
    return 0;
}