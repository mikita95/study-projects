#include <fstream>
#include <string>
#include <vector>
using namespace std;
ifstream cin("palindrome.in");
ofstream cout("palindrome.out");
int main()
{
    FILE *f;
    int l1,l2,i,j;
    string a,b;
    cin>>a;
    for (i=a.length()-1;i>=0;i--)
        b+=a[i];
    vector<vector<int> > max_len;
    max_len.resize(a.size() + 1);
    for(int i = 0; i <= a.size(); i++)
        max_len[i].resize(b.size() + 1);
    for(int i = a.size() - 1; i >= 0; i--)
        {
            for(int j = b.size() - 1; j >= 0; j--)
            {
                if(a[i] == b[j])
                {
                    max_len[i][j] = 1 + max_len[i+1][j+1];
                }
                else
                {
                    max_len[i][j] = max(max_len[i+1][j], max_len[i][j+1]);
                }
            }
        }
    string res;
    for(int i = 0, j = 0; max_len[i][j] != 0 && i < a.size() && j < b.size(); )
        {
            if(a[i] == b[j])
            {
                res.push_back(a[i]);
                i++;
                j++;
            }
            else
            {
                if(max_len[i][j] == max_len[i+1][j])
                    i++;
                else
                    j++;
            }
        }
    cout<<res.size()<<endl<<res;
}