<%--
  Created by IntelliJ IDEA.
  User: narey
  Date: 01.05.2017
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reserve</title>
</head>
<body>
<form method="post" action="hotel">
    <table align="center">
        <tr>
            <td>Дата заезда</td>
            <td><input type="date"></td>
            <td>Дата выезда</td>
            <td><input type="date"></td>
        </tr>
        <tr>
            <td>Количество спальных мест</td>
            <td>
                <select name="size">
                    <option value="one">1</option>
                    <option value="two">2</option>
                    <option value="three">3</option>
                    <option value="four">4</option>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td><input align="center" type="submit" name="reserveButton" value="Забронировать номер"></td>
        </tr>
    </table>
</form>
</body>
</html>