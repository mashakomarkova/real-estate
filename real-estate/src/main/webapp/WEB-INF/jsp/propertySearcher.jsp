<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<%
    Date today = new Date();
    Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
%>
<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");%>
<html>
<head>
    <title>#[[$Title$]]#</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    Filter
    <form action="${pageContext.request.contextPath}/searchProperty" method="post">
        <div class="form-group">
            <select class="form-control" name="district">District
                <option value="">Any district</option>
                <c:forEach var="district" items="${districts}">
                    <option value="${district}">${district.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <input class="form-control" type="number" name="numberOfRooms" placeholder="Number of rooms" min="1"
                   max="6"/>
        </div>
        <div class="form-group">
            <input class="form-control" type="number" name="priceFrom" placeholder="Price from" min="0"/>
        </div>
        <div class="form-group">
            <input class="form-control" type="number" name="priceTo" placeholder="Price to" min="0"/>
        </div>
        <div class="form-group mb-2">
            <input class="form-control" required type="date" name="arrivalDate" min="<%=sdf.format(today)%>"/>
        </div>
        <div class="form-group mb-2">
            <input class="form-control" required type="date" name="departureDate" min="<%=sdf.format(tomorrow)%>"/>
        </div>
        <button class="btn btn-primary" type="submit">Find</button>
        <p class="text-danger">${error}</p>
    </form>
    <table class="table table-striped">
        <thead class="thead-light">
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
        </thead>
        <tbody>
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
                        <th>
                            <a href="/propertyById/${property.id}?arrivalDate=${arrivalDate}&departureDate=${departureDate}">
                                book
                            </a>
                        </th>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
