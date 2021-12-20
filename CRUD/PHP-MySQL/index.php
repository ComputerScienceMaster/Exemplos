<?php
// inicializa a sessão
session_start();
include_once 'Classes/Item.php';
include_once 'Classes/ItemDoPedido.php';
include_once 'Classes/Pedido.php';


if(isset($_POST['acao']) && $_POST['acao'] == "finalizarCompra"){
    $pedidoRegistrar = new Pedido();
    $pedidoRegistrar->dataDoPedido = date('y/m/d');
    $idPedido = $pedidoRegistrar->salvar();

    $itensNoCarrinho = unserialize($_SESSION['carrinho']);

    foreach ($itensNoCarrinho as $i) {
        $itemDoPedido = new ItemDoPedido();
        $itemDoPedido->idPedido = $idPedido;
        $itemDoPedido->idItem = $i->idItem;
        $itemDoPedido->quantidade = $i->quantidade;
        $itemDoPedido->salvar();
    }

    unset($_SESSION['carrinho']);

}



if(isset($_POST['acao']) && $_POST['acao'] == "limparCarrinho"){
    unset($_SESSION['carrinho']);
}

if(isset($_POST['acao']) && $_POST['acao'] == 'adicionarAoCarrinho'){

    $itemDoPedido = new ItemDoPedido();
    $itemDoPedido->idItem = intval($_POST['item']);
    $itemDoPedido->quantidade = intval($_POST['quantidade']);

    if(!isset($_SESSION['carrinho'])){
       $arrayCarrinho = array();
       array_push($arrayCarrinho, $itemDoPedido);
       $_SESSION['carrinho'] = serialize($arrayCarrinho);
   }else {
    $arrayCarrinho = unserialize($_SESSION['carrinho']);
    array_push($arrayCarrinho, $itemDoPedido);
    $_SESSION['carrinho'] = serialize($arrayCarrinho);
}
}


if(isset($_POST['acao']) && $_POST['acao'] == 'removerDoCarrinho'){

    $posicaoParaRemover = intval($_POST['position']);
    $arrayCarrinho = unserialize($_SESSION['carrinho']);
    array_splice($arrayCarrinho,$posicaoParaRemover,1);
    $_SESSION['carrinho'] = serialize($arrayCarrinho);
}

?>

<html>
<head>
    <title>Faça seu pedido</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</head>
<body class="container">
    <h1 class="jumbotron" >Faça seu pedido</h1>

    <div>
        <h3>Adicionar ao carrinho</h3>
        
        <table class="table table-stripped">
            <tr>
                <td>Imagem</td>
                <td>Nome</td>
                <td>Descrição</td>
                <td>Marca</td>
                <td>Preço</td>
                <td>Quantidade</td>
                <td>Ações</td>
            </tr>

            <?php
            $it = new Item();
            $itArray = $it->buscarTodos();
            foreach ($itArray as $i) {
                echo "<form method='POST'>";
                echo "<tr>";
                echo "<td><img src='" .$i->imagem . "' width=100 heigth=100>'</td>";
                echo "<td>" . $i->nome . "</td>";
                echo "<td>" . $i->tamanho . " " . $i->unidadeDeMedida . "</td>";
                echo "<td>" . $i->marca. "</td>";
                echo "<td>" . $i->precoDeCompra . "</td>";
                echo "<td><input class='form-control' type='number' name='quantidade'></td>"; 
                echo "<td> <input type='hidden' name='acao' value='adicionarAoCarrinho'> 
                <input type='hidden' name='item' value=" .$i->idItem . "> 
                <input type='submit' value='Adicionar ao Carrinho' class='btn btn-success'></td>";
                echo "</tr>";
                echo "</form>";
            }
            ?>
        </table>
    </div>

    <div class="jumbotron alert-info">
        <h3>Itens no carrinho</h3>
    </div>

    <?php if(isset($_SESSION['carrinho'])){ ?>

        <table class="table table-dark">
            <tr>
                <td>Imagem</td>
                <td>Nome</td>
                <td>Descrição</td>
                <td>Marca</td>
                <td>Preço</td>
                <td>Quantidade</td>
                <td>Ações</td>
            </tr>

            <?php
            $itensNoCarrinho = unserialize($_SESSION['carrinho']);
            for ($i=0; $i < count($itensNoCarrinho) ; $i++) { 
                $toSearch = new Item();
                $toSearch->idItem = $itensNoCarrinho[$i]->idItem;
                $toSearch = $toSearch->buscarPorId();
                echo "<form method='POST'>";
                echo "<tr>";
                echo "<td><img src='" .$toSearch->imagem . "' width=100 heigth=100>'</td>";
                echo "<td>" . $toSearch->nome . "</td>";
                echo "<td>" . $toSearch->tamanho . " " . $toSearch->unidadeDeMedida . "</td>";
                echo "<td>" . $toSearch->marca. "</td>";
                echo "<td>" . $toSearch->precoDeCompra . "</td>";
                echo "<td>" . $itensNoCarrinho[$i]->quantidade . "</td>";
                echo "<td> <input type='hidden' name='acao' value='removerDoCarrinho'> 
                <input type='hidden' name='position' value=" . $i . "> 
                <input type='submit' value='Remover do Carrinho' class='btn btn-danger'></td>";
                echo "</tr>";
                echo "</form>";
            }
            ?>
        </table>
    <?php }else{
        echo '<div> Carrinho vazio</div>';

    } ?>


    <div style="margin-bottom:40px">
        <form method="POST" style="display: inline">
            <input type="hidden" name="acao" value="limparCarrinho">
            <input type="submit" value="Limpar carrinho" class="btn btn-danger">
        </form>
        <form method="POST" style="display: inline">
            <input type="hidden" name="acao" value="finalizarCompra">
            <input type="submit" value="Finalizar Compra" class="btn btn-success">
        </form>
    </div>

</body>
</html>
