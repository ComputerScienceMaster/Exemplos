#define MAX 10

void initialize(int vet[]){
   int i;
   for(i = 0 ; i < MAX; i++)
        vet[i] = -1;
}

void append(int vet[], int value){
    int i;
    for (i = 0 ; i < MAX ; i++){
        if(vet[i] == -1){
            vet[i] = value;
            break;
        }
    }
}

void remove(int vet[], int value){
    int found = 0;
    int i;
    for(i = 0 ; i < MAX; i++){
        if( vet[i] == value){
            found = i;
        }
    }

    for (i = 0 ; i< MAX ; i++ ){
        vet[i] = vet[i+1];
        vet[MAX] = -1;
    }
}

void show(int vet[]){
    int i;
    printf("----printing the list-----\n");
    for(i = 0 ; i < MAX; i++){
        printf("position: %d - value: %d\n", i, vet[i]);
    }
}

void search(int vet[], int toSearch){
    int i;
    for(i = 0 ; i < MAX ; i++){
        if(vet[i] == toSearch){
            printf("Valor encontrado na posicao: %d\n", i );
        }
    }
}

void main(){
    int vet[MAX];
    initialize(vet);
    append(vet,1);
    append(vet,2);
    append(vet,3);
    remove(vet,3);
    show(vet);
    show(vet);
    search(vet,3);
}
