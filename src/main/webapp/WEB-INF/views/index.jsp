<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<c:if test="${notAuthenticated != null}">
    <h2>Добрый день!</h2>
    <p>Вы можете войти в свой аккаунт или пройти регистрацию.</p>
    <form action="/login" method="GET">
        <button type="submit">Войти в аккаунт</button>
    </form>
    <form action="/registration" method="GET">
        <button type="submit">Пройти регистрацию</button>
    </form>
</c:if>
<c:if test="${user != null}">
    <a>Добрый день, ${user.email}!</a>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit">Выход</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <div align="center">
        <h2>Счета</h2>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Баланс</th>
            </tr>
            <tbody>
            <c:forEach var="account" items="${accounts}">
                <tr>
                    <td><c:out value="${account.id}"/></td>
                    <td><c:out value="${account.name}"/></td>
                    <td><c:out value="${account.balance}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h2>Категории расходов </h2>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Лимит</th>
            </tr>
            <tbody>
            <c:forEach var="outcomeType" items="${outcomeTypes}">
                <tr>
                    <td><c:out value="${outcomeType.id}"/></td>
                    <td><c:out value="${outcomeType.name}"/></td>
                    <td><c:out value="${outcomeType.balance}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
</body>
</html>
