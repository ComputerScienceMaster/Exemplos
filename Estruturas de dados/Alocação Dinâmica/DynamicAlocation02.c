#include <stdio.h>
#include <stdlib.h>

int main(){

    int **p;

    int i,j;

    i = 3;
    j = 3;

    /* aloca as linhas da matriz */
    p = (int **) calloc (i, sizeof(int *));
  if (p == NULL) {
     printf ("** Erro: Memoria Insuficiente **");
     return (NULL);
     }

     int m;
  /* aloca as colunas da matriz */
    for ( m = 0; m < i; m++ ) {
      p[m] = (float*) calloc (j, sizeof(float));
      if (p[m] == NULL) {
         printf ("** Erro: Memoria Insuficiente **");
         return (NULL);
         }
    }

    p[0][0] = 10;
    p[0][1] = 20;
    p[0][2] = 30;

    int k, l;
    for(k = 0 ; k < i ; k++){
        for(l = 0 ; l < j ; l++){
            printf("|%d|",p[k][l]);
        }
    }

}
