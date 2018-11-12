Algoritmo "Exercicio 04"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 3 números e informe qual o maior entre os mesmos
// Autor(a)    : vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis
num1, num2, num3: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   escreva("Digite o valor para o primeiro número")
   leia(num1)
   escreva("Digite o valor para o segundo número")
   leia(num2)
   escreva("Digite o valor para o terceiro número")
   leia(num3)
   
   se(num1 > num2) e (num1 > num3) entao
         escreva("o numero ", num1, " é o maior")
   fimse
   se(num2 > num1) e (num2 > num3) entao
                escreva("o numero ", num2, " é o maior")
   fimse
   se(num3 > num1) e (num3 > num2) entao
                escreva("o numero ", num3, " é o maior")
   fimse

Fimalgoritmo