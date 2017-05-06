<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/HomeStyle.css">
</head>
<body>
<form method="post" action="hotel">
    <div align="right" id="Out">
        Дратути, <%=session.getAttribute("user") + "    "%>
        <input type="submit" name="exitButton" value="Выйти" id="OutButton">
    </div>
    <div id="Content">
        <div align="center" id="TextMessage">
            <table>
                <c:forEach items="${records}" var="cell">
                    <tr>
                        <td align="left">Номер: ${cell.number}</td>
                        <td align="left">Дата заезда: ${cell.dateFrom}</td>
                        <td align="left">Дата отъезда: ${cell.dateTo}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div align="center">
            <input type="submit" name="reserveJsp" value="Забронировать номер" id="ReserveButton">
        </div>
    </div>
</form>
</body>
</html>
