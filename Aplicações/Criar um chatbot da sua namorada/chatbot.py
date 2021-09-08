# -*- coding: utf-8 -*-

import re
from chatterbot import ChatBot
from chatterbot.trainers import ListTrainer


corpus = open(r"conversa.txt", encoding="utf-8").read()

preprocessedCorpus = re.sub('[0-9]+/[0-9]+/[0-9]+\s[0-9]+:[0-9]+\s-\s\w+:\s+', '', corpus)
preprocessedCorpus = re.sub('\[[0-9]+/[0-9]+\s[0-9]+:[0-9]+\]\s+.*', '', preprocessedCorpus)
preprocessedCorpus = re.sub('\[[0-9]+[0-9]+,\s[0-9]+/[0-9]+/[0-9]+\]\s+.*', '', preprocessedCorpus)
preprocessedCorpus = re.sub('<Arquivo de mídia oculto>', '', preprocessedCorpus)
preprocessedCorpus = re.sub('\s+\n', '', preprocessedCorpus)

chatbot = ChatBot("Vanessa")

trainer = ListTrainer(chatbot)
trainer.train(preprocessedCorpus)

while True:
    request = input('Você: ')
    response = chatbot.get_response(request)
    print('Vanessa bot: ', response)
    
