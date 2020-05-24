<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>Property details</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    Сделки
    <table class="table table-striped">
        <thead class="thead-light">
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
        </thead>
        <tbody>
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
        </tbody>
    </table>
    <form action="/updateProperty" method="post">
        <input type="hidden" name="id" value="${property.id}">
        <div class="form-group">
            <select class="form-control" name="district">District
                <c:forEach var="district" items="${districts}">
                    <option value="${district}">${district.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <input required min="1" max="6" class="form-control" name="numberOfRooms" value="${property.numberOfRooms}"
                   placeholder="Number of rooms">
        </div>
        <div class="form-group">
            <input required class="form-control" type="number" min="0" name="price" value="${property.price}" placeholder="price">
        </div>
        <div class="form-group">
            <input required class="form-control" name="type" value="${property.type}" placeholder="type">
        </div>
        <div class="form-group">
            <input required class="form-control" name="area" type="number" min="1" value="${property.area}" placeholder="area">
        </div>
        <div class="form-group">
            <input required class="form-control" name="description" value="${property.description}" placeholder="description">
        </div>
        <button class="btn btn-primary" type="submit">Update</button>
    </form>
    <div class="card text-center">
        <div class="card-header">
            Отчет
        </div>
        <div class="card-body">
            <h5 class="card-title"> Общее количество завершенных сделок - ${totalCompletedDeals}</h5>
            <h5 class="card-title"> Общее количество отмененных сделок - ${totalCanceledDeals}</h5>
            <h5 class="card-title"> Общее количество активных сделок - ${totalActiveDeals}</h5>
            <h5 class="card-title"> Общая прибыль - ${totalProfit}</h5>

            <form action="${pageContext.request.contextPath}/saveReport" method="post">
                <input type="hidden" name="id" value="${property.id}">
                <input type="hidden" name="totalCompletedDeals" value="${totalCompletedDeals}">
                <input type="hidden" name="totalCanceledDeals" value="${totalCanceledDeals}">
                <input type="hidden" name="totalActiveDeals" value="${totalActiveDeals}">
                <input type="hidden" name="totalProfit" value="${totalProfit}">
                <button type="submit" class="btn btn-primary">Сохранить отчет</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
