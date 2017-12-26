#include <fstream>
#include <vector>
 
using namespace std;
ifstream cin("absmarkchain.in");
ofstream cout("absmarkchain.out");
 
struct tmt
{
    int start;
    int finish;
    double value;
};
 
tmt v;
const int maxm = 160005;
const int maxn = 405;
const int correct = 6;
vector<tmt> input;
int position[maxn], abs, row, n, m, i, j, p, t, k, count_q = 0, count_r = 0;
double Q[maxn][maxn], R[maxn][maxn], G[maxn][maxn], prob,r ,E[maxn][maxn],mul,N[maxn][maxn];
bool absorbing[maxn];
 
void MyScanf()
{
    cin >> n >> m;
    for (i = 0; i < m; i++)
    {
        cin >> p >> t >> r;
 
        v.start = p - 1;
        v.finish = t - 1;
        v.value = r;
        input.push_back(v);
    }
}
void MyPrint(int nonabs)
{
    cout.precision(correct);
 
    for (i = 0; i < n; i++)
    {
        prob = 0;
        if (absorbing[i])
        {
            for (j = 0; j < nonabs; j++)
                prob += G[j][position[i]];
            prob++;
            prob/= n;
        }
        cout << prob << endl;
    }
}
 
void FullMatrix()
{
    for (i = 0; i < n; i++)
        if (absorbing[i])
        {
            position[i] = count_r;
            count_r++;
        }
        else
           {
                position[i] = count_q;
                count_q++;
           }
 
    for (i = 0; i < m; i ++)
        if (absorbing[input[i].finish])
        {
            if (!absorbing[input[i].start])
                R[position[input[i].start]][position[input[i].finish]] = input[i].value;
        }
        else
            Q[position[input[i].start]][position[input[i].finish]] = input[i].value;
}
 
int GetAbsCount()
{
    int abs = 0;
    for (i = 0; i < m; i++)
        if (input[i].start == input[i].finish && input[i].value == 1)
            {
                absorbing[input[i].start] = true;
                abs++;
            }
    return abs;
}
 
void CreateIMatrix(int nonabs)
{
    for (i = 0; i < nonabs; i++)
    {
        N[i][i] = 1;
        E[i][i] = 1;
        for (j = 0; j < nonabs; j++)
            E[i][j] -= Q[i][j];
    }
}
 
void MyGauss(int nonabs)
{
    for (i = 0; i< nonabs; i++)
    {
        if (E[i][i] != 1)
        {
            mul = E[i][i];
            for (j = 0; j < nonabs; j++)
            {
                E[i][j] /= mul;
                N[i][j] /= mul;
            }
        }
        for (row = 0; row < nonabs; row++)
            if (i != row)
                {
                    mul = E[row][i];
                    for (j = 0; j < nonabs; j++)
                    {
                        E[row][j] -= mul * E[i][j];
                        N[row][j] -= mul * N[i][j];
                    }
                }
    }
}
 
void FullGMatrix(int nonabs)
{
    for (i = 0; i < nonabs; i++)
        for (j = 0; j < abs; j++)
        {
            G[i][j] = 0;
            for (k = 0; k < nonabs; k++)
                G[i][j] += N[i][k] * R[k][j];
        }
}
 
void Prepare()
{
    for (i = 0; i < n; i++)
        for (j = 0; j < n; j++)
            {
                E[i][j]=0;
                N[i][j]=0;
                G[i][j]=0;
                Q[i][j]=0;
                R[i][j]=0;
                position[i]=0;
            }
}
 
int main()
{
    Prepare();
    MyScanf();
    abs = GetAbsCount();
    int nonabs = n - abs;
    FullMatrix();
    CreateIMatrix(nonabs);
    MyGauss(nonabs);
    FullGMatrix(nonabs);
    MyPrint(nonabs);
    return 0;
}