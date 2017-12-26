#include <math.h>
#include <fstream>
#include <string>
using namespace std;
ifstream cin("rmq.in");
ofstream cout("rmq.out");
 
 
const int maxn = 1e6;
const int inf = 1e9 + 1;
int n, n1;
int data[maxn];
int tree[4 * maxn];
int opn(int a, int b) {
 
    return min(a, b);
}
 
void build(){
     for (int  i = 0; i < n1; ++i)
        tree[i + n] = data[i];
 
    for (int i = n1; i < 2 * n; ++i)
        tree[i + n] = inf;
 
    for (int i = n - 1; i > 0; --i)
        tree[i] = opn(tree[2 * i], tree[2 * i + 1]);
 
     }
 
int getopn(int l,int r){
    int res;
    l = l + n - 1;
    r = n + r - 1;
    res = inf;
    while (l <= r){
        if (l % 2 == 1)
          res = opn(res,tree[l]);
        if (r % 2 == 0)
          res = opn(res,tree[r]);
        l = (l + 1) / 2;
        r = (r - 1) / 2;
 
          }
    return res;
}
 
void modify(int i,int v){
     i = i + n - 1;
     tree[i] = v;
     while (i /= 2){
         tree[i] = opn(tree[2 * i],tree[2 * i + 1]);
           }
     }
 
int main(){
    cin >> n;
    n1 = n;
    for (int i = 0;i < n; ++i)
       cin >> data[i];
    build();
 
    string s;
    while (cin >> s) {
            int a, b;
 
            if (s == "min")
            {
 
                cin >> a >> b;
                cout << getopn(a, b) << endl;
 
            } else
            {
                cin >> a >> b;
                modify(a, b);
            }
 
    }
}