<%--
  Created by IntelliJ IDEA.
  User: narey
  Date: 01.05.2017
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form method="post" action="register">
    <table>
        <tr>
            <td>Логин</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td><input type="text" name="password"></td>
        </tr>
        <tr>
            <td>Подтверждение</td>
            <td><input type="text" name="confirm"></td>
        </tr>
        <tr>
            <td></td>
            <td align="right"><input type="submit" name="regButton" value="Регистрация"></td>
        </tr>
    </table>
    <p style="color:Red"><%=request.getAttribute("err")%></p>
</form>

</body>
</html>
