#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
float somma(float a, float b) {
float result;
printf(""Somma: "\n"); 
result = a + b;
return result;
}
float moltiplica(float a, float b) {
float result;
printf(""Moltiplicazione: "\n"); 
if (a == 0.0 || b == 0.0) {
result = 0.0;
}
else {
result = a * b;

}
return result;
}
float dividi(float a, float b) {
float result;
printf(""Divisione: "\n"); 
if (b == 0.0) {
result = 0.0;
}
else {
result = a / b;

}
return result;
}
float sottrai(float a, float b) {
float result;
printf(""Sottrazione: "\n"); 
if (a < b) {
result = 0.0;
}
else {
result = a - b;

}
return result;
}
void stampa(char* messaggio) {
i;
while (i <= 4) {
incremento;
printf("""\n"); 
i = i + incremento;
    }
printf("messaggio\n"); 
}
int main() {
float a = 0.0;
float b = 0.0;
op;
ans;
int operator = 0;
while (ans == "si") {
ans = "";
printf("op\n"); 

scanf(");
printf(""Operatore selezionato:"\n"); 
printf("operator\n"); 

scanf(");

scanf(");
if (operator == 1) {
result = somma(a, b);
}
if (operator == 2) {
result = moltiplica(a, b);
}
if (operator == 3) {
result = dividi(a, b);
}
if (operator == 4) {
result = sottrai(a, b);
}
stampa("Il risultato della tua operazione è "result);
printf(""vuoi continuare? (si/no)"\n"); 

scanf(");
    }
printf("""\n"); 
printf(""ciao"\n"); 

    return 0;
}
