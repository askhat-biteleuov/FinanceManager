<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Types</title>
</head>
<body>
<div align="center">
    <h3>OutcomeTypes</h3>
    <table>
        <th>Id</th>
        <th>Name</th>
        <th>Limit</th>
        <c:forEach var="type" items="${types}">
            <tr>
                <td>${type.id}</td>
                <td>${type.name}</td>
                <td>${type.limit}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
