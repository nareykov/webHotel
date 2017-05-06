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
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/ReserveStyle.css">
</head>
<body>
<form method="post" action="hotel">
    <div id="BG">
        <div id="Reserve">
            <table align="center">
                <tr>
                    <td class="Text Prompt">Date from</td>
                    <td><input type="date" name="from" class="Field"></td>
                </tr>
                <tr>
                    <td class="Text Prompt">Date to</td>
                    <td><input type="date" name="to" class="Field" ></td>
                </tr>
                <tr>
                    <td class="Text Prompt">Size: </td>
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
                    <td></td>
                    <td><input align="center" type="submit" name="reserveButton" value="Reserve room" class="Text" id="ReserveButton"></td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>
