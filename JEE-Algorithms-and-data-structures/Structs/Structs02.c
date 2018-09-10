#include <stdio.h>
#include <stdlib.h>

struct ponto {
    float x;
    float y;
};

void imprime(struct ponto* pp){
    printf("O ponto fornecido foi: (%.2f,%.2f)\n", pp->x, pp->y);
}

void captura(struct ponto* pp){
    printf("Digite as coordenadas do ponto(x y): ");
    scanf("%f %f", &pp->x, &pp->y);
}

int main(){
    struct ponto p;
    captura(&p);
    imprime(&p);
    system("pause");
    return 0;
}
