#include<iostream>
#include<stdio.h>

using namespace std;

int main(){
    int a = 0;
    printf("Digite um numero:");
    scanf("%d", &a);
    if(a > 0){
        printf("o valor eh positivo\n");
    }else{
        printf("o numero eh negativo\n");
    }

    switch(a){
        case 1:
            printf("o valor eh 1\n");
        case 2:
            printf("o valor eh 2\n");
    }
}
