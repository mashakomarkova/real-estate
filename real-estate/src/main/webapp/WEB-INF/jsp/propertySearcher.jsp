<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%Date today = new Date(); %>
<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");%>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

Filter
<form action="${pageContext.request.contextPath}/filterProperty" method="post">
    <select name="district">District
        <c:forEach var="district" items="${districts}">
            <option value="${district}">${district.name}</option>
        </c:forEach>
    </select>
    <input name="numberOfRooms" placeholder="Number of rooms"/>
    <input name="priceFrom" placeholder="Price from"/>
    <input name="priceTo" placeholder="Price to"/>
    <button type="submit">Find</button>
</form>
<br>
Search
<form action="${pageContext.request.contextPath}/searchProperty" method="post">
    <input type="date" name="date" min="<%=sdf.format(today)%>"/>
    <button type="submit">Find</button>
</form>
<table border="1">
    <tr>
        <th>
            district
        </th>
        <th>
            numberOfRooms
        </th>
        <th>
            price
        </th>
        <th>
            type
        </th>
        <th>
            area
        </th>
        <th>
            description
        </th>
        <th>
            availability
        </th>
    </tr>
    <c:forEach var="property" items="${properties}">

        <tr>
            <th>${property.district.name}</th>
            <th>${property.numberOfRooms}</th>
            <th>${property.price}</th>
            <th>${property.type}</th>
            <th>${property.area}</th>
            <th>${property.description}</th>
            <c:choose>
                <c:when test="${busyEstateMap[property.id] eq true}">
                    <th>busy</th>
                </c:when>
                <c:otherwise>
                    <th><a href="/propertyById/${property.id}">book</a></th>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>
</body>
</html>
