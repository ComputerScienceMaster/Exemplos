#include <stdio.h>
#include <stdlib.h>

int main(){
    /*Alocação convencional*/
    int vetor[10] = {1,2,3,4,5,6,7,8,9,10};

    /*Alocação Dinâmina de um vetor*/
    int *aVetor;
    aVetor = (int *) malloc(10 * sizeof(int *));

    aVetor[0] = 1;
    aVetor[1] = 2;
    aVetor[2] = 3;
    aVetor[3] = 4;
    aVetor[4] = 5;
    aVetor[5] = 6;
    aVetor[6] = 7;
    aVetor[7] = 8;
    aVetor[8] = 9;
    aVetor[9] = 10;

    int i;
    for( i = 0; i < 10; i++){
        printf("Vetor [%d] = %d -- Vetor dinamico[%d] = %d \n", i,vetor[i],i,aVetor[i]);
    }
    system("pause");
}
