#include<stdio.h>

using namespace std;

int main(){
    // variavel com o valor 10
    int a = 10;

    //ponteiro de inteiro que recebe o endereço de A
    int *ponteiroParaA = &a;

    //vamos imprimir algumas coisas para ilustrar...

    printf("valor de a: %d\n", a);
    printf("valor de a usando o ponteiro: %d\n", *ponteiroParaA);
    printf("valor do ponteiroParaA: %x\n", ponteiroParaA);
}
