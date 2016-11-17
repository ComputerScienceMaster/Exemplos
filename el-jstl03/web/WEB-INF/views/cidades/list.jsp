<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="content-type"
              content="text/html; charset=utf-8">
        <title>List</title>
    </head>
    <body>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <ul>
            <c:forEach items="${cidades}" var="cidade">
                <c:choose>
                    <c:when test="${cidades.indexOf(cidade) % 2 == 1}">
                        <li style="background-color: yellow">${cidade.nome}</li>
                    </c:when>
                    <c:when test="${cidades.indexOf(cidade) % 2 == 0}">
                        <li style="background-color: white">${cidade.nome}</li>
                    </c:when>
                </c:choose>
            </c:forEach>
        </ul>
    </body>
</html>
