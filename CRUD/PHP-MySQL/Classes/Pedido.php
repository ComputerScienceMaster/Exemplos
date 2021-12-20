<?php
class Pedido {

    public $idPedido;
    public $dataDoPedido;

    public function salvar(){
        $stringSalvar = "INSERT INTO pedido(dataDoPedido) VALUES ('" . $this->dataDoPedido . "')"; 
        Connect::getConnection()->query($stringSalvar);
        return Connect::getConnection()->insert_id;
    }

    public function atualizar(){
        $stringSalvar = "UPDATE pedido SET datadopedido =  " . $this->dataDoPedido ." "; 
        Connect::getConnection()->query($stringSalvar);

    }

    public function deletar(){
        $stringDelete = "DELETE from pedido WHERE idPedido = " . $idPedido;
    }

    public function buscarPorId($id){
        $sqlBuscar = "SELECT * FROM pedido WHERE idPedido = " . $id;
        $rs = Connect::getConnection()->query($sqlBuscar);
        $row = mysqli_fetch_row($rs);
        if($row){
            if($row->num_rows === 0 ){

                $pedido = new Item();
                $pedido->idPedido = $row[0];
                $pedido->dataDoPedido = $row[1];
                return $pedido;
            }
        }

    }

    public function buscarTodos() {

        $sqlListarPedidos = "SELECT * FROM pedido";
        $pedidos = Connect::getConnection()->query($sqlListarPedidos);


        // Popula o array de pedidos
        $arrayDePedidos = array();
        while ($row = mysqli_fetch_row($pedidos)) {
            $pedido = new Pedido();
            $pedido->idPedido = $row[0];
            $dataDoPedido = $row[1];
            array_push($arrayDePedidos, $pedido);
        }

        return $arrayDePedidos;
    }

}

?>