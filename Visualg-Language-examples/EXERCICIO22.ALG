Algoritmo "Exercicio22"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Leia a idade e o estado de 100 pessoas. no final informe:
 //a.	a média das idades das pessoas
 //b.	total de pessoas que moram no estado do RJ
 //c.	quantas pessoas que moram no estado de SP e que tem idade para votar
 //d.	Quantas pessoas, do estado do RS que tem idade para se aposentar
 // Autor(a)    : Nome do(a) aluno(a)
// Data atual  : 10/11/2018
Var
// Seção de Declarações das variáveis 
acumulaIdade, idade, i, moramNoRio, moramEmSp, moramNoRs: inteiro
estado: caractere

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   para i de 1 ate 100 faca
          escreval("Digite o estado da pessoa: ")
          leia(estado)
          Escreval("escreva a idade da pessoa")
          leia(idade)
          acumulaIdade := acumulaIdade + idade
          se (estado = "rj") entao
               moramNoRio := moramNoRio + 1
          fimse
          se (estado = "sp") e (idade > 16) entao
               moramEmSp := moramEmSp + 1
          fimse
          se (estado = "rs") e (idade > 60) entao
               moramNoRs := moramNoRs + 1
          fimse
   fimpara
   
   escreval("Média de idades: ", acumulaIdade /100)
   escreval("Pessoas que moram no rio: ", moramNoRio)
   escreval("Pessoas que moram em SP e votam: ", moramEmSp)
   escreval("Pessoas que moram no RS e podem aposentar: ", moramNoRs)



Fimalgoritmo