<%--
  Created by IntelliJ IDEA.
  User: Javi
  Date: 09/12/2022
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recuperación de contraseña</title>
</head>
<body>
<%
    String code = request.getParameter("code");
%>
<form action="${pageContext.request.contextPath}/passwordRecovery" method="post">
    <input type="hidden" name="code" value="<%= code %>">
    <label for="password">Escribe tu nueva contraseña:</label>
    <input type="password" id="password" name="password"/>
    <input type="submit" value="Enviar"/>
</form>
</body>
</html>
