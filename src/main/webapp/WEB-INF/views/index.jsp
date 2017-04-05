<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="user" value="${sessionScope.user}"/>

<html>
    <body>
        <a>Добрый день, ${user.email}!</a>
        <a href="/exit">Выход</a>
        <h2>Счета</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Баланс</th>
            </tr>
            <tbody>
            <c:forEach var="accounts" items="${user.accounts}">
                <tr>
                    <td><c:out value="${accounts.id}"/></td>
                    <td><c:out value="${accounts.name}"/></td>
                    <td><c:out value="${accounts.balance}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h2>Категории расходов </h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Лимит</th>
            </tr>
            <tbody>
            <c:forEach var="outcomeType" items="${user.outcomeTypes}">
                <tr>
                    <td><c:out value="${outcomeType.id}"/></td>
                    <td><c:out value="${outcomeType.name}"/></td>
                    <td><c:out value="${outcomeType.balance}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>
