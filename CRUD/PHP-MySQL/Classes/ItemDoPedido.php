<?php
class ItemDoPedido {

    public $idPedido;
    public $idItem;
    public $quantidade;

    public function salvar(){
        $stringSalvar = "INSERT INTO PedidoPossuiItem VALUES (" . $this->idPedido . "," . $this->idItem . "," . $this->quantidade . ")"; 
        Connect::getConnection()->query($stringSalvar);
    }

    public function atualizar(){
        $stringAtualizar = "UPDATE PedidoPossuiItem SET idItem =  " . $this->idItem . "quantidade =  " . $this->quantidade . "WHERE idPedido = " . $this->idPedido ; 
        Connect::getConnection()->query($stringAtualizar);

    }

    public function deletar(){
        $stringDelete = "DELETE from PedidoPossuiItem WHERE idPedido = " . $this->idPedido;
    }

    public function buscarPorId($id){
        $sqlBuscar = "SELECT * FROM PedidoPossuiItem WHERE idPedido = " . $this->idPedido;
        $rs = Connect::getConnection()->query($sqlBuscar);
        $row = mysqli_fetch_row($rs);
        $items = array();
        while ($row = mysqli_fetch_row($pedidos)) {
            if($row->num_rows === 0 ){
                $i = new ItemDoPedido();
                $i->idPedido = $row[0];
                $i->idItem = $row[1];
                $i->quantidade = $row[3];
                array_push($items, $i);
            }
        }
        return $items;

    }

    public function buscarTodos() {

        $sqlListarPedidos = "SELECT * FROM PedidoPossuiItem";
        $pedidos = Connect::getConnection()->query($sqlListarPedidos);


        // Popula o array de pedidos
        $arrayDeItems = array();
        while ($row = mysqli_fetch_row($pedidos)) {
            $i = new ItemDoPedido();
            $i->idPedido = $row[0];
            $i->idItem = $row[1];
            $i->quantidade = $row[2];
            array_push($arrayDeItems, $i);
        }

        return $arrayDeItems;
    }

}

?>