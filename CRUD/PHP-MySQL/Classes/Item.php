<?php

include_once 'dbConnect.php';

class Item {

	public $idItem;
	public $nome;
	public $marca;
	public $precoDeCompra;
	public $unidadeDeMedida;
	public $tamanho;
	public $imagem;

	public function salvar(){

		$stringSalvar = "INSERT INTO item(nome,marca,precoDeCompra,unidadeDeMedida,tamanho,precoDeCompra,imagem) VALUES ('" . $this->nome . "',' " . $this->marca . " ', " .$this->precoDeCompra . " , '" .$this->unidadeDeMedida . "' ," . $this->tamanho . " , " . $this->precoDeCompra . " , ' " . $this->imagem . "' )"; 
		Connect::getConnection()->query($stringSalvar);
	}

	public function atualizar(){
		$stringAtualizar = "UPDATE item SET nome = '" .$this->nome. "', marca ='" .$this->marca. "', precoDeCompra = '" .$this->unidadeDeMedida . "', unidadeDeMedida = '" .$this->unidadeDeMedida . "', tamanho = '" . $this->tamanho . "', precoDeCompra = '" . $this->precoDeCompra . "', imagem = ' " .$this->imagem . " ' WHERE idItem = " . $this->idItem;

		Connect::getConnection()->query($stringSalvar);
	}

	public function deletar(){
		$sqlDeletar = "DELETE FROM item WHERE idItem = " . $idItem;
		Connect::getConnection()->query($sqlDeletar);
	}

	public function buscarTodos(){
		$sqlBuscar = "SELECT * FROM item";
		$rs = Connect::getConnection()->query($sqlBuscar);
		$items = array();
		while ($row = mysqli_fetch_row($rs)){
			$item = new Item();
			$item->idItem = $row[0];
			$item->nome = $row[1];
			$item->marca = $row[2];
			$item->precoDeCompra = $row[3];
			$item->unidadeDeMedida = $row[4];
			$item->tamanho = $row[5];
			$item->imagem = $row[6];
			array_push($items, $item);
		}
		return $items;

	}

	public function buscarPorId(){
		$sqlBuscar = "SELECT * FROM item WHERE idItem = " . $this->idItem;
		$rs = Connect::getConnection()->query($sqlBuscar);

		$row = mysqli_fetch_row($rs);
		if($row){
			

			$item = new Item();
			$item->idItem = $row[0];
			$item->nome = $row[1];
			$item->marca = $row[2];
			$item->precoDeCompra = $row[3];
			$item->unidadeDeMedida = $row[4];
			$item->tamanho = $row[5];
			$item->imagem = $row[6];	
			return $item;
			

		}

	}

}

?>

