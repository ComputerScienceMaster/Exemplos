#include<stdio.h>
#include<stdlib.h>
#define MAX 4

void initialize(int queue[]){
    int i;
    for(i = 0 ; i < MAX ; i++){
        queue[i] = -1;
    }
}

void showQueue(int queue[]){
    int i;
    printf("printing...");
    for(i = 0 ; i < MAX ; i++){
        printf("\nValue: %d", queue[i]);
    }
}

void push(int queue[], int toPush){
    int i;
    for(i = MAX ; i >=0  ; i--){
        queue[i] = queue[i - 1];
    }
    queue[0] = toPush;
}

void pop(int queue[]){
    int i;

    for(i = 0 ; i < MAX ; i++){
        if(queue[i] == -1){
            queue[i - 1] = -1;
            return;
        }
    }
    queue[MAX-1] = -1;
}


void main(){
    int queue[MAX];
    initialize(queue);
    push(queue, 5);
    push(queue, 4);
    push(queue, 9);
    push(queue, 8);
    showQueue(queue);
    pop(queue);
    showQueue(queue);
}
