Algoritmo "Exercicio29"
// Disciplina  :  [Linguagem e Lógica de Programação] 
// Professor   : Antonio Carlos Nicolodi 
// Descrição   : 	Com o exercício anterior, informe a qtd de pessoas que estão na faixa do peso ideal (entre 18,5 e 24,99).
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
   i, qtdPessoasPesoIdeal: inteiro
   vetPes: vetor [0..10] de Pessoa
   

Inicio
// Seção de Comandos, procedimento, funções, operadores, etc...

   para i de 1 ate 2 faca
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
      se (vetPes[i].imc < 24.99) e (vetPes[i].imc > 18.5) entao
         qtdPessoasPesoIdeal := qtdPessoasPesoIdeal + 1
      fimse
   fimpara


   escreval("quantidade de pessoas no peso ideal:", qtdPessoasPesoIdeal)
   


Fimalgoritmo