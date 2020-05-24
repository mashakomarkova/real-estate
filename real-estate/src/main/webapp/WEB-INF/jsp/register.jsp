<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>Register</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-group">
            <input required class="form-control" type="text" name="username" placeholder="Enter username"/>
        </div>
        <div class="form-group">
            <input required class="form-control" type="password" name="password" placeholder="Enter password"/>
        </div>
        <div class="form-group">
            <input required class="form-control" type="text" name="firstName" placeholder="Enter first name"/>
        </div>
        <div class="form-group">
            <input required class="form-control" type="text" name="lastName" placeholder="Enter last name"/>
        </div>
        <div class="form-group">
            <input required class="form-control" type="text" name="phoneNumber" placeholder="Enter phone number"/>
        </div>
        <select class="form-control" name="role"> Role
            <c:forEach var="role" items="${roles}">
                <option value="${role}">${role.name}</option>
            </c:forEach>
        </select><br>
        <button class="btn btn-primary">Register</button>
        <p>${successRegister}</p>
    </form>
</div>
</body>
</html>
