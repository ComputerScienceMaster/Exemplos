#include<stdio.h>
#include<stdlib.h>

void insertionSort(int vectorToSort[], int sizeOfVector){
    int i, j, tmp;
    for (i = 1; i < sizeOfVector; i++) {
        j = i;
        while (j > 0 && vectorToSort[j - 1] > vectorToSort[j]) {
            tmp = vectorToSort[j];
            vectorToSort[j] = vectorToSort[j - 1];
            vectorToSort[j - 1] = tmp;
            j--;
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

    insertionSort(vectorToSort, 7);

    printVector(vectorToSort, 7);
}
