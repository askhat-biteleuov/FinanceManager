<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Types</title>
</head>
<body>
<div align="left">
    <h3>Incomes</h3>
    <table>
        <th>Id</th>
        <th>Amount</th>
        <th>Date</th>
        <th>Note</th>
        <c:forEach var="income" items="${incomes}">
            <tr>
                <td>${income.id}</td>
                <td>${income.amount}</td>
                <td>${income.date}</td>
                <td>${income.note}</td>
            </tr>
        </c:forEach>
    </table>
</div>
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

<div align="right">
    <h3>Outcomes</h3>
    <table>
        <th>Id</th>
        <th>Amount</th>
        <th>Date</th>
        <th>Note</th>
        <th>Account</th>
        <th>Type</th>
        <c:forEach var="outcome" items="${outcomes}">
            <tr>
                <td>${outcome.id}</td>
                <td>${outcome.amount}</td>
                <td>${outcome.date}</td>
                <td>${outcome.note}</td>
                <td>${outcome.account.name}</td>
                <td>${outcome.outcomeType.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
