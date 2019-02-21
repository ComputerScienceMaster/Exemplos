Algoritmo "Exercicio14"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 10 números, usando a estrutura enquanto, no final informe:
     //a.	O maior número lido
     //b.	O menor número lido
     //c.	A média dos valores lidos
// Autor(a)    : Vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
i, numero,acumulador,maior, menor: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc...
   enquanto i < 10 faca
            escreval("digite um numero: ")
            leia(numero)
            se i = 0 entao
               menor := numero
            fimse
            acumulador := acumulador + numero
            se (maior < numero) entao
               maior := numero
            fimse
            se (menor > numero) entao
               menor:= numero
            fimse
            i := i + 1
   fimenquanto
   
   escreval("maior numero lido: ", maior)
   escreval("menor numero lido: ", menor)
   escreval("media dos valores lidos: ", acumulador/10)

Fimalgoritmo