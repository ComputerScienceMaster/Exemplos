<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="content-type"
              content="text/html; charset=utf-8">
        <title>List</title>
    </head>
    <body>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setBundle basename="resources.geral" var="bundle" />

        <h1><fmt:message bundle="${bundle}" key="HELLO" /></h1>
        <div style="font-weight: bold">
            <fmt:message bundle="${bundle}" key="DATE" />
            <fmt:formatDate pattern="hh:mm aa - dd / MMM / yyyy" value="${today}" />
        </div>
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
