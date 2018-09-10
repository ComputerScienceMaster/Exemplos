#include <stdio.h>
#include <stdlib.h>

int main (){
    int *piValor; /* ponteiro para inteiro */
    int iVariavel = 1;
    piValor = &iVariavel; /* pegando o endereço de memória da variável */

    printf ("Endereco: %x\n", piValor);
    printf ("Valor : %d\n", *piValor);

    *piValor = 180118 ;
    printf ("Valor alterado: %d\n", iVariavel);
    printf ("Endereco : %x\n", piValor);
    system("pause");
    return 0;
}
