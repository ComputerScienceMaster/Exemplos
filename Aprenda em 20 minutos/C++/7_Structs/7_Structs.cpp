#include<stdio.h>

using namespace std;

struct pessoa{
    int codigo;
    char nome[40];
};

int main(){
    struct pessoa p;
    printf("Digite o código da pessoa: ");
    scanf("%d", &p.codigo);

    printf("Digite o nome pessoa: ");
    scanf("%s", &p.nome);

    printf("\nVamos imprimir o struct agora\n");
    printf("id: %d - nome: %s", p.codigo, p.nome);
}
