#include<stdio.h>
#include<stdlib.h>

void selectionSort(int vectorToSort[], int sizeOfVector){
    int i, j, min, pos = 0;
    for (i = 0 ; i < sizeOfVector - 1; ++i ){
        min = i;
        for(j = i+1  ; j < sizeOfVector; ++j){
            if(vectorToSort[min] > vectorToSort[j]){
                min = j;
            }
        }
        int aux = vectorToSort[i];
        vectorToSort[i] = vectorToSort[min];
        vectorToSort[min] = aux;
        printf("\n\n");
    }
}

void printVector(int vectorToSort[], int sizeOfVector){
    int i;
    for(i = 0 ; i < sizeOfVector; i++){
        printf(" - %d - ", vectorToSort[i]);
    }
}


void main(){
    int vectorToSort[8] = {5,1,11,20,6,7,13};

    selectionSort(vectorToSort, 7);

    printVector(vectorToSort, 7);
}
