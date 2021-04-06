#include<stdio.h>

using namespace std;

void imprimirNome(char nome[]){
    int i = 0 ;
    while( i < 10 ){
        printf("%c",nome[i]);
        i++;
    }

}

void imprimirComPonteiro(char *nome){
    int i = 0 ;
    while( i < 10 ){
        printf("%c",nome[i]);
        i++;
    }
}


void imprimirComPonteiroOperadorAdicao(char *nome){
    int i = 0 ;
    while( i < 10 ){
        printf("%c",*(nome + i));
        i++;
    }
}

int main(){

    char nome[10];
    printf("Digite o seu nome: ");
    scanf("%s", &nome);
    printf("\n Eu estou imprimindo sem ponteiros:\n");
    imprimirNome(nome);

    printf("\n eu estou imprimindo com ponteiro: \n");
    imprimirComPonteiro(nome);

    printf("\n eu estou imprimindo com utilizando o operador + : \n");
    imprimirComPonteiroOperadorAdicao(nome);

}


