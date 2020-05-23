<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <c:if test="${empty user}">
        <form action="${pageContext.request.contextPath}/logIn" method="post">
            <div class="form-group">
                <input class="form-control" type="text" name="username" placeholder="Enter username"/>
            </div>
            <div class="form-group">
                <input class="form-control" type="password" name="password" placeholder="Enter password"/>
            </div>
            <button class="btn btn-primary">Log in</button>
            <p>${incorrectCredentials}</p>
        </form>
    </c:if>
    <c:if test="${not empty user}">
        <div class="card text-center">
            <div class="card-header">
                Здравствуйте, ${user.firstName}
            </div>
            <div class="card-body">
                <h5 class="card-title">Никнейм - ${user.username}</h5>
                <h5 class="card-title">Имя - ${user.firstName}</h5>
                <h5 class="card-title">Фамилия - ${user.lastName}</h5>
                <h5 class="card-title">Номер телефона - ${user.phoneNumber}</h5>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
