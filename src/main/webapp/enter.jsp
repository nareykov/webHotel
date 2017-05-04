<%--
  Created by IntelliJ IDEA.
  User: narey
  Date: 01.05.2017
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter</title>
</head>
<body>
<form method="post" action="hotel">
    <table>
        <tr>
            <td>Имя пользователя</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td><input type="text" name="password"></td>
        </tr>
        <tr>
            <td><input type="submit" name="regButton" value="Регистрация"></td>
            <td align="right"><input type="submit" name="entButton" value="  Вход  "></td>
        </tr>
    </table>
    <p style="color:Red"><%=request.getAttribute("err")%></p>
</form>
</body>
</html>
