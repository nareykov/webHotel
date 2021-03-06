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
        Дратути, ВСЕМОГУЩИЙ
        <input type="submit" name="exitButton" value="Выйти" id="OutButton">
    </div>
    <div id="Content">
        <table>
            <tr>
                <td valign="top">
                    <table>
                        <tr>
                            <th>Имя пользователя</th><th>Номер</th><th>Дата заезда</th><th>Дата выезда</th>
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
                </td>
                <td valign="top">
                    <table>
                        <tr>
                            <td id="Number">Номер</td>
                            <td id="Size">Размер</td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" name="number"  id="NumberField">
                            </td>
                            <td>
                                <select name="size" class="Field Text">
                                    <option selected value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" name="newRoomButton" value="Добавить номер" id="ReserveButton">
                            </td>
                        </tr>
                        <tr>
                            <th>Номер</th><th>Статус</th>
                        </tr>
                        <c:forEach items="${rooms}" var="ttt">
                            <tr>
                                <td align="left">${ttt.number}</td>
                                <td align="left">${ttt.size}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
        <br>

    </div>
</form>
</body>
</html>
