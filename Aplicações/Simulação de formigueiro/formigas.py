import numpy as np
from matplotlib import pyplot as plt
import random

## manipulação de imagens
    
def criarNovaImagem(width, height, greyPoints):
    mat = []
    x1 = []
    for i in range(0, width):
        x1.append(greyPoints)
    for i  in range(0,height):
        mat.append(x1)
    return mat
    
def escrevePonto(img, x,y, intensity):
    img = np.array(img)
    img[y, x] = intensity
    return img

def mostrarImagem(mapa):
     ni = np.array(mapa)
     plt.imshow(ni, cmap='gray')
     plt.show()

##################################################     
     
class Interface:
    framerate = 30
    tela = []
    form = [0,0]
    alim = [0,0]
    encontrou = 0
    
    
    
    
    def mostrarTela(self):
        mostrarImagem(self.tela)
        
    def ajustarFramerate(self, fr):
        self.framerate = fr
        
    def criaNovaInterface(self, width, height):
        self.tela  = criarNovaImagem(width,height,15)
        
        
    def criarFormigueiro(self):
        #self.form = [random.randint(0, 10), random.randint(0, 10)]
        self.tela = escrevePonto(self.tela, self.form[0], self.form[1] , 0)
    
    def criarAlimento(self):
        self.form = [random.randint(40, 50), random.randint(40, 50)]
        self.tela = escrevePonto(self.tela, self.form[0], self.form[1] , 0)



class Formiga:
    comida = False
    x = 0
    y = 0
    ux = 0
    uy = 0
    
    def setFromiga(self, t):
        self.x = t.form[0]
        self.y = t.form[1]        
    
    def moveRandom(self, t):
        if(t.encontrou == 1):
            tempx = self.x
            tempy = self.y
            if t.alim[0] > self.x:
                self.x += 1
            elif t.alim[0] < self.x:
                self.x -= 1
            elif t.alim[1] < self.y:
                self.y -= 1
            elif t.alim[1] > self.y:
                self.y += 1
            t.tela = escrevePonto(t.tela, tempx, tempy, 15)
            t.tela = escrevePonto(t.tela, self.x, self.y, 0)
            self.ux = tempx
            self.uy = tempy
            return t
            
                
        if(self.x == t.alim[0] and self.y == t.alim[1]):
            t.encontrou = 1
            return t
        while(1==1):
            tempx = self.x
            tempy = self.y
            rand = random.randint(0, 5)
            if rand == 0 or rand == 4:
                self.y = self.y -1
            if rand == 1 or rand == 5:
                self.x = self.x - 1
            if rand == 2:
                self.y = self.y + 1
            if rand == 3:
                self.x = self.x + 1
        
            if checarValor(self.x,self.y) == 1 and self.ux != self.x and self.uy != self.y:
                t.tela = escrevePonto(t.tela, tempx, tempy, 15)
                t.tela = escrevePonto(t.tela, self.x, self.y, 0)
                self.ux = tempx
                self.uy = tempy
                return t
            else:
                self.x = tempx
                self.y = tempy
        

##################################################

    
def checarValor(x, y):
    if x >= 50 or y >= 50 or x < 0 or y < 0:
        return 0
    return 1



#### bloco prinpal (main)        

t = Interface()
t.criaNovaInterface(50, 50)
t.criarFormigueiro()
t.criarAlimento()
t.mostrarTela()

f = Formiga()
f.setFromiga(t)
f1 = Formiga()
f1.setFromiga(t)
f2 = Formiga()
f2.setFromiga(t)
for r in range(0,500):
    t = f.moveRandom(t)
    t = f1.moveRandom(t)
    t = f2.moveRandom(t)
    t.mostrarTela()