<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:if test="${empty user}">
<form action="${pageContext.request.contextPath}/logIn" method="post">
    <input type="text" name="username" placeholder="Enter username"/><br>
    <input type="text" name="password" placeholder="Enter password"/><br>
    <button>Log in</button>
    <p>${incorrectCredentials}</p>
</form>
</c:if>
<c:if test="${not empty user}">
    Hello, ${user.firstName}
</c:if>
</body>
</html>
