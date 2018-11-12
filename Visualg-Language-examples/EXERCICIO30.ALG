Algoritmo "Exercicio30"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Para 100 alunos de uma turma, leia o nome do aluno, nome da disciplina, nota da P1, nota da P2 e nota da P3, dentro de um registro. No final informe, para cada aluno, em cada disciplina, se o mesmo foi aprovado ou reprovado, usando a mesma regra de aprovação da UCAM.
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018

tipo
    Aluno = registro
    nome: caractere
    disciplina:   caractere
    p1:real
    p2: real
    p3: real
    status: caractere
fimregistro

Var
// Seção de Declarações das variáveis 
i: inteiro
pes: vetor [0..100] de Aluno
media:real

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
       para i de 1 ate 100 faca
            escreval("digite o nome do aluno: ")
            leia(pes[i].nome)
            escreval("digite o nome da disciplina: ")
            leia(pes[i].disciplina)
            escreval("digite a nota da p1:")
            leia(pes[i].p1)
            escreva("digite a nota da p2:")
            leia(pes[i].p2)
            escreva("digite a nota da p3:")
            leia(pes[i].p3)
            media := (pes[i].p1 + pes[i].p2 + pes[i].p3)/3
            se (media > 70) entao
               pes[i].status := "aprovado"
            senao
               pes[i].status := "reprovado"
            fimse
            
       fimpara
       
       para i de 1 ate 100 faca
              escreval(pes[i].nome, " : ", pes[i].status)
       fimpara
       

Fimalgoritmo