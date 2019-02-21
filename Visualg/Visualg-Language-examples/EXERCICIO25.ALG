Algoritmo "Exercicio25"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : 	Leia 40 idades em um vetor, depois imprima o resultado dos dados lidos na ordem inversa da leitura.
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis 
idades: vetor [1..40] de inteiro
i: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   para i de 1 ate 40 faca
        escreval("digite um numero:")
        leia(idades[i])
   fimpara

   escreval("na ordem inversa...")
   para i de 40 ate 1 passo - 1 faca
        escreva(idades[i])
   fimpara
   
Fimalgoritmo