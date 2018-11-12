Algoritmo "Exercicio12"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia diversos números, enquanto o número lido for um número inteiro e positivo. Ao final informe a média dos valores lidos.
// Autor(a)    : vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis
numero, qtd, acumulador: inteiro


Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   enquanto numero >= 0 faca
            escreval("digite um numero: ")
            leia(numero)
            acumulador := acumulador + numero
            qtd := qtd + 1
   fimenquanto
   escreval("média dos valores: ", acumulador/qtd)

Fimalgoritmo