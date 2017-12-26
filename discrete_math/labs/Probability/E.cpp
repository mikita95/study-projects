
#include fstream
#include cmath
using namespace std;
ifstream cin(markchain.in);
ofstream cout(markchain.out);
const int maxn = 101;
const double eps = 0.000001;
int n, i, k, j;
long double mas[maxn][maxn], t, alena[maxn][maxn], c[maxn][maxn];
void mulmatrix(long double a[maxn][maxn], long double b[maxn][maxn])
{
    int i, k, j;
    double sum;
    for (i = 0; i  n; i++)
    {
        for (j = 0; j  n; j++)
        {
            sum = 0;
            for (k = 0; k  n; k++)
                sum += a[i][k]b[k][j];
            c[i][j] = sum;
        }
    }
}
 
bool check()
{
    int i, k;
    for (k = 0; k  n ; k++)
    {
        for (i = 0; i  n - 1; i++)
        {
            if (abs(mas[i][k]-mas[i+1][k]) = eps)
            {
                return false;
            }
 
        }
    }
    return true;
}
 
 
int main()
{
    cin  n;
    for (i = 0;i  n; i++)
        for (j = 0;j  n; j++)
        {
            cin  t;
            alena[i][j] = t;
            mas[i][j] = t;
        }
    i =1;
    do
    {
        mulmatrix(mas,alena);
        for (i = 0; i  n; i++)
            for (j = 0; j  n; j++)
                mas[i][j] = c[i][j];
    }
    while (!check());
    cout.precision(6);
    for (i = 0; i  n; i++)
    {
        cout  mas[0][i]endl;
    }
 
    return 0;
}