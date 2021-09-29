# -*- coding: utf-8 -*-

from invoice2data import extract_data
from invoice2data.extract.loader import read_templates

templates = read_templates("Template/")
result = []
for i in range(1,4):    
    result.append(extract_data("Invoice/nome_do_arquivo_" + str(i) + ".pdf", templates=templates))
    
    
import csv

csv_file = "invoices.csv"
csv_columns = ['amount','currency','date','desc','invoice_number','issuer']

with open(csv_file, 'w') as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=csv_columns)
    writer.writeheader()
    for data in result:
        writer.writerow(data)
    
