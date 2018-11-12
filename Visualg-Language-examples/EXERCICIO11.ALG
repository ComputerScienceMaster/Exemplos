Algoritmo "Exercicio11"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 1 número e escreva a tabuada do mesmo. Faça isso enquanto o usuário não digitar o número 99. Use a estrutura ENQUANTO
// Autor(a)    : vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
numero, i: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc...

   enquanto numero <> 99 faca
            escreval("Digite um numero")
            leia(numero)
            para i de 0 ate 10 faca
                 escreval(i, " x ", numero, " = ", i * numero)
            fimpara
   fimenquanto

Fimalgoritmo