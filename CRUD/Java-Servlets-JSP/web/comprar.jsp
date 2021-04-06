<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="Resources/js/jquery.js"></script>
        <script src="Resources/js/popper.js"></script>
        <script src="Resources/js/bootstrap.js"></script>

        <link rel="stylesheet" href="Resources/css/bootstrap.css">
        <link rel="stylesheet" href="Resources/css/bootstrap-grid.css">
        <link rel="stylesheet" href="Resources/css/Loja.css">
        <title>Cadastrar novo produto</title>
    </head>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" href="loja">Loja</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item active">
                    <a class="nav-link" href="login">Entrar<span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
    <body style="background-color: #eee" >
        <div class="centralizar-pagina">
            <div class="row ">
                <div style="margin-top: 5%" class="col-md-12">
                    <div class="col-md-12">
                        <h4 > Comprar produto</h4>
                        
                        <div class="box-produtos alert-info ">
                            <h6>Nome do produto: ${produto.nome}</h6>
                            <div>Valor: ${produto.valor}</div>
                            <div>Descrição: ${produto.descricao}</div>
                        </div>
                        <form action="comprar" method="post">
                            <input type="hidden" name="idProduto" value="${produto.codigo}" />
                            <input type="hidden" name="valor" value="${produto.valor}"/>
                            <div class="form-group">
                                <input class="form-control" type="text" name="nomeComprador" placeholder="Digite o seu nome" autofocus/>
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="text" name="cartaoComprador" placeholder="Digite o seu cartão de crédito"/>                        
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="text" maxlength="3" name="codSegurancaComprador" placeholder="Digite o código de segurança"/>                        
                            </div>
                            <input  type="submit" class="btn btn-success" name="btnEnviar" value="Finalizar compra">
                        </form>
                        <p style="color: red"> ${erro} </p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
