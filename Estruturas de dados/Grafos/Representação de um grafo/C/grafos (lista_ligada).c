
#include <stdio.h>
#include <stdlib.h>

/*
Este código representa uma forma de mostrar grafos usando listas ligadas.
Essa representação talvez seja a mais clássica para representação de grafos usando a linguagem C.
*/


/*
Estruturas (structs) que "guardam os dados" dos grafos. Aqui utilizamos basicamente três estruturas: nó, vértice, grafo.

Um nó possui: vertex, peso e próximo nó.

*/

typedef struct node {
    int vertex;
    int weight;
    struct node *next;
}Node;


/*

Um nó guarda informações de um ponto do grafo e também o peso para chegar nesse ponto.
Veja o seguinte exemplo:

Imagine duas cidades: Astorga, Campinas e Blumenau.

Se fossemos representar uma cidade usando nós teriamos:

Nó 1: Astorga
	vertex = 1;
	weight = 10;
	next = null;

Certo, agora você precisa entender que esse nó faz parte de uma estrutura de dados "lista encadeada".
A lista encadeada de nó é na verdade uma aresta ou "edge".

Então pense da seguinte forma, dadas as cidades:

[Astorga] [Campinas] [Blumenau]

Podemos representar um grafo mostrando as arestas que partem de cada uma delas:

**Edge1** [Astorga] -> [Campinas [peso: 4]] | [Blumenau [peso: 7]]
**Edge2** [Campinas] ->  [Astorga [peso: 9]]
**Edge3** [Blumenau] ->  [Astorga [peso: 6]]

Portanto, veja como fica a estrutura da aresta:

*/

typedef struct edge {
    Node *cab;
}Edge;

/*

Se você quiser montar um grafo, é bem simples. Você precisa apenas criar uma estrutura que é uma lista ligada de arestas.
Além disso, você pode adicionar informações adicionais como a quantidade de nós ou então a quantidade de arestas no grafo.

*/

typedef struct graph {
    int nodes;
    int edges;
    Edge *adj;
}Graph;



/*

Para criar um grafo, você precisa informar quantos vértices esse grafo terá.
Então se você possui 4 cidades, a função "createGraph" precisa ser chamada passando o parâmetro 4

*/

Graph *createGraph(int v) {
	int i;
    Graph *g = (Graph *)malloc(sizeof(Graph)); // Aloca a estrutura "graph" completa
	g->nodes = v; // atribui v a quantidade de nós
	g->edges = 0; // atribui 0 a quantidade de edges
	g->adj = (Edge*)malloc(v*sizeof(Edge)); // aloca um edge para ser a "cabeça" da nossa lista ligada
	for (i=0; i < v; i++){
		g->adj[i].cab = NULL; // atribui NULL para todos os nós que estão dentro da lista de adjacências
	}
	return(g); // retorna o grafo
}

/*
Essa função cria um novo nó dinamicamente e retorna esse nó.
*/

Node *createNode(int v, int weight){
	Node *temp = (Node *) malloc (sizeof(Node)); // aloca um novo nó
	temp->vertex =v; // coloca um valor no nó
	temp->weight = weight; // coloca um peso para o nó
	temp->next = NULL; // coloca o null como próximo nó
	return(temp); // retorna um nó
}

/*
Essa função cria um novo edge
*/
int createEdge(Graph *gr, int vi, int vf, int p) {
	if(!gr) return (0); // se o grafo estiver NULL
	// Retorna zero se o valor final e valor inicial for menor que zero ou então maior que o número de nós
	if((vf < 0)||(vf >= gr->nodes))
        return(0);
	if((vi < 0)||(vf >= gr->nodes))
        return(0);

	Node *newNode = createNode(vf,p); // Cria o novo nó
	newNode->next = gr->adj[vi].cab; // faz o encadeamento do novo edge
	gr->adj[vi].cab = newNode; // substitui a cabeça para o novo nó criado
	gr->edges++; // adiciona um edge ao grafo

	return 1;
}

/*
Essa função mostra o grafo completo
*/

void printGraph(Graph *gr){

	printf("nodes: %d. Arestas: %d. \n",gr->nodes,gr->edges);
	int i;
    // faz uma iteração dentro do grafo
	for(i = 0 ; i < gr->nodes ; i++){
		printf("v%d: ",i);
		Node *ad = gr->adj[i].cab;
			while(ad){
				printf("v%d(%d) ", ad->vertex , ad->weight);
				ad = ad->next;
			}
		printf("\n");
	}
}

int main()
{
    Graph * gr = createGraph(4);
	createEdge(gr, 0, 2, 4);
	createEdge(gr, 0, 1, 10);
	createEdge(gr, 1, 3, 42);
	createEdge(gr, 2, 3, 21);
	createEdge(gr, 3, 1, 32);
	createEdge(gr, 4, 2, 14);

    printGraph(gr);
}
