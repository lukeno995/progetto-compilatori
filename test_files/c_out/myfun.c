#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
float  somma(float a, float b) {
float result;
printf("Somma: \n");
result = a + b;
return result;
}
float  moltiplica(float a, float b) {
float result;
printf("Moltiplicazione: \n");
if (a == 0.0 || b == 0.0) {
result = 0.0;
}
else {
result = a * b;

}
return result;
}
float  dividi(float a, float b) {
float result;
printf("Divisione: \n");
if (b == 0.0) {
result = 0.0;
}
else {
result = a / b;

}
return result;
}
float  sottrai(float a, float b) {
float result;
printf("Sottrazione: \n");
if (a < b) {
result = 0.0;
}
else {
result = a - b;

}
return result;
}
void stampa(char *messaggio) {
int i = 1;
while (i <= 4) {
int incremento = 1;
printf("\n");
i = i + incremento;
    }
printf("%s", messaggio);
}
char* concatRealToString(char *s1, float i) {
    char* s = malloc(256);
    sprintf(s, "%s%.2f", s1, i);
    return s;
}

int main() {
float a = 0.0;
float b = 0.0;
float result;
char *ans = "si";
int operator = 0;
while (strcmp(ans,"si") == 0) {
ans = "";
printf("1. addizione 2. moltiplicazione 3. divisione 4. sottrazione  5. esci\n");
printf("Seleziona loperazione con un intero:");

scanf("%d", &operator);
printf("inserisci primo operando con un reale:");

scanf("%f", &a);
printf("inserisci secondo operando con un reale:");

scanf("%f", &b);
if (operator == 2) {
result = moltiplica(a, b);
}
stampa(concatRealToString("Il risultato della tua operazione è ",result));
printf("vuoi continuare? (si/no)\n");
ans = malloc(256);

scanf("%s", ans);
    }
printf("ciao\n");

    return 0;
}
