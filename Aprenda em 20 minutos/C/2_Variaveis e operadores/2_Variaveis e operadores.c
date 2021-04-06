void main(){
    //regra geral para declaração -> tipo de dados + nome da
    //variável começando com uma letra sempre

    // tipos aceitos dentro da linguagem C
    int inteiro = 0;
    char caractere = 'c';
    float pontoFlutuante = 0.5;
    double dobroDoFloat = 0.6;
    int *ponteiro = &inteiro;

    //não existem booleanos
    //não existem o tipo String


    printf("Imprimindo um inteiro: %d", inteiro);
    printf("Imprimindo um caractere: %c", caractere);
    printf("Imprimindo um pontoFlutuante: %f", pontoFlutuante);
    printf("Imprimindo um double: %lf", dobroDoFloat);
    printf("Imprimindo um ponteiro: %x", ponteiro);
}
