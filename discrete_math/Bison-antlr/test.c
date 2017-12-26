#include<stdio.h> 
 #include<math.h>
int prime(int n) {
int flag = 0;
int sn = sqrt(n)+1;
for (int i = 2; i < sn; ++i) {
if (n%i==0) {
flag = 1;
}
}
return flag;
}
int main(int argc, char *argv[]) {
printf("%s", "Number:");
int n = 0;
scanf("%d", &n);
int a = 1;
int b = 2;
a = 3;
b = 4;
int result = prime(n);
if (result==0) {
printf("%s\n", "YES");
} else {
printf("%s\n", "NO");
}
return 0;
}
