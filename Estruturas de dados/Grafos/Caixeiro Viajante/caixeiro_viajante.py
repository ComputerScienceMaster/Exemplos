# -*- coding: utf-8 -*-
"""
Criação das Classes que vão representar o grafo
"""
class Node:
    vertex = 0
    weight = 0

class Edge:
    index = 0
    nodes = [] 
    
    def __init__(self, index):
        self.index = index
        
class Graph:
    edges = []
    

"""
Criação de um grafo - função que cria um grafo e inicializa os edges
"""
    
def createGraph(v):
    g = Graph()
    for i in range (0,v):
        g.edges.append(Edge(i))
    return g

"""
Cria um nó
"""
def createNode(v, weight):
    n = Node()
    n.vertex = v
    n.weight = weight
    return n

"""
Cria uma nova aresta
"""
def createEdge(g, vi, vf, w):
    if g is None:
        return False
    if vi < 0 or vi >= len(g.edges):
        return False
    if vf < 0 or vf >= len(g.edges):
        return False
    
    newNode = createNode(vf, w)
    if len(g.edges[vi].nodes) == 0 :
        g.edges[vi].nodes = [newNode]
    else:
        g.edges[vi].nodes.append(newNode)
    
"""
Mostra o grafo
"""
def printGraph(g):
    for i in range (0, len(g.edges)):
        print ("v" + str(i), end=" →  ")
        for nodes in g.edges[i].nodes:
            print ("v" + str(nodes.vertex) + "(" + str(nodes.weight) + ")" , end="|")
        print("\n")
        
"""
Valida uma rota
"""
def validarRota(g, rota):
    ## verifica se a rota repete algum valor
    a_set = set(rota)
    contains_duplicates = len(rota) != len(a_set)
    if contains_duplicates == True:

        return False
    
    ## verifica se a rota possui caminhos para percorrer as adjacencias
    for r in range (0,len(rota) -1):
        vi = rota[r]
        vf = rota[r + 1]
        checked = False
        for n in g.edges[vi].nodes:
            if n.vertex == vf:
                checked = True
        if checked == False:
            return False
    return True
                 
"""
Soma pesos
"""
def somaPesos(g, rota):
    ## Soma os pesos da rota
    soma = 0
    for r in range (0,len(rota) -1):
        vi = rota[r]
        vf = rota[r + 1]
        checked = False
        for n in g.edges[vi].nodes:
             if n.vertex == vf:
                soma += n.weight
                checked = True
        if checked == False:
            return False
    return soma

"""
Resolução de força bruta
"""
def resolverComForcaBruta():
    ## porquê não aplicar um pouco de força bruta?
    for a in range(0,4):
        for b in range(0,4):
            for c in range(0,4):
                for d in range(0,4):
                    rota = [a,b,c,d]
                    if(validarRota(g,rota) == True):
                        print("------------")
                        print(rota)
                        print(validarRota(g, rota))                
                        print(somaPesos(g,rota))
                        print("------------")
    
            
g = createGraph(4)
createEdge(g, 0,2,4)
createEdge(g, 0,1,10)
createEdge(g, 1,3,42)
createEdge(g, 2,3,21)
createEdge(g, 3,1,32)
createEdge(g, 4,2,14)

resolverComForcaBruta()
printGraph(g)