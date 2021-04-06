require 'Classes.rb'

#instanciando e invocando um método
p = Pessoa.new
puts(p.fala())

#intanciando uma classe utilizando um construtor
c = Casa.new("maria")
puts(c.diz_nome())

# não é possível acessar atributos privados
# ex: puts(c.casa)

# crie gets e sets para acessar os atributos privados
c.muda_nome('jonas')
puts(c.diz_nome())

# Assessores publicos
# Estes assessores não deveriam existir
# pois podem ser utilizados por qualquer um
#  attr_accessor :cartao_public, :valor_public

c.numeroDaCasa = 10
puts(c.numeroDaCasa())

