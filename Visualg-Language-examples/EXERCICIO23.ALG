Algoritmo "Exercicio23"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Escreva um algoritmo que leia a idade de 20 pessoas em um vetor A, depois copie os dados das 20 pessoas do vetor A para o vetor B, dividindo por 2 o valor da posição do vetor A.
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis 
i: inteiro
idds: vetor [0..20] de inteiro
metadeDasIdds: vetor [0..20] de real


Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
     para i de 1 ate 20 faca
            escreval("digite um numero:")
            leia(idds[i])
            metadeDasIdds[i] := idds[i] / 2
     fimpara
     
     para i de 1 ate 20 faca
            escreva(metadeDasIdds[i])
     fimpara

Fimalgoritmo