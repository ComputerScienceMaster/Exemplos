# -*- coding: utf-8 -*-

#importando tabelas em pdf usando o pytabula

from tabula import read_pdf

# faz a leitura de uma tabela complexa
holerite = read_pdf("C:/Users/Vinicius/Documents/GitHub/CSM-Examples/Python/Reading_PDF_Files/holerite1.pdf")
# claramente o resultado mostra dataframe bastante mal formado. A ferramenta tem dificuldade de 
# compreender como a tabela é formada e transforma-la em algo manipulável.)

# faz a leitura de uma tabela comum
tabelaComum = read_pdf("C:/Users/Vinicius/Documents/GitHub/CSM-Examples/Python/Reading_PDF_Files/testetabela.pdf")
# bastante fácil de compreender e manipular os dados

#Exemplos: 

# retorna a primeira linha da tabela completa
tabelaComum.iloc[0]

# pega o primeiro dado da tabela
tabelaComum.iloc[0][0]

# exibe todos os nomes da (primeira coluna)
tabelaComum['Nome']


# exibe o primeiro nome da tabela 
tabelaComum['Nome'][0]

#conta quantas linhas a tabela tem
len(tabelaComum.iloc[0])

print(tabelaComum)