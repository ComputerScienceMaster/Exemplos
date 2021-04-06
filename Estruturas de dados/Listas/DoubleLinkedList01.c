#include <stdio.h>
#include <stdlib.h>

struct list{
    int value;
    struct list* next;
    struct list* previous;
}; typedef struct list List;

void showList(List* list){
    List* actual = list;
    printf("\nImprimindo...\n");
    do{
        printf("\nValor: %d", actual->value);
        actual = actual->next;
    }while(actual != NULL);
}

List* removeFromList(List* list, int toRemove){
    List* aux = list;
    do{
        if(aux->value == toRemove){
            List* a = aux->next;
            List* c = aux->previous;

            if(a == 0){
                c->next = NULL;
                return aux;
            }
            if(c == 0){
                List* toReturn = aux->next;
                free(aux);
                return toReturn;
            }
            if(a != 0  &&  c != 0){
                c->next = a;
                a->previous = c;
                return list;
            }
        }
        aux = aux->next;
    }while(aux != NULL);
}

List* appendToList(List* list, int toAppend){
    //add into the final of the list
    List* newNode = (List*) malloc(sizeof(List));

    newNode->value = toAppend;
    newNode->next = list;
    newNode->previous = NULL;
    if(list != 0){
        list->previous = newNode;
    }
    return newNode;
}

void main (){
    List* list;
    list = NULL;
    list = appendToList(list,3);
    list = appendToList(list,4);
    list = appendToList(list,5);
    showList(list);
    list = removeFromList(list,4);
    showList(list);
}
