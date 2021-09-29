
#include <stdio.h>
#include <stdlib.h>
#define true 1
#define fase 0

typedef struct node {
    int vertex;
    int weight;
    struct node *next;
}Node;

typedef struct edge {
    Node *cab;
}Edge;

typedef struct graph {
    int nodes;
    int edges;
    Edge *adj;
}Graph;


Graph * createGraph(int v) {
	int i;

	Graph *g = (Graph *)malloc(sizeof(Graph));
	g->nodes = v;
	g->edges = 0;
	g->adj = (Edge*)malloc(v*sizeof(Edge));
	for (i=0; i < v; i++){
		g->adj[i].cab = NULL;
	}
	return(g);
}


Node *createNode(int v, int weight){
	Node *temp = (Node *) malloc (sizeof(Node));
	temp->vertex =v;
	temp->weight = weight;
	temp->next = NULL;
	return(temp);
}

int createEdge(Graph *gr, int vi, int vf, int p) {
	if(!gr) return (0);
	if((vf<0)||(vf >= gr->nodes))
        return(0);
	if((vi<0)||(vf >= gr->nodes))
        return(0);

	Node *newNode = createNode(vf,p);
	newNode->next = gr->adj[vi].cab;
	gr->adj[vi].cab = newNode;
	gr->edges++;

	return 1;
}

void imprime(Graph *gr){

	printf("nodes: %d. Arestas: %d. \n",gr->nodes,gr->edges);
	int i;

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
    Graph * gr = createGraph(5);
	createEdge(gr, 0, 1, 2);
	createEdge(gr, 1, 2, 4);
	createEdge(gr, 2, 0, 12);
	createEdge(gr, 2, 4, 40);
	createEdge(gr, 3, 1, 3);
	createEdge(gr, 4, 3, 8);

    imprime(gr);
}
