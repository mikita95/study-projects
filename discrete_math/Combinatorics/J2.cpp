
#include <stdio.h>
void main()
{
    FILE *f;
    unsigned long long mas[120][120],r;
    int s[120],c=0,j,i,n,k;
    f=fopen("part2num.in","rt");
    n=0;
    i=0;
    while (!feof(f))
    {
        fscanf(f,"%d+",&s[i]);
        n+=s[i];
        i++;
    }
    fclose(f);
    f=fopen("part2num.out","wt");
    k=i;
    for(i =0; i<= n; i++) 
    {
        mas[0][i] = 1;
    }
    for(i = 1; i <= n; i++)
        for(j = i; j >= 0; j--) 
        {
            mas[i][j] =(j < n ? mas[i][j+1] : 0) + mas[i-j][j];
        }
 
    r = 0;
    j = 1;
    for (i = 0; i < k; i++) 
    {
        r += mas[n][j] - mas[n][s[i]];
        j = s[i];
        n -= j;
    }
    fprintf(f,"%llu",r);
    fclose(f);
}