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
                <li>${cidade.nome}</li>
            </c:forEach>
        </ul>
    </body>
</html>
