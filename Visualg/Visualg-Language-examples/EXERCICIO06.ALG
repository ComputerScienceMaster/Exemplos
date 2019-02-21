Algoritmo "Exercicio06"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 2 números e também a operação aritmética desejada (1 para soma, 2 para subtração, 3 para multiplicação e 4 para divisão) e informe qual o resultado da respectiva operação. Use a estrutura CASO.
// Autor(a)    : Vanessa
// Data atual  : 09/11/2018
var
   num1 : REAL
   num2 : REAL
   operacao : CARACTERE
   resultado : REAL
inicio
      ESCREVA ("Digite o primeiro número: ")
      LEIA (num1)
      ESCREVA ("Digite a operação: ")
      LEIA (operacao)
      ESCREVA ("Digite o segundo número: ")
      LEIA (num2)
      ESCOLHA operacao
         CASO "1"
            resultado := num1 + num2
         CASO "2"
            resultado := num1 - num2
         CASO "3"
            resultado := num1 * num2
         CASO "4"
            resultado := num1 / num2
      FIMESCOLHA

      ESCREVA ("Resultado: ", resultado)
   
Fimalgoritmo