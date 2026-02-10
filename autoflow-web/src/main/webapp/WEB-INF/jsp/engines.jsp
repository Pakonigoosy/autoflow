<jsp:useBean id="params" scope="request" type="com.kargin.autoflow.dto.PaginationParams"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление двигателями</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 1400px;
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

        .btn-primary {
            background-color: #007bff;
            color: white;
            border: none;
        }

        .btn-danger {
            background-color: #dc3545;
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
    </style>
</head>
<body>
<div class="container">
    <h1>Управление двигателями</h1>
    <a href=".">На главную</a>

    <h2>Добавить двигатель</h2>
    <form method="post" action="engines">
        <input type="hidden" name="action" value="create">
        <div class="form-group">
            <label>Тип:
                <input type="text" name="type" required>
            </label>
        </div>
        <div class="form-group">
            <label>Объем (л):
                <input type="number" step="0.1" name="volume" required>
            </label>
        </div>
        <div class="form-group">
            <label>Мощность (кВт):
                <input type="number" step="0.1" name="power" required>
            </label>
        </div>
        <div class="form-group">
            <label>Серийный номер:
                <input type="text" name="serialNumber" required>
            </label>
        </div>
        <button type="submit" class="btn-primary">Создать</button>
    </form>

    <div class="search-form">
        <form method="get" action="engines">
            <label>
                Поиск:
                <input type="text" name="search" placeholder="Поиск..." value="${params.search}">
            </label>
            <label>
                Сортировка:
                <select name="sortBy">
                    <option value="">Сортировка</option>
                    <option value="type" ${params.sortBy == 'type' ? 'selected' : ''}>Тип</option>
                    <option value="volume" ${params.sortBy == 'volume' ? 'selected' : ''}>Объем</option>
                    <option value="power" ${params.sortBy == 'power' ? 'selected' : ''}>Мощность</option>
                    <option value="serialNumber" ${params.sortBy == 'serialNumber' ? 'selected' : ''}>Серийный номер</option>
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

    <table>
        <tr>
            <th>ID</th>
            <th>Тип</th>
            <th>Объем (л)</th>
            <th>Мощность (кВт)</th>
            <th>Серийный номер</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        <jsp:useBean id="result" scope="request" type="com.kargin.autoflow.util.PaginationHelper<com.kargin.autoflow.entity.Engine>"/>
        <c:forEach var="item" items="${result.items}">
            <tr>
                <td>${item.id}</td>
                <td>${item.type}</td>
                <td>${item.volume}</td>
                <td>${item.powerKw}</td>
                <td>${item.serialNumber}</td>
                <td>${item.hasCar ? 'Используется' : 'Доступен'}</td>
                <td>
                    <a href="engines?action=delete&id=${item.id}" class="btn-danger"
                       onclick="return confirm('Удалить?')">Удалить</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="pagination">
        <c:if test="${result.hasPreviousPage}">
            <a href="engines?page=${result.currentPage - 1}&search=${params.search}&sortBy=${params.sortBy}&sortOrder=${params.sortOrder}">Предыдущая</a>
        </c:if>
        <span>Страница ${result.currentPage} из ${result.totalPages} (Всего: ${result.totalItems})</span>
        <c:if test="${result.hasNextPage}">
            <a href="engines?page=${result.currentPage + 1}&search=${params.search}&sortBy=${params.sortBy}&sortOrder=${params.sortOrder}">Следующая</a>
        </c:if>
    </div>
</div>
</body>
</html>
