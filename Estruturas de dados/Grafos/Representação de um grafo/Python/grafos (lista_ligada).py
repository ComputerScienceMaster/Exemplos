# -*- coding: utf-8 -*-
"""
Created on Wed Nov 24 14:49:47 2021

@author: Vinicius
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
    
    
def createGraph(v):
    g = Graph()
    for i in range (0,v):
        g.edges.append(Edge(i))
    return g

def createNode(v, weight):
    n = Node()
    n.vertex = v
    n.weight = weight
    return n

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
    
def printGraph(g):
    for i in range (0, len(g.edges)):
        print ("v" + str(i), end=" →  ")
        for nodes in g.edges[i].nodes:
            print ("v" + str(nodes.vertex) + "(" + str(nodes.weight) + ")" , end="|")
        print("\n")
            
g = createGraph(4)
createEdge(g, 0,2,4)
createEdge(g, 0,1,10)
createEdge(g, 1,3,42)
createEdge(g, 2,3,21)
createEdge(g, 3,1,32)
createEdge(g, 4,2,14)

printGraph(g)