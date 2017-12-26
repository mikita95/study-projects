#include <stdio.h>
#include <vector>
using namespace std;
int main()
{
    vector<int> a;
    FILE *f;
    int i,k,j,n;
    f=fopen("lis.in","rt");
    fscanf(f,"%d\n",&n);
    for (i=0;i<n;i++)
    {
        fscanf(f,"%d\n",&k);
        a.push_back(k);
 
    }
    vector<int> prev(n);
    vector<int> d(n);
    for (i = 0;i<n;i++)
    {
        d[i] = 1;
        prev[i] = -1;
        for (j = 0;j<i;j++)
        {
            if (a[j] < a[i])
                if (d[j] + 1 > d[i])
                    {
                        d[i] = d[j] + 1;
                        prev[i] = j;
                    }
        }
    }
    int length = d[0], pos = 0;//length - длина наибольшей подпоследовательности, pos - последний символ наибольшей возрастающей подпоследовательности
    for (i = 0;i<n;i++)
    {
 
        if (d[i] > length)
            {
                length = d[i];
                pos = i;
 
            }
    }
    vector<int> answer;
    while (pos != -1)
    {
        answer.push_back(a[pos]);
        pos = prev[pos];
    }
    fclose(f);
    f=fopen("lis.out","wt");
    fprintf(f,"%d\n",answer.size());
    for(i=answer.size()-1;i>=0;i--)
        fprintf(f,"%d ",answer[i]);
    return 0;
}