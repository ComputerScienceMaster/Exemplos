#include<stdio.h>
#include<stdlib.h>

struct stack{
    int value;
    int next;
};
typedef struct stack Stack;

Stack* initialize(){
    return NULL;
}

Stack* push(Stack* stack, int toPush){
    Stack* newNode = (Stack*) malloc(sizeof(Stack*));
    newNode->value = toPush;
    newNode->next = stack;
    return newNode;
}

Stack* pop(Stack* stack){
    return stack->next;
}

void showStack(Stack* stack){
    Stack* aux = stack;
    printf("\nImprimindo...\n");
    do{
        printf("\nValue: %d", aux->value);
        aux = aux->next;
    }while(aux != NULL);
}
void main(){
    Stack* stack;
    stack = initialize();
    stack = push(stack, 4);
    stack = push(stack, 5);
    stack = push (stack,6);
    stack = push(stack, 7);
    showStack(stack);
    stack = pop(stack);
    stack = pop(stack);
    showStack(stack);

}
