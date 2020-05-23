<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <div class="card text-center">
        <div class="card-header">
            Договор аренды
        </div>
        <div class="card-body">
            <h5 class="card-title"> Цена за ночь - ${property.price}</h5>
            <p class="card-text">Количество комнат - ${property.numberOfRooms}</p>
            <p class="card-text">Тип - ${property.type}</p>
            <p class="card-text">Общая площадь - ${property.area}</p>
            <p class="card-text">Описание - ${property.description}</p>
            <p class="card-text">Район - ${property.district.name}</p>
            <p class="card-text">Дата заезда - ${arrivalDate}</p>
            <p class="card-text">Дата отъезда - ${departureDate}</p>
        </div>
        <form action="${pageContext.request.contextPath}/bookProperty" method="post">
            <input type="hidden" name="arrivalDate" value="${arrivalDate}"/>
            <input type="hidden" name="departureDate" value="${departureDate}"/>
            <input type="hidden" name="propertyId" value="${property.id}">
            <button type="submit"  class="btn btn-primary">Подтвердить</button>
        </form>
    </div>
</div>
</body>
</html>
