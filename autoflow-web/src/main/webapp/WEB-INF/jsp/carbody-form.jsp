<jsp:useBean id="carBody" scope="request" type="com.kargin.autoflow.entity.CarBody"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${carBody.id == null ? 'Добавить кузов' : 'Редактировать кузов'}</title>
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
            width: 140px;
        }

        input {
            padding: 5px;
            width: 250px;
        }

        button {
            padding: 8px 15px;
            margin: 5px;
            cursor: pointer;
        }

        .btn-primary {
            background-color: #007bff;
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
    <h1>${carBody.id == null ? 'Добавить кузов' : 'Редактировать кузов'}</h1>

    <c:if test="${not empty requestScope.error}">
        <div class="error">${requestScope.error}</div>
    </c:if>



    <form method="post" action="../carbodies/form" accept-charset="UTF-8">
        <c:if test="${carBody.id != null}">
            <input type="hidden" name="id" value="${carBody.id}">

        </c:if>
        <c:if test="${not empty pageParam}"><input type="hidden" name="page" value="${pageParam}"></c:if>
        <c:if test="${not empty searchParam}"><input type="hidden" name="search" value="${searchParam}"></c:if>
        <c:if test="${not empty sortByParam}"><input type="hidden" name="sortBy" value="${sortByParam}"></c:if>
        <c:if test="${not empty sortOrderParam}"><input type="hidden" name="sortOrder" value="${sortOrderParam}"></c:if>

        <div class="form-group">
            <label>Тип:
                <input type="text" name="type" required value="${carBody.type != null ? carBody.type : ''}"
                       maxlength="50">
            </label>
        </div>
        <div class="form-group">
            <label>Цвет:
                <input type="text" name="color" required value="${carBody.color != null ? carBody.color : ''}"
                       maxlength="30">
            </label>
        </div>
        <div class="form-group">
            <label>Кол-во дверей:
                <input type="number" name="doorCount" required min="2"
                       value="${carBody.doorCount != null ? carBody.doorCount : ''}">
            </label>
        </div>
        <div class="form-group">
            <label>VIN:
                <input type="text" name="vin" required value="${carBody.vin != null ? carBody.vin : ''}" maxlength="17">
            </label>
        </div>
        <button type="submit" class="btn-primary">${carBody.id == null ? 'Создать' : 'Сохранить'}</button>
        <a href="../carbodies?${queryString}" class="btn-primary">Отмена</a>
    </form>
</div>
</body>
</html>
