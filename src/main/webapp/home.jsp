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
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Title</title>
</head>
<body>
<jsp:useBean id="records" class="java.util.ArrayList" scope="request"/>

<form method="post" action="hotel">
    <div align="right">
        Дратути, <%=session.getAttribute("user") + "    "%>
        <input type="submit" name="exitButton" value="Выйти">
    </div>
    <table>
        <c:forEach items="${records}" var="cell">
            <tr><td align="left">Номер: ${cell.number}</td>
                <td align="left">Дата заезда: ${cell.dateFrom}</td>
                <td align="left">Дата отъезда: ${cell.dateTo}</td>
            </tr>
        </c:forEach>
    </table>
    <div align="center"><input type="submit" name="reserveJsp" value="Забронировать номер"></div>
</form>
</body>
</html>

