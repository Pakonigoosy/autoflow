<jsp:useBean id="params" scope="request" type="com.kargin.autoflow.dto.PaginationParams"/>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление кузовами</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1400px; margin: 0 auto; background-color: white; padding: 20px; border-radius: 5px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #007bff; color: white; }
        button { padding: 8px 15px; margin: 5px; cursor: pointer; }
        .btn-primary { background-color: #007bff; color: white; border: none; }
        .pagination { margin-top: 20px; }
        .pagination a { padding: 5px 10px; margin: 0 2px; text-decoration: none; border: 1px solid #ddd; }
        .search-form { margin: 20px 0; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Управление кузовами</h1>
        <a href=".">На главную</a>
        <p><a href="carbodies/form" class="btn-primary">Добавить кузов</a></p>

        <div class="search-form">
            <form method="get" action="carbodies" accept-charset="UTF-8">
                <label>
                    Поиск: <input type="text" name="search" placeholder="Поиск..." value="${params.search}">
                </label>
                <label>
                    Сортировка:
                    <select name="sortBy">
                        <option value="">Сортировка</option>
                        <option value="type" ${params.sortBy == 'type' ? 'selected' : ''}>Тип</option>
                        <option value="color" ${params.sortBy == 'color' ? 'selected' : ''}>Цвет</option>
                        <option value="doorCount" ${params.sortBy == 'doorCount' ? 'selected' : ''}>Кол-во дверей</option>
                        <option value="vin" ${params.sortBy == 'vin' ? 'selected' : ''}>VIN</option>
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
                <th>Цвет</th>
                <th>Кол-во дверей</th>
                <th>VIN</th>
                <th>Статус</th>
                <th>Действия</th>
            </tr>
            <jsp:useBean id="result" scope="request"
                         type="com.kargin.autoflow.util.PaginationHelper<com.kargin.autoflow.entity.CarBody>"/>
            <c:forEach var="item" items="${result.items}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.type}</td>
                    <td>${item.color}</td>
                    <td>${item.doorCount}</td>
                    <td>${item.vin}</td>
                    <td>${item.carLinked ? 'Используется' : 'Доступен'}</td>
                    <td>
                        <a href="carbodies/form?id=${item.id}${not empty requestScope.queryString ? '&' : ''}${requestScope.queryString}" class="btn-primary">Редактировать</a>
                        <form action="carbodies" method="post" style="display:inline;">
                            <input type="hidden" name="_method" value="DELETE">
                            <input type="hidden" name="id" value="${item.id}">
                            <button type="submit" onclick="return confirm('Удалить?')">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <div class="pagination">
            <c:if test="${result.previousPage}">
                <a href="carbodies?page=${result.currentPage - 1}&search=${params.search}&sortBy=${params.sortBy}&sortOrder=${params.sortOrder}">Предыдущая</a>
            </c:if>
            <span>Страница ${result.currentPage} из ${result.totalPages} (Всего: ${result.totalItems})</span>
            <c:if test="${result.nextPage}">
                <a href="carbodies?page=${result.currentPage + 1}&search=${params.search}&sortBy=${params.sortBy}&sortOrder=${params.sortOrder}">Следующая</a>
            </c:if>
        </div>
    </div>
</body>
</html>
