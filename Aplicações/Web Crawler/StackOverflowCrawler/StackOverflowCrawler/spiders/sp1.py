import scrapy

from stackoverflowcrawler.items import StackoverflowcrawlerItem

class Sp1Spider(scrapy.Spider):
	name = 'sp1'
	allowed_domains = ['stackoverflow.com']
	start_urls = ['https://stackoverflow.com/']
	
	def start_requests(self):
		yield scrapy.Request('https://stackoverflow.com/questions/11827176/', self.parse)
		yield scrapy.Request('https://stackoverflow.com/questions/11827175/', self.parse)
		yield scrapy.Request('https://stackoverflow.com/questions/11827174/', self.parse)

	def parse(self, response):
		for h1 in response.xpath('//*[@id="question-header"]/h1/a').getall():
			yield StackoverflowcrawlerItem(titulo=h1)

		for href in response.xpath('//*[@id="question-header"]/h1/a').getall():
			yield scrapy.Request(response.urljoin(href), self.parse)
