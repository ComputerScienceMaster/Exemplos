Algoritmo "Exercicio09"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia 2 notas, da P1 e da P2, calcule se o aluno passou ou não. Faça isso para 4 alunos. Use a estrutura PARA.
// Autor(a)    : vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
nota1, nota2: real
i: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   PARA i de 1 ate 4 faca
        ESCREVAl("Aluno ", i)
        ESCREVAl("Digite o valor da primeira nota")
        LEIA(nota1)
        ESCREVAl("Digite o valor da segunda nota")
        LEIA(nota2)
        ESCREVAl("média do aluno ", i, ": ", (nota1+nota2)/2)
   fimpara

Fimalgoritmo