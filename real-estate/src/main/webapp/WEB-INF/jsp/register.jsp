<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<form action="${pageContext.request.contextPath}/register" method="post">
    <input type="text" name="username" placeholder="Enter username"/><br>
    <input type="text" name="password" placeholder="Enter password"/><br>
    <input type="text" name="firstName" placeholder="Enter first name"/><br>
    <input type="text" name="lastName" placeholder="Enter last name"/><br>
    <input type="text" name="phoneNumber" placeholder="Enter phone number"/><br>
    <select name="role"> Role
        <c:forEach var="role" items="${roles}">
            <option value="${role}">${role.name}</option>
        </c:forEach>
    </select><br>
    <button>Register</button><br>
    <p>${successRegister}</p>
</form>
</body>
</html>
