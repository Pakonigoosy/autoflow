<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Собрать автомобиль</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
        }

        .form-group {
            margin: 10px 0;
        }

        label {
            display: inline-block;
            width: 120px;
        }

        select {
            padding: 5px;
            width: 350px;
        }

        button {
            padding: 8px 15px;
            margin: 5px;
            cursor: pointer;
        }

        .btn-success {
            background-color: #28a745;
            color: white;
            border: none;
            text-decoration: none;
        }

        .error {
            color: red;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <c:set var="pageParam" value="${param.page}"/>
    <c:set var="searchParam" value="${param.search}"/>
    <c:set var="sortByParam" value="${param.sortBy}"/>
    <c:set var="sortOrderParam" value="${param.sortOrder}"/>
    <c:set var="queryString" value=""/>
    <c:if test="${not empty pageParam}"><c:set var="queryString" value="${queryString}page=${pageParam}&"/></c:if>
    <c:if test="${not empty searchParam}"><c:set var="queryString" value="${queryString}search=${searchParam}&"/></c:if>
    <c:if test="${not empty sortByParam}"><c:set var="queryString" value="${queryString}sortBy=${sortByParam}&"/></c:if>
    <c:if test="${not empty sortOrderParam}"><c:set var="queryString" value="${queryString}sortOrder=${sortOrderParam}&"/></c:if>
    <h1>Собрать автомобиль</h1>
    <a href="../cars?${queryString}">К списку автомобилей</a>

    <c:if test="${not empty requestScope.error}">
        <div class="error">${requestScope.error}</div>
    </c:if>

    <form method="post" action="../cars/form" accept-charset="UTF-8">
        <c:if test="${not empty pageParam}"><input type="hidden" name="page" value="${pageParam}"></c:if>
        <c:if test="${not empty searchParam}"><input type="hidden" name="search" value="${searchParam}"></c:if>
        <c:if test="${not empty sortByParam}"><input type="hidden" name="sortBy" value="${sortByParam}"></c:if>
        <c:if test="${not empty sortOrderParam}"><input type="hidden" name="sortOrder" value="${sortOrderParam}"></c:if>
        <div class="form-group">
            <label>Кузов:
                <select name="bodyId" required>
                    <option value="">Выберите кузов</option>
                    <c:forEach var="body" items="${requestScope.availableBodies}">
                        <option value="${body.id}">${body.type} - ${body.color} (VIN: ${body.vin})</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label>Двигатель:
                <select name="engineId" required>
                    <option value="">Выберите двигатель</option>
                    <c:forEach var="engine" items="${requestScope.availableEngines}">
                        <option value="${engine.id}">${engine.type} - ${engine.powerKw} кВт (SN: ${engine.serialNumber})
                        </option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label>Трансмиссия:
                <select name="transmissionId" required>
                    <option value="">Выберите трансмиссию</option>
                    <c:forEach var="transmission" items="${requestScope.availableTransmissions}">
                        <option value="${transmission.id}">${transmission.type} (SN: ${transmission.serialNumber})
                        </option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <button type="submit" class="btn-success">Собрать автомобиль</button>
        <a href="../cars?${queryString}" class="btn-success">Отмена</a>
    </form>
</div>
</body>
</html>
