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
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/LogInStyle.css">
    <link rel="stylesheet" href="css/HomeStyle.css">
</head>
<body>
<form method="post" action="hotel">
    <div align="right">
        <select name="lang" class="Field Text">
            <option selected value="RU">RU</option>
            <option value="EN">EN</option>
        </select>
        <input type="submit" name="langButton" value="OK" id="SignUpButton">
    </div>
    <div id="BG">
        <div id="LogIn">
            <table>
                <tr>
                    <td id="UserName" align="right">Username</td>
                    <td><input type="text" name="username" id="UserNameField"></td>
                </tr>
                <tr>
                    <td id="Password">Password</td>
                    <td><input type="password" name="password"  id="PasswordField"></td>
                </tr>
                <tr>
                    <td><input type="submit" name="regButton" value="Register" id="RegistrationButton"></td>
                    <td align="right"><input type="submit" name="entButton" value="  Enter  " id="SignUpButton"></td>
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
