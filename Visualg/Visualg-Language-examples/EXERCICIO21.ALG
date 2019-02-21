Algoritmo "Exercicio21"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Escreva um algoritmo que leia 100 números e armazene em um vetor. No final informe ao usuário
   //a.	Qual a média dos números?
   //b.	Quantos números estão acima da média?
   //c.	Entre os números lidos, quais os números que estão acima da média?
   //d.	Entre os números lidos, informe quais os 10 últimos números lidos
// Autor(a)    : Nome do(a) aluno(a)
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis 
numeros: vetor [1..100] de inteiro
i: inteiro
acumula, contaAcimaDaMedia: inteiro
media: real

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   para i de 1 ate 100 faca
        escreval("digite um numero: ")
        leia(numeros[i])
   fimpara
   
   escreval("10 ultimos numeros: ")
   para i de 1 ate 100 faca
        escreva(numeros[i])
   fimpara
   escreval("")
   para i de 1 ate 100 faca
        acumula := acumula + numeros[i]
   fimpara
   media := acumula /100
   escreval("A média dos numeros é: ", media)
   
   escreval("Estão acima da media os numeros: ")
   para i de 1 ate 100 faca
          se (numeros[i] > media) entao
             escreva(numeros[i])
             contaAcimaDaMedia := contaAcimaDaMedia + 1
          fimse
   fimpara
   escreval("")
   escreval("Quantidade de numeros acima da media: ", contaAcimaDaMedia)
   
   

Fimalgoritmo