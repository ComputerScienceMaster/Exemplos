Algoritmo "Exercicio07"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 2 números, calcule a média entre eles. No final informe qual dos dois números é maior que a média calculada
// Autor(a)    : Nome do(a) aluno(a)
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
num1, num2, media:real

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   escreva("digite o primeiro valor")
   leia(num1)
   escreva("digite o segundo valor")
   leia(num2)
   media := (num1+num2)/2
   se (num1 > media) entao
      escreva("o numero ", num1, "é maior que a média")
   senao
        se(num2 > media) entao
                escreva("o numero ", num2, " é maior que a média")
        fimse
   fimse
Fimalgoritmo