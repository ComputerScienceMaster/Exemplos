#include<stdio.h>

using namespace std;

int main(){
    int vet[10];

    printf("\nvetor!\n");

    int i = 0;
    for(i = 0; i < 3; i++){
        printf("Digite um numero:");
        scanf("%d", &vet[i]);
    }
    for(i = 0; i < 3; i++){
        printf("numero digitado: %d\n", vet[i]);
    }


    //matrizes

    printf("\nMatriz!\n");
    int matriz[3][3];

    int j, k;
    for(j = 0 ; j < 3; j++){
        for(k = 0 ; k < 3; k++){
            printf("Digite um numero:");
            scanf("%d", &matriz[j][k]);
        }
    }
    for(j = 0 ; j < 3; j++){
        printf("\n");
        for(k = 0 ; k < 3; k++){
             printf(" - %d - " , matriz[j][k]);
        }
    }

}
