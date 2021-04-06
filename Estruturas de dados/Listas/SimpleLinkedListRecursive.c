#include<stdio.h>
#include<stdlib.h>

struct list{
    int value;
    struct list* next;
};
typedef struct list List;

List* initialize(){
    return NULL;
}

List* push(List* list, int toPush){
    List* newNode = (List*) malloc(sizeof(List*));
    newNode->value = toPush;
    newNode->next = list;
    return newNode;
}

List* pop(List* list, int toRemove){
    if(list == NULL){
        return;
    }
    if (list->value == toRemove) {
        List* t = list;
        list = list->next;
        free(t);
    }
    else {
        list->next = pop(list->next,toRemove);
    }
    return list;
}

void showList(List* list){
    if(list == NULL){
        return;
    }
    printf("\n Value: %d", list->value);
    showList( list->next);
}

void main(){
    List* list;
    list = initialize();
    list = push(list, 5);
    list = push(list, 6);
    list = push(list, 3);
    list = push(list, 8);
    list = push(list, 4);
    printf("\nfirst print\n");
    showList(list);
    pop(list,8);
    printf("\nsecond print\n");
    showList(list);
}
