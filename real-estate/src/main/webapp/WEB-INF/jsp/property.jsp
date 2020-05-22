<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
Договор аренды<br>
Количество комнат - ${property.numberOfRooms}<br>
Цена за ночь - ${property.price}<br>
Тип - ${property.type}<br>
Общая площадь - ${property.area}<br>
Описание - ${property.description}<br>
Район - ${property.district}<br>
Дата заезда - ${arrivalDate}<br>
Дата отъезда - ${departureDate}<br>
<br>
<form action="${pageContext.request.contextPath}/bookProperty" method="post">
    <input type="hidden" name="arrivalDate" value="${arrivalDate}" />
    <input type="hidden" name="departureDate" value="${departureDate}" />
    <input type="hidden" name="propertyId" value="${property.id}">
    <button type="submit">Подтвердить</button>
</form>
</body>
</html>
