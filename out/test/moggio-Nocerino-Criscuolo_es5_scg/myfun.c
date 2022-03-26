#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
void stampa(char* messaggio) {
int i;
while (i <= 4) {
int incremento;
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
char* ans;
int operator = 0;
while (strcmp(ans,"si")) {
ans = "";
printf("1. addizione 2. moltiplicazione 3. divisione 4. sottrazione  5. esci\n");
printf("Seleziona loperazione con un intero:");

scanf("%d", &operator);
printf("inserisci primo operando con un reale:");

scanf("%f", &a);
printf("inserisci secondo operando con un reale:");

scanf("%f", &b);
stampa(concatRealToString("Il risultato della tua operazione è ",result));
printf("vuoi continuare? (si/no)\n");
printf("");

scanf("%s", ans);
    }
printf("ciao\n");

    return 0;
}
