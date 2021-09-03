# -*- coding: utf-8 -*-
"""
Created on Wed Aug 25 19:10:00 2021

@author: Vinicius
"""

import re


corpus = open(r"conversa.txt", encoding="utf-8").read()

preprocessedCorpus = re.sub('[0-9]+/[0-9]+/[0-9]+\s[0-9]+:[0-9]+\s-\s\w+:\s+', '', corpus)

preprocessedCorpus = re.sub('\[[0-9]+/[0-9]+\s[0-9]+:[0-9]+\]\s+.*', '', preprocessedCorpus)


preprocessedCorpus = re.sub('\[[0-9]+[0-9]+,\s[0-9]+/[0-9]+/[0-9]+\]\s+.*', '', preprocessedCorpus)


preprocessedCorpus = re.sub('<Arquivo de mídia oculto>', '', preprocessedCorpus)


preprocessedCorpus = re.sub('\s+\n', '', preprocessedCorpus)

listOfSentences = preprocessedCorpus.split("\n")

from chatbot import chatbot
from chatterbot.trainers import ListTrainer


trainer = ListTrainer(chatbot)

'''
from chatterbot import ChatBot
from chatterbot.trainers import ChatterBotCorpusTrainer

chatbot = ChatBot('Ron Obvious')

# Create a new trainer for the chatbot
trainer = ChatterBotCorpusTrainer(chatbot)

# Train the chatbot based on the english corpus
trainer.train("chatterbot.corpus.english")

# Get a response to an input statement
chatbot.get_response("Hello, how are you today?")
'''