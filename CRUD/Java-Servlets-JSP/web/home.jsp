<%-- 
    Document   : home
    Created on : Nov 9, 2018, 8:34:22 AM
    Author     : isabela
--%>

<%@page import="model.Usuario"%>
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
        <title>JSP Page</title>
    </head>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" href="loja">Loja</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="listarprodutos">Produtos <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cadastrarproduto">Vender</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="carrinho">Carrinho</a>
                </li>
            </ul>

        </div>
    </nav>
    <body>
        <h1>Seja bem vindo: </h1> ${usuario.nome}

        <div>Ações</div>
        <a href="cadastrarproduto">Cadastrar novo Produto</a>
        <a href="listarprodutos">Listar Produtos</a>

    </body>
</html>
