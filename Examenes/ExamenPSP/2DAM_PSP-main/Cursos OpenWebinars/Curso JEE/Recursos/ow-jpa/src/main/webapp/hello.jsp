<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
	<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
	<c:when test="${empty company.id}">
		<p> No existe la empresa </p>
	</c:when>
	<c:otherwise>
		<p> Sí existe la empresa </p>
	</c:otherwise>
</c:choose>

<h1>${mensaje}</h1>

<c:forEach items="" var="">

<a href="/hello?id=${product.id }">Editar</a>
</c:forEach>

</body>
</html>