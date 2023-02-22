<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>GANASTE!!!!!!!</h1>
    <h5>El numero era ${pageContext.session.getAttribute("numRandom")} y te han sobrado ${pageContext.session.getAttribute("vidasLeft")} vidas!</h5>
    <form action="${pageContext.request.contextPath}/Juego" method="post">
        <input type="submit" value="Seguir con la racha!">
    </form>
</body>
</html>
