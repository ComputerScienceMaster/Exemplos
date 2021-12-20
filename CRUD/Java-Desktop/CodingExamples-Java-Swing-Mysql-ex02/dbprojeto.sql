create database dbprojeto;
use dbprojeto;

	
create table produtos(
	id int primary key auto_increment not null,
    descricao varchar (75),
    qtd int,
    preco double
)

