class Pessoa
  def fala
    puts "Sei Falar"
  end

  def troca(roupa, lugar="banheiro")
    "trocando de #{roupa} no #{lugar}"
  end
end


class Casa
  #atributo público
  attr_accessor :numeroDaCasa
  
  #construtor
  def initialize(nome)
    @casa = nome
  end
  
  #metodo getset
  def muda_nome(novo_nome)
    @casa = novo_nome
  end
  
  #metodo comum
  def diz_nome
    "o nome da casa é #{@casa}"
  end
end