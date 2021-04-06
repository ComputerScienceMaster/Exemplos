<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="Resources/js/jquery.js"></script>
        <script src="Resources/js/popper.js"></script>
        <script src="Resources/js/bootstrap.js"></script>

        <link rel="stylesheet" href="Resources/css/bootstrap.css"/>
        <link rel="stylesheet" href="Resources/css/bootstrap-grid.css"/>
        <link rel="stylesheet" href="Resources/css/Loja.css"/>
        <title>Lista de produtos cadastrados</title>
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
                    <a class="nav-link" href="cadastrarproduto">Novo produto</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="relatoriodevendas">Relatório de Vendas</a>
                </li>
            </ul>

        </div>
    </nav>

    <div class="centering"  style="margin-top:4%">
        <div class="col-md-12" >
            <body>
                <h1>Vendas realizadas no site</h1>

                <table class="table table-striped">
                    <tr>
                        <td>Nome do Comprador</td>
                        <td>data</td>
                        <td>Cartão de Crédito</td>
                        <td>Cod Segurança</td>
                        <td>Valor da venda</td>

                    </tr>

                    <c:forEach var="venda" items="${listaDeVendas}">
                        <tr>
                            <td>${venda.nomeComprador}</td>
                            <td>${venda.data}</td>
                            <td>${venda.cartaoComprador}</td>
                            <td>${venda.codSegurancaComprador}</td>
                            <td>${venda.valor}</td>
                            
                        </tr>
                    </c:forEach>

                </table>

                <p style="color: red"> ${erro} </p>

            </body>
        </div>
    </div>
</html>
