#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
int  powFun(int x, int y) {
int temp;
temp = pow(x , y);
return temp;
}
char* concatStringToString(char *s1, char* i) {
    char* s = malloc(256);
    sprintf(s, "%s%s", s1, i);
    return s;
}

char* concatIntegerToString(char *s1, int i) {
    char* s = malloc(256);
    sprintf(s, "%s%d", s1, i);
    return s;
}

int main() {
int z;
int y;
int x;
printf("Inserire il primo numero : ");

scanf("%d", &x);
printf("Inserire il secondo numero : ");

scanf("%d", &y);
z = powFun(x, y);
printf("%s\n", concatIntegerToString(concatStringToString(concatIntegerToString(concatStringToString(concatIntegerToString("result pow ",x),"*"),y),"="),z));

    return 0;
}
