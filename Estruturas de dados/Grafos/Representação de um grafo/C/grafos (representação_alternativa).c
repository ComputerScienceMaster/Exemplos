#include<stdio.h>
#include<stdlib.h>
#define MAX 100

int index = 0;

struct edge{
    char start;
    char end;
    int weight;
    int checked;
};
typedef struct edge Edge;

struct graph{
    Edge edges[MAX];
    int numberOfEdges;
};
typedef struct graph Graph;

Graph initialize(Graph g){
    for (int x = 0; x < MAX; x++){
        g.edges[x].start = 0;
    }
    g.numberOfEdges = 0;
    return g;
}

void addEdge(Graph* g, char start, char end, int weight){
    if (index >= 100){
        printf("O Grafo está cheio");
    } else {
        Edge edge;
        edge.start = start;
        edge.checked = 0;
        edge.end = end;
        edge.weight = weight;
        g->edges[index] = edge;
        index++;
        g->numberOfEdges += 1;
    }
}

void showGraph(Graph g){
    ///imprimir todos os nós
    for (int x = 65; x < 90; x++){
        for (int y = 0; y < MAX; y++){
            if (g.edges[y].start == x){
                printf("%c - %d -  %c\n", g.edges[y].start, g.edges[y].weight, g.edges[y].end );
            }
        }
    }
}


int validarRota(char rota[], int tamanho, Graph g){
    for (int y = 0; y < tamanho; y++) {
        int check = 0;
        for (int x = 0; x < g.numberOfEdges; x++){
            if (g.edges[x].start == rota[y] && g.edges[x].end == rota[y + 1] && g.edges[x].checked == 0) {
                check = 1;
                g.edges[x].checked = 1;
                break;
            }
            if (g.edges[x].end == rota[y] && g.edges[x].start == rota[y + 1] && g.edges[x].checked == 0) {
                check = 1;
                g.edges[x].checked = 1;
                break;
            }
        }
        if (check == 0){
            return 0;
        }
    }
    return 1;
}

int somaPesos(char rota[], int tamanho, Graph g){
    int soma = 0;
    for (int y = 0; y < tamanho; y++) {
        for (int x = 0; x < g.numberOfEdges; x++){
            if (g.edges[x].start == rota[y] && g.edges[x].end == rota[y + 1]) {
                soma += g.edges[x].weight;
                break;
            }
            if (g.edges[x].end == rota[y] && g.edges[x].start == rota[y + 1]) {
                soma += g.edges[x].weight;
                break;
            }
        }
    }
    return soma;
}

void mostrarRotasValidas(Graph g, char first, char last){
    for (int j = 66; j <= 67; j++){
        char letra1 = j;
        for (int k = 66; k <= 67; k++){
            char letra2 = k;
            char rota[] = {first, letra1, letra2, last};
            if (validarRota(rota, 3, g)){
                int soma = somaPesos(rota, 3, g);
                printf("\n%c-%c-%c-%c => Peso: %d\n", first,letra1,letra2,last,soma);
            }
        }
    }
}

