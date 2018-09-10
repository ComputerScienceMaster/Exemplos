#include<stdio.h>
#include<stdlib.h>

void quickSort(int toSort[], int from, int to){
    int elements = to - from;
    if(elements > 1){
        int pivotPosition = pivotate(toSort, from, to);
        printf("\n from = %d - To = %d -  pivotPosition = %d", from, to, pivotPosition);
        quickSort(toSort, from, pivotPosition);
        quickSort(toSort, pivotPosition + 1, to);
    }
}

int pivotate(int toSort[], int initial, int end ){
    int minors = 0;
    printf("\n\ninitial = %d - estou usando o pivot = %d", initial, end-1);
    int pivot = toSort[end - 1];
    int i;
    for(i = 0 ; i < end - 1; i++){
        int actual = toSort[i];
        if(actual <= pivot){
            change(toSort,i,minors);
            minors++;
        }
    }
    change(toSort, end -1, minors);
    return minors;
}

void change(int toSort[], int from, int to){
    int value1 = toSort[from];
    int value2 = toSort[to];
    toSort[to] = value1;
    toSort[from] = value2;
}


void printVector(int toSort[], int sizeOfVector){
    printf("\n");
    int i;
    for(i = 0 ; i < sizeOfVector; i++){
        printf(" - %d - ", toSort[i]);
    }
    printf("\n");
}


void main(){
    int toSort[6] = {3,2,7,13,8,6};

    quickSort(toSort, 0, 6);

    printVector(toSort, 6);
}
