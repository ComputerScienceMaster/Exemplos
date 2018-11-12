Algoritmo "Exercicio18"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Leia a altura e sexo (1 para homens e 2 para mulheres) de 5 pessoas, usando a estrutura PARA, e no final informe:
   // a.	A maior altura
   // b.	A menor altura
   // c.	A média das alturas das mulheres
   // d.	A média das alturas gerais
// Autor(a)    : Vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis 
i, sexo: inteiro
acumulaAlturaFeminina,  acumulaAlturaGeral, maiorAltura, menorAltura, altura: real

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc... 
   para i de 1 ate 5 faca
        escreval("Digite a altura: ")
        leia(altura)
        escreval("Digite o sexo (1) masculino - (2) feminino")
        leia(sexo)
        se(sexo = 2) entao
                acumulaAlturaFeminina := acumulaAlturaFeminina + altura
        fimse
        se (i = 1) entao
           menorAltura := altura
        fimse
        acumulaAlturaGeral := acumulaAlturaGeral + altura
        se (maiorAltura < altura) entao
           maiorAltura := altura
        fimse
        se(menorAltura > altura) entao
           menorAltura := altura
        fimse
   fimpara

   escreval("maior altura: ", maiorAltura)
   escreval("menor altura: ", menorAltura)
   escreval("media das alturas das mulheres: ", acumulaAlturaFeminina /5)
   escreval("Media das alturas gerais: ", acumulaAlturaGeral/5)

Fimalgoritmo