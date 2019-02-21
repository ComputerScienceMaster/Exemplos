Algoritmo "Exercicio13"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia salários e a qtd de filhos de diversos funcionários, até que a qtd de filhos seja um valor negativo. No final, informe:
     //a.	A média dos salários lidos
     //b.	A média da qtd de filhos lidos
     //c.	Qual o maior salário lido
     //d.	% de pessoas que tem salário até 100 reais
// Autor(a)    : vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
salario, acumuladorSalario, acumuladorFilhos, maiorSalario, salarioAte100: real
qtdFilhos, qtdPessoas: inteiro

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   enquanto qtdFilhos >= 0 faca
            escreval("digite o salario: ")
            leia(salario)
            escreval("Digite a quantidade de filhos")
            leia(qtdFilhos)
            acumuladorSalario := acumuladorSalario + salario
            acumuladorFilhos := acumuladorFilhos + qtdFilhos
            qtdPessoas := qtdPessoas + 1
            se (salario <= 100) entao
               salarioAte100 := salarioAte100 + 1
            fimse
            se (maiorSalario < salario) entao
               maiorSalario := salario
            fimse
   fimenquanto

   escreval("Média dos salarios lidos: ", acumuladorSalario/qtdPessoas)
   escreval("Média da qtd de filhos lidos: ", acumuladorFilhos/qtdPessoas)
   escreval("Maior salario Lido: ", maiorSalario)
   escreval("Porcentagem de pessoas com salario até 100: ", salarioAte100/qtdPessoas)

Fimalgoritmo