<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

Сделки
<table border="1">
    <tr>
        <th>
            client username
        </th>
        <th>
            client first name
        </th>
        <th>
            client last name
        </th>
        <th>
            client phone number
        </th>
        <th>
            dateOfDeal
        </th>
        <th>
            totalPrice
        </th>
        <th>
            arrivalDate
        </th>
        <th>
            departureDate
        </th>
        <th>
            status
        </th>
    </tr>
    <c:forEach var="deal" items="${property.deals}">
        <tr>
            <th>${deal.client.username}</th>
            <th>${deal.client.firstName}</th>
            <th>${deal.client.lastName}</th>
            <th>${deal.client.phoneNumber}</th>
            <th>${deal.dateOfDeal}</th>
            <th>${deal.totalPrice}</th>
            <th>${deal.arrivalDate}</th>
            <th>${deal.departureDate}</th>
            <th>${deal.status}</th>
        </tr>
    </c:forEach>
</table>
<form action="/updateProperty" method="post">
    <input type="hidden" name="id" value="${property.id}">
    <select name="district">District
        <c:forEach var="district" items="${districts}">
            <option value="${district}">${district.name}</option>
        </c:forEach>
    </select>
    <input name="numberOfRooms" value="${property.numberOfRooms}" placeholder="Number of rooms">
    <input name="price" value="${property.price}" placeholder="price">
    <input name="type" value="${property.type}" placeholder="type">
    <input name="area" value="${property.area}" placeholder="area">
    <input name="description" value="${property.description}" placeholder="description">
    <button type="submit">Update</button>
</form>
<br>
Отчет

Общее количество сделок - ${totalDeals}
Общая прибыль - ${totalProfit}

<form action="${pageContext.request.contextPath}/saveReport" method="post">
    <input type="hidden" name="id" value="${property.id}">
    <input type="hidden" name="property" value="${property}">
    <input type="hidden" name="totalDeals" value="${totalDeals}">
    <input type="hidden" name="totalProfit" value="${totalProfit}">
    <button type="submit">Save report</button>
</form>
</body>
</html>
