<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Главная">
    <c:if test="${user == null}">
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
        <div align="center">
            <h2>Счета</h2>
            <form action="/add-account" method="GET">
                <button type="submit">Добавить счет</button>
            </form>
            <table border="1" class="table">
                <tr>
                    <th>Название</th>
                    <th>Баланс</th>
                </tr>
                <tbody>
                <c:forEach var="account" items="${user.accounts}">
                    <tr>
                        <td>
                            <a href="<c:url value="/account/page?name=${account.name}"/>">
                                <c:out value="${account.name}"/>
                            </a>
                        </td>
                        <td><fmt:formatNumber type="currency" value="${account.balance}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <h2>Категории расходов</h2>
            <table border="1" class="table">
                <tr>
                    <th>Название</th>
                    <th>Лимит</th>
                </tr>
                <tbody>
                <c:forEach var="outcomeType" items="${user.outcomeTypes}">
                    <tr>
                        <td>
                            <c:url value="/outcometype/page" var="outcomeTypeUrl">
                                <c:param name="itemId" value="${outcomeType.id}"/>
                            </c:url>
                            <a href="<c:out value="${outcomeTypeUrl}"/>"><c:out value="${outcomeType.name}"/></a>
                        </td>
                        <td><fmt:formatNumber type="currency" value="${outcomeType.limit}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/>
            <form action="/outcometype/add" method="GET">
                <button type="submit">Добавить категорию расходов</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </c:if>
</t:master-page>