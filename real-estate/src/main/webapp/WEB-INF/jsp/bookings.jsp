<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<table border="1">
    <tr>
        <th>
            property district
        </th>
        <th>
            number of rooms
        </th>
        <th>
            total price
        </th>
        <th>
           type
        </th>
        <th>
            area
        </th>
        <th>
            date of deal
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
    <c:forEach var="deal" items="${deals}">
        <tr>
            <th>${deal.property.district.name}</th>
            <th>${deal.property.numberOfRooms}</th>
            <th>${deal.totalPrice}</th>
            <th>${deal.property.type}</th>
            <th>${deal.property.area}</th>
            <th>${deal.dateOfDeal}</th>
            <th>${deal.arrivalDate}</th>
            <th>${deal.departureDate}</th>
            <th>${deal.status}</th>
        </tr>
    </c:forEach>
</table>
</body>
</html>
