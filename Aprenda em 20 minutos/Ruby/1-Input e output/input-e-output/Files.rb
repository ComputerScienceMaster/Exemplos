# Escrevendo em um arquivo
f = File.open('output.txt', 'w')
f.puts "The Ruby tutorial"
f.close

# Lendo e imprimindo o conteúdo em um terminal
d = File.read("testingFiles.txt")
puts(d)