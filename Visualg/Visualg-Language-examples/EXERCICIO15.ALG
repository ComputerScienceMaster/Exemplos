Algoritmo "Exercicio15"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : Faça um algoritmo que leia os dados abaixo, até que o nome do trabalhador seja igual a “Ricardo” e, para cada trabalhador lido, informe o valor do salário líquido:
     //a.	Nome do trabalhador
     //b.	Valor do salário bruto
     //c.	Valor do desconto do vale transporte
     //d.	Valor do desconto do vale alimentação
     //e.	Valor do desconto do plano de saúde
// Autor(a)    : Nome do(a) aluno(a)
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
            
            escreval("Salario liquido: ", salarioBruto - valeTransporte - valeAlimentacao - planoDeSaude)
            
            
            

            
   fimenquanto

Fimalgoritmo