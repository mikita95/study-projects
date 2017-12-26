#include <fstream>
#include <vector>
#include <string>
using namespace std;
const int MaxN=402;
const int inf=1E9;
 
ifstream cin("matrix.in");
ofstream cout("matrix.out");
vector<int> v;
int dp[MaxN][MaxN],s[MaxN][MaxN];
string ans;
 
using namespace std;
 
void MyOrder(vector<int> p) {
        int n = p.size()-1;
        for (int i = 0; i < n; i++)
            for (int j=0; j< n; j++)
            {
                dp[i][j] = 0;
                s[i][j]=0;
            }
        for (int ii = 1; ii < n; ii++) {
            for (int i = 0; i < n - ii; i++) {
                int j = i + ii;
                dp[i][j] = inf;
                for (int k = i; k < j; k++) {
                    int q = dp[i][k] + dp[k+1][j] + p[i]*p[k+1]*p[j+1];
                    if (q < dp[i][j]) {
                        dp[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
}
void printOrder(int i, int j){
    if (i == j)
        ans += "A";
    else
    {
        ans +="(";
        printOrder(i,s[i][j]);
        printOrder(s[i][j]+1,j);
        ans +=")";
    }
}
int main()
{
    int i, j, t, n;
    cin >> n;
    cin >> i >> j;
    v.push_back(i);
    v.push_back(j);
    for (i = 0; i < n - 1; i++)
    {
        cin>>t;
        cin>>t;
        v.push_back(t);
    }
    MyOrder(v);
    printOrder(0,n-1);
    cout<<ans;
    return 0;
}