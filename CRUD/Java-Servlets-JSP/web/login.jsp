

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

        <title>JSP Page</title>
    </head>
    <body style="background-color: #eee">
        <div class="centralizar-pagina">
            <div class="col-md-12">
                <form action="login" method="post">
                    <fieldset>
                        <legend>Acesso ao Sistema</legend>
                        <input class="form-control" type="text" name="usuario" value="${param.usuario}" placeholder="Usuário" autofocus>
                        </br>
                        <input class="form-control" type="password" name="senha" placeholder="Senha">                        
                        </br>
                        <input class="btn btn-success" type="submit" name="btnEnviar" value="Enviar">
                        <a class="btn btn-primary" href="cadastrarusuario">Cadastrar</a>

                    </fieldset>
                </form>
            </div>
        </div>
        <p style="color: red"> ${erro} </p>

    </body>
</html>
