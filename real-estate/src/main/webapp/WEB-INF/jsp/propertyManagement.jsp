<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>Property management</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <h2>My properties</h2>
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
                details
            </th>
            <th>
                delete
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
                <th><a href="/detailsProperty/${property.id}">Details</a></th>
                <th><a href="/deleteProperty/${property.id}">Delete</a></th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="/addProperty" method="post">
        <div class="form-group">
            <select class="form-control" name="district">District
                <c:forEach var="district" items="${districts}">
                    <option value="${district}">${district.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <input class="form-control" required type="number" name="numberOfRooms" placeholder="Number of rooms" min="1"
                   max="6">
        </div>
        <div class="form-group">
            <input class="form-control" required name="price" placeholder="price" type="number" min="0">
        </div>
        <div class="form-group">
            <input class="form-control" required name="type" placeholder="type">
        </div>
        <div class="form-group">
            <input class="form-control" required name="area" placeholder="площадь (м^2)" type="number" min="1">
        </div>
        <div class="form-group">
            <input class="form-control" required name="description" placeholder="description">
        </div>
        <button class="btn btn-primary" type="submit">Add</button>
    </form>
</div>
</body>
</html>
