Algoritmo "Exercicio16"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Com o algoritmo anterior, caso a qtd de descontos seja maior que o salário bruto, informe que os descontos não podem ser maiores que o salário bruto, senão informe o salário líquido.
// Autor(a)    : Vanessa
// Data atual  : 09/11/2018
Var
// Seção de Declarações das variáveis
nome: caractere
salarioBruto, valeTransporte, valeAlimentacao, planoDeSaude: real

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc...
   enquanto nome <> "ricardo" faca
            escreval("Digite o nome do trabalhador: ")
            leia(nome)
            escreval("digite o salario bruto do trabalhador: ")
            leia(salarioBruto)
            escreval("digite o vale transporte do trabalhador: ")
            leia(valeTransporte)
            escreval("digite o vale alimentacao do trabalhador: ")
            leia(valeAlimentacao)
            escreval("digite o plano de saude do trabalhador: ")
            leia(planoDeSaude)

            se(salarioBruto < valeTransporte + valeAlimentacao + planoDeSaude) entao
                escreval("Os descontos não podem ser maiores que o salario bruto")
            senao
                 escreval("Salario liquido: ", salarioBruto - valeTransporte - valeAlimentacao - planoDeSaude)
            fimse




   fimenquanto

Fimalgoritmo