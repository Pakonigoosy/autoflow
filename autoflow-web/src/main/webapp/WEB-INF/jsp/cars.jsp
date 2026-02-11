<jsp:useBean id="params" scope="request" type="com.kargin.autoflow.dto.PaginationParams"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление автомобилями</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 1600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        .form-group {
            margin: 10px 0;
        }

        label {
            display: inline-block;
            width: 150px;
        }

        input, select {
            padding: 5px;
            width: 200px;
        }

        button {
            padding: 8px 15px;
            margin: 5px;
            cursor: pointer;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
            border: none;
        }

        .btn-success {
            background-color: #28a745;
            color: white;
            border: none;
        }

        .pagination {
            margin-top: 20px;
        }

        .pagination a {
            padding: 5px 10px;
            margin: 0 2px;
            text-decoration: none;
            border: 1px solid #ddd;
        }

        .search-form {
            margin: 20px 0;
        }

        .error {
            color: red;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Управление автомобилями</h1>
    <a href=".">На главную</a>

    <c:if test="${not empty requestScope.error}">
        <div class="error">Ошибка: ${requestScope.error}</div>
    </c:if>

    <h2>Собрать автомобиль</h2>
    <form method="post" action="cars" accept-charset="UTF-8">
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
    </form>

    <div class="search-form">
        <form method="get" action="cars" accept-charset="UTF-8">
            <label>
                Поиск:
                <input type="text" name="search" placeholder="Поиск..." value="${params.search}">
            </label>
            <label>
                Сортировка:
                <select name="sortBy">
                    <option value="">Сортировка</option>
                    <option value="assemblyDate" ${params.sortBy == 'assemblyDate' ? 'selected' : ''}>Дата сборки
                    </option>
                </select>
            </label>
            <label>
                Порядок сортировки:
                <select name="sortOrder">
                    <option value="asc" ${params.sortOrder == 'asc' ? 'selected' : ''}>По возрастанию</option>
                    <option value="desc" ${params.sortOrder == 'desc' ? 'selected' : ''}>По убыванию</option>
                </select>
            </label>
            <button type="submit">Поиск</button>
        </form>
    </div>

    <h2>Автопарк</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Кузов</th>
            <th>Двигатель</th>
            <th>Трансмиссия</th>
            <th>Дата сборки</th>
            <th>Действия</th>
        </tr>
        <jsp:useBean id="result" scope="request"
                     type="com.kargin.autoflow.util.PaginationHelper<com.kargin.autoflow.entity.Car>"/>
        <c:forEach var="car" items="${result.items}">
            <tr>
                <td>${car.id}</td>
                <td>${car.body.type} - ${car.body.color} </td>
                <td>${car.engine.type} - ${car.engine.powerKw} кВт (SN: ${car.engine.serialNumber})</td>
                <td>${car.transmission.type} (SN: ${car.transmission.serialNumber})</td>
                <td><fmt:formatDate value="${car.assembledDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td>
                    <a href="cars?action=disassemble&id=${car.id}" class="btn-danger"
                       onclick="return confirm('Разобрать автомобиль?')">Разобрать</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="pagination">
        <c:if test="${result.previousPage}">
            <a href="cars?page=${result.currentPage - 1}&search=${params.search}&sortBy=${params.sortBy}&sortOrder=${params.sortOrder}">Предыдущая</a>
        </c:if>
        <span>Страница ${result.currentPage} из ${result.totalPages} (Всего: ${result.totalItems})</span>
        <c:if test="${result.nextPage}">
            <a href="cars?page=${result.currentPage + 1}&search=${params.search}&sortBy=${params.sortBy}&sortOrder=${params.sortOrder}">Следующая</a>
        </c:if>
    </div>
</div>
</body>
</html>
