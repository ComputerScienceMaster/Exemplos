Algoritmo "Exercicio20"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Crie uma função que receba 2 números, além dos dois números, receba também uma operação matemática (1 para soma, 2 para subtração, 3 para multiplicação e 4 para divisão) e retorne à multiplicação dos dois números para o programa principal. Para esse exercício é necessário usar FUNÇÃO.
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis 

funcao calculadora(num1, num2, op:inteiro):real
inicio
     ESCOLHA op
        caso 1
           retorne num1 + num2
        caso 2
           retorne num1 - num2
        caso 3
           retorne num1 * num2
        caso 4
           retorne num1 / num2
     FIMESCOLHA
fimfuncao


Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
     escreva(calculadora(2,4,4))

Fimalgoritmo