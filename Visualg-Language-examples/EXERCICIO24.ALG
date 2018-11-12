Algoritmo "Exercicio24"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que tenha 30 posições em um vetor, onde cada valor armazenado corresponde ao quadrado de sua posição. Imprima depois o vetor resultante.
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis 
i: inteiro
v: vetor [1..30] de inteiro


Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
     para i de 1 ate 30 faca
            v[i] := i*i
     fimpara
     
     para i de 1 ate 30 faca
            escreva(v[i])
     fimpara

Fimalgoritmo