<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>PERDISTE!!!!</h1>
    <h5>El numero era ${pageContext.session.getAttribute("numRandom")}</h5>
    <form action="${pageContext.request.contextPath}/Juego" method="post">
        <input type="submit" value="Intentar de nuevo!">
    </form>
</body>
</html>
