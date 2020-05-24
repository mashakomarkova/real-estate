<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>Bookings</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <table class="table table-striped">
        <thead class="thead-light">
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
            <th>
                cancel
            </th>
        </tr>
        </thead>
        <tbody>
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
                <th>
                    <c:if test="${deal.status eq 'accepted'}">
                        <a href="/cancelBooking/${deal.id}">
                            Cancel
                        </a>
                    </c:if>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    ${incorrect}
</div>
</body>
</html>
