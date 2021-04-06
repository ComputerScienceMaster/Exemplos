<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LearnToImplement-Hibernate-Spring</title>
    </head>
    <body>
        <h1>Lista de Pessoas cadastradas</h1>
        <form>
            <a href="form">Adicionar uma pessoa</a>
        </form>
        <table border="1">
            <tr>
                <td>Nome</td>
                <td>Login</td>
                <td>E-mail</td>
                <td>Ações</td>
            </tr>
            <c:forEach items="${pessoas}" var="pessoa">
                <tr>
                    <td>${pessoa.name}</td>
                    <td>${pessoa.login}</td>
                    <td>${pessoa.email}</td>
                    <td>
                        <form action="formeditar" method="GET">
                            <input type="hidden" name="idPerson" value="${pessoa.idPerson}"/>
                            <input type="submit" value="alterar"/>
                        </form>
                        <form action="excluir" method="POST">
                            <input type="hidden" name="idPerson" value="${pessoa.idPerson}"/>
                            <input type="submit" value="Excluir"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
