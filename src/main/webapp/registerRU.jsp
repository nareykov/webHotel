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
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/RegistrationStyle.css">
</head>
<body>
<form method="post" action="register">
    <div id="BG">
        <div id="Registration">
            <table>
                <tr>
                    <td class="Text Prompt">Логин</td>
                    <td><input type="text" name="username" class="Field"></td>
                </tr>
                <tr>
                    <td class="Text Prompt">Пароль</td>
                    <td><input type="password" name="password" class="Field"></td>
                </tr>
                <tr>
                    <td class="Text Prompt" >Подтверждение</td>
                    <td><input type="password" name="confirm" class="Field"></td>
                </tr>
                <tr>
                    <td></td>
                    <td align="right"><input type="submit" name="regButton" value="Регистрация" id="RegistrationButton"></td>
                </tr>
                <tr >
                    <td colspan="2" >
                        <p style="color:Red" class="Text" align="center"><%=request.getAttribute("err")%></p>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>

</body>
</html>
