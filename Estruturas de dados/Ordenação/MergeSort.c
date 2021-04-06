#include<stdio.h>
#include<stdlib.h>

void mergeSort(int toSort[],int initial,int end){
    int mid;

    if(initial<end){
        mid=(initial+end)/2;
        mergeSort(toSort,initial,mid);        //left recursion
        mergeSort(toSort,mid+1,end);          //right recursion
        merge(toSort,initial,mid,mid+1,end);  //merging of two sorted sub-arrays
    }
}


void merge(int a[],int i1,int j1,int i2,int j2){
    int temp[50];    //array used for merging
    int i,j,k;
    i=i1;    //beginning of the first list
    j=i2;    //beginning of the second list
    k=0;

    while(i<=j1 && j<=j2)    //while elements in both lists
    {
        if(a[i]<a[j])
            temp[k++]=a[i++];
        else
            temp[k++]=a[j++];
    }

    while(i<=j1)    //copy remaining elements of the first list
        temp[k++]=a[i++];

    while(j<=j2)    //copy remaining elements of the second list
        temp[k++]=a[j++];

    //Transfer elements from temp[] back to a[]
    for(i=i1,j=0;i<=j2;i++,j++)
        a[i]=temp[j];
}


int main(){
    int i;
    int toSort[7] = {5,3,9,8,4,1,6};

    mergeSort(toSort,0,7);

    printf("\nSorted array is :");
    for(i=0;i<7;i++)
        printf("- %d -",toSort[i]);

    return 0;
}
