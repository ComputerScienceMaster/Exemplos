Algoritmo "Exercicio02"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 2 números e informe qual dos dois números é o maior e qual é o menor
// Autor(a)    : vanessa
// Data atual  : 09/11/2018
Var

valor1, valor2:real


Inicio
escreva("Digite o primeiro valor: ")
leia(valor1)
escreva("Digite o segundo valor: ")
leia(valor2)

se(valor1 > valor2) entao
          escreva(valor1, " é o maior valor")
          escreva(valor2, " é o menor valor")
senao
          escreva(valor2, " é o maior valor")
          escreva(valor1, " é o menor valor")
fimse

Fimalgoritmo