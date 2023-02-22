<%@ page contentType="text/html;charset=UTF-8"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE>
<html lang="es">
<head>
    <title>Adivina el numero!</title>
</head>

<body>
    <h1>Te quedan ${pageContext.session.getAttribute("vidas")} VIDAS</h1>
    <form action="${pageContext.request.contextPath}/Juego" method="post">
        <input type="number" name="number" placeholder="Numero" min="1" max="10" required>
        <input type="submit" value="Adivinar!">
    </form>
    <br/>
    <h2>${pageContext.session.getAttribute("numRandom")}</h2>
    <h3>Aquí veras los números que has ido probando:</h3>
<%--    <ul>--%>
<%--        <jsp:useBean id="lista" scope="session" type="java.util.ArrayList"/>--%>
<%--        <c:forEach items="${lista}" var="numero">--%>
<%--        <li>${numero}</li>--%>
<%--        </c:forEach>--%>
<%--    </ul>--%>
</body>
</html>
