#include<stdlib.h>
#include<stdio.h>

struct queue{
    int value;
    struct queue* next;
};
typedef struct queue Queue;


Queue* initialize(){
    return NULL;
}

Queue* push(Queue* queue, int toPush){
    Queue* newNode = (Queue*) malloc(sizeof(Queue*));
    newNode->value = toPush;
    newNode->next = queue;
    return newNode;
}

void pop(Queue* queue){
    Queue* aux = queue;
    Queue* previous;
    while (aux != NULL){
        if(aux->next == 0){
            previous->next = NULL;
            free(aux);
            return;
        }
        previous = aux;
        aux = aux->next;
    }
}

void showQueue(Queue* queue){
    Queue* aux = queue;
    printf("\nprinting...\n");
    while(aux != NULL){
        printf("\nvalue: %d", aux->value);
        aux = aux->next;
    }
}

void main(){
    Queue* queue;
    queue = initialize();
    queue = push(queue, 6);
    queue = push(queue, 5);
    queue = push(queue, 3);
    queue = push(queue, 7);
    queue = push(queue, 8);
    showQueue(queue);
    pop(queue);
    pop(queue);
    showQueue(queue);
}
