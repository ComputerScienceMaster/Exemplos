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
        <title>Cadastrar Novo usuário</title>
    </head>
    <body>

        <form action="cadastrarusuario" method="post">
            <fieldset>
                <legend>Cadastre-se</legend>
                <input type="text" name="usuario" placeholder="Digite seu usuário" autofocus>
                <br/>
                <input type="password" name="senha" placeholder="Digite sua Senha">                        
                <br/>
                <br/>
                <input type="nome" name="nome" placeholder="Digite seu nome">                        
                <br/>
                <input type="cpf" name="cpf" placeholder="Digite seu CPF">                        
                <br/>
                <input type="telefone" name="telefone" placeholder="Digite seu Telefone">                        
                <br/>
                <input type="endereco" name="endereco" placeholder="Digite seu endereço">                        

                <input type="submit" name="btnEnviar" value="Enviar">
            </fieldset>
        </form>

        <p style="color: red"> ${erro} </p>

    </body>
</html>
