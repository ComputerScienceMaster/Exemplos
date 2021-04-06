#include <stdio.h>
#include <stdlib.h>

void imprimir_vetor ( int n , int *v ) {
    int i;
    for (i = 0; i < n ; i++)
         printf("|%d|", v[i]);
    printf("\n\n\n");
}

void imprimir_matriz ( int n , int m[][3] ) {
    int i,j;
    for (i = 0; i < n; i++){
        for (j = 0; j < n; j++){
            printf("|%d|", m[i][j]);
        }
        printf("\n");
    }
    printf("\n\n\n");

}

int main(){
    int v1[5] = { 5, 10, 15, 20, 25 };
    int v2[] = { 5, 10, 15, 20, 25 };
    int m1[4][3] =  {{5,10,15},
                    {20,25,30},
                    {35,40,45},
                    {50,55,60}};
    int m2[4][3] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
    int m3[4][3] = {1,2,3,4,5,6,7,8,9,10,11,12};

    imprimir_vetor(5, v1);
    imprimir_vetor(5, v2);

    imprimir_matriz(4, m1);
    imprimir_matriz(4, m2);
    imprimir_matriz(4, m3);

    system("pause");

}
