<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>colores</title>
</head>
<body>
<p>tu color de usuario es<% out.println(request.getSession().getAttribute("color"));%></p>
<p>el color global es<% out.println(request.getSession().getAttribute("colorGlobal"));%></p>
</body>
</html>
