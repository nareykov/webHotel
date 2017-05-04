<%--
  Created by IntelliJ IDEA.
  User: narey
  Date: 01.05.2017
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="hotel">
    <div align="right">
        Дратути, <%=session.getAttribute("user") + "    "%>
        <input type="submit" name="exitButton" value="Выйти">
    </div>
    <div align="center"><b>Вы не забронировали номер</b></div>
    <div align="center"><input type="submit" name="reserveJsp" value="Забронировать номер"></div>
</form>
</body>
</html>

