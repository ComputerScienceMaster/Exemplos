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
    </nav>
    <body>
        <div class="jumbotron alert-success">
            <h1>Compra efetuada com sucesso</h1>
            <h5>Qualquer dúvida entre em contato com nosso suporte</h5>
            <div>loja@projeto.com.br</div>
        </div>
    </body>
</html>
