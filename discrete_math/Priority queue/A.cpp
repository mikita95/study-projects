#include <fstream>
 
using namespace std;
ifstream fin("isheap.in");
ofstream fout("isheap.out");
const int maxn = 100002;
 
 
int main()
{
    int n;
    long int a[maxn];
    fin >> n;
    for (int i = 1; i <= n; i++)
        fin >> a[i];
    bool f = true;
    for (int i = 1; i <= n; i++)
    {
        if (2*i <= n)
          if (a[i] > a[2*i])
          {
              f = false;
              break;
          }
        if (2*i + 1 <= n)
            if (a[i] > a[2*i + 1])
            {
                f = false;
                break;
            }
    }
    if (f)
        fout << "YES";
    else
        fout << "NO";
}