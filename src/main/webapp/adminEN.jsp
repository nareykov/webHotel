<%--
  Created by IntelliJ IDEA.
  User: narey
  Date: 06.05.2017
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/HomeStyle.css">
</head>
<body>
<form method="post" action="hotel">
    <div align="right" id="Out">
        HELLO, GREATEST!
        <input type="submit" name="exitButton" value="Exit" id="OutButton">
    </div>
    <div id="Content">
        <div align="center" id="TextMessage">
            <table>
                <tr>
                    <th>User name</th><th>Room</th><th>Date from</th><th>Date to</th>
                </tr>
                <c:forEach items="${records}" var="cell">
                    <tr>
                        <td align="left">${cell.user}</td>
                        <td align="left">${cell.number}</td>
                        <td align="left">${cell.from}</td>
                        <td align="left">${cell.to}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <br>
        <div align="center">
            <td id="Number">Number</td>
            <input type="text" name="number"  id="NumberField">
            <td id="Size">Size</td>
            <select name="size" class="Field Text">
                <option selected value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
        </div>
        <br>
        <div align="center">
            <input type="submit" name="newRoomButton" value="Add room" id="ReserveButton">
        </div>
    </div>
</form>
</body>
</html>
