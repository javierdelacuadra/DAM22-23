<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Escribe un número del 1 al 100, tienes 10 intentos</h3>
<form action="juego" method="post">
    <label>
        <input type="text" name="numero">
    </label>
    <input type="submit" value="Enviar">
</form>

<p><% out.println(request.getSession().getAttribute("mensaje"));%></p>
</body>
</html>