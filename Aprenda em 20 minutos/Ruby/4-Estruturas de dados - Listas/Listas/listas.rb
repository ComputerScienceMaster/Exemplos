# listas são coleções não homogeneas de dados (os dados não são do mesmo tipo)

lista1 = [10, 'eu sou legal', 13.5]
puts(lista1)

# posso imprimir um elemento por vez

puts(lista1[1])

# posso ainda atualizar um valor na lista

puts(lista1[1])
lista1[1] = 'eu sou muito legal demais'
puts(lista1[1])

# posso eliminar o ultimo elemento da lista com a função pop()

lista2 = [10,20,30,40]
lista2.pop()
puts(lista2)


# ou também posso eliminar um elemento especifico da lista com o operador del

lista3 = [10,20,30,40,50,60]

lista3.delete(30)
puts("imprimindo a lista")
print(lista3)

# Matrizes são listas de listas

matriz1 = [[10,20],[30,40],[50,60]]
puts("Imprimindo a matriz")
print(matriz1)




