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
My properties
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
            details
        </th>
        <th>
            delete
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
            <th><a href="/detailsProperty/${property.id}">Details</a></th>
            <th><a href="/deleteProperty/${property.id}">Delete</a></th>
        </tr>
    </c:forEach>
</table>
<form action="/addProperty" method="post">
    <select name="district">District
        <c:forEach var="district" items="${districts}">
            <option value="${district}">${district.name}</option>
        </c:forEach>
    </select>
    <input name="numberOfRooms" placeholder="Number of rooms">
    <input name="price" placeholder="price">
    <input name="type" placeholder="type">
    <input name="area" placeholder="area">
    <input name="description" placeholder="description">
    <button type="submit">Add</button>
</form>
</body>
</html>
