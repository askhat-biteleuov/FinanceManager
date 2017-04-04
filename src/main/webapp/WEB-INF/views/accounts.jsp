<%--
  Created by IntelliJ IDEA.
  User: Рамиль
  Date: 03.04.2017
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Users</title>
</head>
<body>
<div align="center">
    <h1>User accounts List</h1>
    <table border="1">
        <th>id</th>
        <th>name</th>
        <th>balance</th>

        <c:forEach var="account" items="${accounts}">
            <tr>
                <td>${account.id}</td>
                <td>${account.name}</td>
                <td>${account.balance}</td>

            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
