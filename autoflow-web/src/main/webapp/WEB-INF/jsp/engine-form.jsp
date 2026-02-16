<jsp:useBean id="engine" scope="request" type="com.kargin.autoflow.entity.Engine"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${engine.id == null ? 'Добавить двигатель' : 'Редактировать двигатель'}</title>
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
    <h1>${engine.id == null ? 'Добавить двигатель' : 'Редактировать двигатель'}</h1>
    <a href="../engines?${queryString}">К списку двигателей</a>

    <c:if test="${not empty requestScope.error}">
        <div class="error">${requestScope.error}</div>
    </c:if>

    <form method="post" action="../engines/form" accept-charset="UTF-8">
        <c:if test="${engine.id != null}">
            <input type="hidden" name="id" value="${engine.id}">
        </c:if>
        <c:if test="${not empty pageParam}"><input type="hidden" name="page" value="${pageParam}"></c:if>
        <c:if test="${not empty searchParam}"><input type="hidden" name="search" value="${searchParam}"></c:if>
        <c:if test="${not empty sortByParam}"><input type="hidden" name="sortBy" value="${sortByParam}"></c:if>
        <c:if test="${not empty sortOrderParam}"><input type="hidden" name="sortOrder" value="${sortOrderParam}"></c:if>
        <div class="form-group">
            <label>Тип:
                <input type="text" name="type" required value="${engine.type != null ? engine.type : ''}"
                       maxlength="50">
            </label>
        </div>
        <div class="form-group">
            <label>Объем (л):
                <input type="number" step="0.1" name="volume" required min="0.1"
                       value="${engine.volume != null ? engine.volume : ''}">
            </label>
        </div>
        <div class="form-group">
            <label>Мощность (кВт):
                <input type="number" step="0.1" name="power" required min="0.1"
                       value="${engine.powerKw != null ? engine.powerKw : ''}">
            </label>
        </div>
        <div class="form-group">
            <label>Серийный номер:
                <input type="text" name="serialNumber" required
                       value="${engine.serialNumber != null ? engine.serialNumber : ''}" maxlength="50">
            </label>
        </div>
        <button type="submit" class="btn-primary">${engine.id == null ? 'Создать' : 'Сохранить'}</button>
        <a href="../engines?${queryString}" class="btn-primary">Отмена</a>
    </form>
</div>
</body>
</html>
