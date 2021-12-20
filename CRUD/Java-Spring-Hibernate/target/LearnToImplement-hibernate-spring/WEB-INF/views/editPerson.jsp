<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LearnToImplement-Hibernate-Spring</title>
    </head>
    <body>
        <h1>Edite as informações do produto</h1>
        <form action="editar" method="POST">
            <input type="hidden" name="idPerson" value="${toedit.idPerson}"/>
            <input name="name" value="${toedit.name}" placeholder="Digite seu nome" />
            <input name="login" value="${toedit.login}" placeholder="Digite seu login" />
            <input name="senha" value="${toedit.senha}" placeholder="Digite seu senha" />
            <input name="email" value="${toedit.email}" placeholder="Digite seu email" />
            <input type="submit" value="Alterar informações"/>
        </form>
    </body>
</html>
