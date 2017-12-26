#include <stdio.h>
#include <vector>
using namespace std;
unsigned long long d[1050][10050];
vector<int> w;
vector<int> p;
vector<int> ans;
void MyPrint(int object, int weight)
{
  if (d[object][weight]==0)
    return;
  else
    if (d[object-1][weight] == d[object][weight])
        MyPrint(object-1,weight);
    else
    {
        MyPrint(object-1,weight-w[object]);
        ans.push_back(object);
    }
}
int main()
{
    FILE *f;
    int n,m,i,j,s,k;
    int object,weight;
    f = fopen("knapsack.in","rt");
    fscanf(f,"%d%d\n",&object,&weight);
    w.push_back(0);
    p.push_back(0);
    for (i=0;i<object;i++)
    {
        fscanf(f,"%d",&j);
        w.push_back(j);
    }
    fscanf(f,"\n");
    for (i=0;i<object;i++)
    {
        fscanf(f,"%d",&j);
        p.push_back(j);
    }
    for (i = 0;i<=weight;i++)
        d[0][i] = 0;
    for (i = 0;i<=object;i++)
        d[i][0] = 0;
    for (k =1;k<=object;k++)
        for (s = 0;s<=weight;s++)
            if (s >= w[k])
                d[k][s] = max(d[k-1][s], d[k-1][s-w[k]]+p[k]);
            else
                d[k][s] = d[k-1][s];
    fclose(f);
    f=fopen("knapsack.out","wt");
    MyPrint(object,weight);
    fprintf(f,"%d\n",ans.size());
    for (i=0;i<ans.size();i++)
        fprintf(f,"%d ",ans[i]);
    return 0;
}