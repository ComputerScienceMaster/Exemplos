#include <stdio.h>
#include <stdlib.h>

/* Captura e imprime as coordenadas de um ponto qualquer */

struct ponto {
float x;
float y;
};

int main () {
    struct ponto p;
    printf("Digite as coordenadas do ponto(x y): ");
    scanf("%f %f", &p.x, &p.y);
    printf("O ponto fornecido foi: (%.2f,%.2f)\n", p.x, p.y);
    system("pause");
    return 0;
}
