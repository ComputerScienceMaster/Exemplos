# Exemplo de como pegar texto no terminal

print "Enter your name: "
name = gets
puts "Hello #{name}"

# Pegando o tamanho da entrada
print "Enter your name again: "
numberofcharacters = gets.chomp
puts "This string have #{numberofcharacters.size} characters"