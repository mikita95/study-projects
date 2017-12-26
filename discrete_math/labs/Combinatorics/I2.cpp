#include <stdio.h>
void main()
{
    FILE *f;
    int n,i,j;
    unsigned long long r;
    f=fopen("num2part.in","rt");
    fscanf(f,"%d%llu",&n,&r);
    fclose(f);
    f=fopen("num2part.out","wt"); 
    unsigned long long mas[120][120];
    int res[120],c=0;
    for(i = 0; i <= n; i++) 
    {
        mas[0][i] = 1;
    }
    for(i = 1; i <= n; i++)
        for(j = i; j >= 0; j--) 
        {
            mas[i][j] = (j < n ? mas[i][j+1] : 0) + mas[i-j][j];
        }
    j = 1;
    while( n > 0) 
    {
        if(mas[n-j][j] > r) 
        {
            n -= j;
            res[c]=j;
            c++;
        } 
        else
        {
            r -= mas[n-j][j];
            j++;
        }
    }
    fprintf(f,"%d",res[0]);
    for (i=1;i<c;i++)
        fprintf(f,"+%d",res[i]);
    fclose(f);
}