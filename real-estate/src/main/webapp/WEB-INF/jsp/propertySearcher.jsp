<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<form action="${pageContext.request.contextPath}/searchProperty" method="post">
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
    </tr>
    <c:forEach var="property" items="${properties}">
        <tr>
            <th>${property.district.name}</th>
            <th>${property.numberOfRooms}</th>
            <th>${property.price}</th>
            <th>${property.type}</th>
            <th>${property.area}</th>
            <th>${property.description}</th>
        </tr>
    </c:forEach>
</table>
</body>
</html>
