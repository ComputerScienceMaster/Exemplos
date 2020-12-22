import scrapy
import sys
sys.path.append("C:/Users/Vinicius/Desktop/Alunos/bruna/Crowler Example/casasbahia/casasbahia")
from casasbahia.items import CasasbahiaItem

class EletroSpider(scrapy.Spider):
    name = 'eletro'
    allowed_domains = ['casasbahia.com.br']
    start_urls = ['https://casasbahia.com.br/']

    def start_requests(self):
        yield scrapy.Request("https://www.casasbahia.com.br/c/eletrodomesticos/refrigeradores/?Filtro=c13_c14&nid=201602", self.parse)

    def parse(self, response):
        data = []
        for a in response.css(".shelf-product"):
            NAME_SELECTOR = ".name-product span ::text"
            PRECO_SELECTOR = ".smallRating span ::text"
            QTD_AVALIACOES = ".qtdReviews ::text"
            nome = a.css(NAME_SELECTOR).getall()
            rating = a.css(PRECO_SELECTOR).getall()
            qtd = a.css(QTD_AVALIACOES).getall()
            for n in range(0,len(nome)):                
                data.append({'nome': nome[n], 'rating': rating[n], 'numberOfRatings:': qtd[n]})
        yield CasasbahiaItem(produtos=data)
