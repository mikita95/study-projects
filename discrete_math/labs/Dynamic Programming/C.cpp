#include <stdio.h>
#include <vector>
using namespace std;
int main()
{
    FILE *f;
    int l1,l2,i,j;
    vector<int> a;
    vector<int> b;
    f=fopen("lcs.in","rt");
    fscanf(f,"%d\n",&l1);
    for (i=0;i<l1;i++)
    {
        fscanf(f,"%d",&j);
        a.push_back(j);
 
    }
    fscanf(f,"%d\n",&l2);
    for (i=0;i<l2;i++)
    {
        fscanf(f,"%d",&j);
        b.push_back(j);
    }
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
    vector<int> res;
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
    fclose(f);
    f=fopen("lcs.out","wt");
    fprintf(f,"%d\n",res.size());
    for (i=0;i<res.size();i++)
        fprintf(f,"%d ",res[i]);
 
}