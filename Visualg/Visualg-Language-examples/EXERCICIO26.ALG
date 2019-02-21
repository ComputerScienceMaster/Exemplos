Algoritmo "Exercicio26"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : 	Leia 50 números em um vetor, depois acrescente 1% ao valor lido de cada posição até o final do vetor e imprima o resultado.
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis
numeros: vetor [1..50] de real
i, numero: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc...
   para i de 1 ate 50 faca
        escreval("digite um numero:")
        leia(numero)
        numeros[i] := numero * 1.01
   fimpara

   escreval("na ordem inversa...")
   para i de 1 ate 50 faca
        escreva(numeros[i])
   fimpara

Fimalgoritmo