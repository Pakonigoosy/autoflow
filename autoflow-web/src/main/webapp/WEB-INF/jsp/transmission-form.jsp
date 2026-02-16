<jsp:useBean id="transmission" scope="request" type="com.kargin.autoflow.entity.Transmission"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${transmission.id == null ? 'Добавить трансмиссию' : 'Редактировать трансмиссию'}</title>
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
            width: 160px;
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
    <h1>${transmission.id == null ? 'Добавить трансмиссию' : 'Редактировать трансмиссию'}</h1>
    <a href="../transmissions?${queryString}">К списку трансмиссий</a>

    <c:if test="${not empty requestScope.error}">
        <div class="error">${requestScope.error}</div>
    </c:if>

    <form method="post" action="../transmissions/form" accept-charset="UTF-8">
        <c:if test="${transmission.id != null}">
            <input type="hidden" name="id" value="${transmission.id}">
        </c:if>
        <c:if test="${not empty pageParam}"><input type="hidden" name="page" value="${pageParam}"></c:if>
        <c:if test="${not empty searchParam}"><input type="hidden" name="search" value="${searchParam}"></c:if>
        <c:if test="${not empty sortByParam}"><input type="hidden" name="sortBy" value="${sortByParam}"></c:if>
        <c:if test="${not empty sortOrderParam}"><input type="hidden" name="sortOrder" value="${sortOrderParam}"></c:if>
        <div class="form-group">
            <label>Тип:
                <input type="text" name="type" required value="${transmission.type != null ? transmission.type : ''}"
                       maxlength="50">
            </label>
        </div>
        <div class="form-group">
            <label>Серийный номер:
                <input type="text" name="serialNumber" required
                       value="${transmission.serialNumber != null ? transmission.serialNumber : ''}" maxlength="50">
            </label>
        </div>
        <button type="submit" class="btn-primary">${transmission.id == null ? 'Создать' : 'Сохранить'}</button>
        <a href="../transmissions?${queryString}" class="btn-primary">Отмена</a>
    </form>
</div>
</body>
</html>
