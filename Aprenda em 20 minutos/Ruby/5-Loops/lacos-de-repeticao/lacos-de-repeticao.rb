# laços de repetição
# while

num = 0

while num < 10
  puts ("while: " + num.to_s)
  num += 1

  #podemos quebrar usando break
end

#until é ao contrário do while
a = 20

until (a < 10)
  puts ("until: " + a.to_s)
  a -= 1
  #podemos quebrar usando break
end

# O laço Do é necessário ser quebrado com break
i = 0
loop do
  i += 2
  puts i
  if i == 10
    break
  end
end

# O laço for utilizando um range:

for i in 1..3 do
  puts("for: " + i.to_s)
end

# Exemplo de um laço for iterando sobre uma lista

lista = [1, 2, 3, 4, 5]

for i in lista do
  puts("lista: " + i.to_s)
end

# você pode iterar usando iterators também:

names = ['Bob', 'Joe', 'Steve', 'Janice', 'Susan', 'Helen']
names.each{|name| puts name}

x = 1
names.each do |name|
  puts "#{x}. #{name}"
  x += 1
end
