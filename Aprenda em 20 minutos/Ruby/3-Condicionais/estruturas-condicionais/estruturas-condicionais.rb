# operador se

num = 8
if (num == 8)
  puts('legall')
end

# operador então

num = 9
if (num == 9)
  print('é nove')
else
  print('não é nove')
end

# encadeamento de If's

notadoaluno = 4

if (notadoaluno > 9)
  puts('ele é um gênio!')
elsif (notadoaluno < 9)
  if (notadoaluno > 5)
    puts ('nota regular')
  else
    puts('nota ruim')
  end
end

# igualdade, maior e menor, etc...
# == Igual
# != Diferente
# > Maior que
# < Menor que
# >= Maior ou igual que
# <= Menor ou igual que

num = 5

if(num == 5)
  puts('verdade')
end

if (num > 2)
  puts('verdade')
end

if (num < 10)
  puts('é menor que 10')
end

# operador lógico E

num1 = 2
num2 = 4

if (num1 == 2) and (num2 == 4)
  puts('os numeros são iguais a 2 e 4')
else
  puts('os numeros não são iguais a 2 e 4')
end

# operador lógico OR

nome = "cristiano"
time = "juventus"

if (nome == "cristiano") or (time == 'juventus' )
  puts('ele joga bem')
else
  puts('não sei quem é')
end

# operador lógico NOT

afirmacao = 'verdade'

#proposicao sem o not

if (afirmacao == 'verdade')
  puts('é verdade')
else
  puts('não é verdade')
end

#proposicao com o not

if not (afirmacao == 'verdade')
  puts('é verdade')
else
  puts('não é verdade')
end

# unless
age = 4
unless age > 4
  puts("não precisa ir a escola")
else
  puts("precisa ir")
end

#puts com condicional
puts "você é bem jovem" if(age < 10)

#switch case

lingua = "french"

case lingua
when "french"
  puts("fale francês")
  exit
when "spanish"
  puts("fale espanhol")
  exit
end