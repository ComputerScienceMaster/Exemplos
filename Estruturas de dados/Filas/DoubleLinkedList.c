#include<stdio.h>
#include<stdlib.h>


//    |  m  |   j  |   -> caixa

//Fila simplesmente encadeada


struct fila{
    int valor;
    struct fila* proximo;
    struct fila* anterior;
};

typedef struct fila Fila;


Fila* initialize(){
    return NULL;
}

Fila* enfileirar(Fila* fila, int paraEnfileirar){

    Fila* newNode = (Fila*) malloc(sizeof(Fila));

    if (fila == NULL){
        newNode->valor = paraEnfileirar;
        newNode->proximo = NULL;
        newNode->anterior = NULL;

    }else{
        newNode->valor = paraEnfileirar;
        newNode->proximo = NULL;
        fila->proximo = newNode;
        newNode->anterior = fila;

    }
    //printf("[ endereco atual [%x] valor atual ->[%d] --- proximo-> [%x] anterior-> [%x]] \n", newNode, newNode->valor, newNode->proximo, newNode->anterior);

    return newNode;



}

void desenfileirar(Fila* fila){
    Fila* aux = fila;
    Fila* penultimo;
    while(aux->anterior != NULL){
        penultimo = aux;
        aux = aux->anterior;
    }
    penultimo->anterior = NULL;
}

void mostrarFila(Fila* fila){
    printf("\nImprimindo...\n\n");
    for (Fila* aux = fila; aux != NULL; aux = aux->anterior){
        printf("[ endereco atual [%x] valor atual ->[%d] --- proximo-> [%x] anterior-> [%x]] \n", aux, aux->valor, aux->proximo, aux->anterior);
    }
    printf("\nFim\n");

}


void main(){

    Fila* fila = initialize();
    fila = enfileirar(fila, 4);
    fila = enfileirar(fila, 6);
    fila = enfileirar(fila, 9);
    fila = enfileirar(fila, 1);
    desenfileirar(fila);
    fila = enfileirar(fila, 2);
    fila = enfileirar(fila, 0);
    fila = enfileirar(fila, 5);
    desenfileirar(fila);

    mostrarFila(fila);


}
