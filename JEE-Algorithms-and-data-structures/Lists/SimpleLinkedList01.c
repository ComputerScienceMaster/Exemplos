#include<stdio.h>
#include<stdlib.h>
#define MAX 10

struct list{
    int value;
    struct list* next;
}; typedef struct list List;

List* initialize(){
    return NULL;
}

List* push(List* list, int toPush){
    List* newNode = malloc(sizeof(List*));
    newNode-> value = toPush;
    newNode->next = list;
    return newNode;
}

List* pop(List* list, int toPop){
    List* actual = list;
    List* previous = NULL;
    while(actual != NULL && actual->value != toPop){
        previous = actual;
        actual = actual->next;
    }
    if(previous == NULL){
        list = actual->next;
    }
    if(previous != NULL){
        previous->next = actual->next;
    }
    if(actual->next == NULL){
        previous->next = NULL;
    }
    return list;
}

void showList(List* list){
    List* aux = list ;
    printf("\nprinting\n");
    while(aux != NULL){
        printf("\n Value: %d", aux->value);
        aux = aux->next;
    }
}


void main(){
    List* list;
    list = initialize();
    list = push(list, 5);
    list = push(list, 4);
    list = push(list, 2);
    list = push(list, 8);
    list = push(list, 9);
    showList(list);
    list = pop(list,9);
    showList(list);
}
