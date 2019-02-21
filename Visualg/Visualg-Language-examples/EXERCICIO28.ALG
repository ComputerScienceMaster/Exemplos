Algoritmo "Exercicio28"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   :Escreva um algoritmo que leia, em um vetor com 100 posições, os registros, com os dados de uma pessoa (nome, idade, altura, data de nascimento e peso. No final calcule o IMC, de cada uma das 100 pessoas, coloque o valor no IMC e apresente o valor calculado (IMC = Peso / Altura2)
// Autor(a)    : Vanessa
// Data atual  : 10/11/2018

tipo
    Pessoa = registro
    nome: caractere
    idade:   inteiro
    altura : real
    dataDeNascimento: caractere
    peso: real
    imc: real
fimregistro


Var
// Seção de Declarações das variáveis
   pes:Pessoa
   i: inteiro
   vetPes: vetor [0..100] de Pessoa
   

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc...

   para i de 1 ate 100 faca
      escreval("Digite o nome da pessoa:")
      leia(vetPes[i].nome)
      escreval("idade: ")
      leia(vetPes[i].idade)
      escreval("altura: ")
      leia(vetPes[i].altura)
      escreval("data de nascimento: ")
      leia(vetPes[i].dataDeNascimento)
      escreval("peso: ")
      leia(vetPes[i].peso)
      vetPes[i].imc := vetPes[i].peso/(vetPes[i].altura*vetPes[i].altura)
   fimpara
   
   para i de 1 ate 100 faca
      escreval("nome: ", vetPes[i].nome)
      escreval("idade: ", vetPes[i].idade)
      escreval("altura: ", vetPes[i].altura)
      escreval("data de nascimento: ", vetPes[i].dataDeNascimento)
      escreval("peso: ", vetPes[i].peso)
      escreval("IMC: ", vetPes[i].imc)
   fimpara


Fimalgoritmo