#include<stdio.h>
#include<stdlib.h>

void bubbleSort(int vectorToSort[], int sizeOfVector){
    int i, j;
    for (i = 0 ; i < sizeOfVector - 1; i++ ){
        for(j = 0 ; j < sizeOfVector - 1; j++){
            if(vectorToSort[j] > vectorToSort[j+1]){
                int aux = vectorToSort[j];
                vectorToSort[j] = vectorToSort[j+1];
                vectorToSort[j+1] = aux;
            }

        }
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

    bubbleSort(vectorToSort, 7);

    printVector(vectorToSort, 7);
}
