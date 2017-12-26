#include <stdio.h>
#include <string>
#include <conio.h>
using namespace std;
int n;
FILE *f;
 
void gen(string s, int p)
{  
char c;
if ( p == n ){
        if ( s.find( "11" ) == s.npos )
            fprintf( f, "%s\n", s.c_str() );
      }
    else
    {  for ( c = '0'; c <= '1'; c++ )
	{
        	gen( s + c, p + 1 );
	}
    }
}
 
void main()
{
    int a, b, c, i, k, t;
    f = fopen( "vectors.in", "rt" );
    fscanf( f, "%d", &n );
    fclose( f );
    a = 1;
    b = 1;
    for ( i = 1; i <= n; i++ )
    {  
	c = b;
       b=a+b;
       a=c;
    }
    f=fopen("vectors.out","wt");
    fprintf(f,"%d\n",b);
    gen("",0);
}