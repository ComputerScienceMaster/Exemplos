# -*- coding: utf-8 -*-
"""
Created on Fri Mar 25 09:38:59 2022

@author: Vinicius
"""
import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_excel("gerarGraficoComPython.xlsm");

plt.figure(figsize=(10,6))
# adicionando o título
plt.title("Temperatura das cidades")
## adicionando alguns grids
plt.grid(True, color = "grey", linewidth = "1", linestyle = "-.")
plt.bar(df['Cidade'], df['Temperatura'],  color="k")
plt.xticks(df['Cidade'], rotation='vertical')
plt.show()