Algoritmo "Exercicio 05"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 2 números e também a operação aritmética desejada (S para soma, U para subtração, M para multiplicação e D para divisão) e informe qual o resultado da respectiva operação. Use a estrutura SE.
// Autor(a)    : Nome do(a) aluno(a)
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
num1, num2:real
op: caractere

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   escreva("Digite um valor para o primeiro numero: ")
   leia(num1)
   escreva("Digite um valor para o segundo numero: ")
   leia(num2)
   escreva("Selecione a operacao: s(soma) u(subtracao) m(multiplicacao) d(divisão)")
   leia(op)
   
   se(op = "s") entao
         escreva(num1 + num2)
   fimse
   se(op = "u") entao
         escreva(num1 - num2)
   fimse
   se(op = "m") entao
         escreva(num1 * num2)
   fimse
   se(op = "d") entao
         escreva(num1 / num2)
   fimse

Fimalgoritmo