#define MAX 10

void initialize(int stack[]){
    int i = 0;
    for (i = 0 ; i < MAX ; i++)
        stack[i] = -1;
}

void push(int stack[], int toAdd){
    int i;
    for(i = 0 ; i < MAX; i++){
        if(stack[i] == -1){
            stack[i] = toAdd;
            break;
        }
    }
}

void show(int stack[]){
    int i;
    printf("\nstarting impression\n");
    for ( i = 0 ; i < MAX ; i++){
        printf("\nValor %d", stack[i]);
    }
}

void pop(int stack[]){
    int i;
    for(i = 0 ; i < MAX; i++){
        if(stack[i] == -1){
            stack[i-1] = -1;
            break;
        }
    }
}


void main(){
    int stack[MAX];
    initialize(stack);
    push(stack, 4);
    push(stack,3);
    push(stack,2);
    show(stack);
    pop(stack);
    show(stack);
}
