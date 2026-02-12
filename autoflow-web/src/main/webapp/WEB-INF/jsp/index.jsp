<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>AutoFlow - Автомобильный завод</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        .menu {
            list-style: none;
            padding: 0;
        }
        .menu li {
            margin: 10px 0;
        }
        .menu a {
            display: block;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 3px;
        }
        .menu a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>AutoFlow - Система управления автомобильным заводом</h1>
        <ul class="menu">
            <li><a href="carbodies">Управление кузовами</a></li>
            <li><a href="engines">Управление двигателями</a></li>
            <li><a href="transmissions">Управление трансмиссиями</a></li>
            <li><a href="cars">Управление автомобилями</a></li>
        </ul>
    </div>
</body>
</html>
