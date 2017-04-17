<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<t:master-page title="${account.name}">
    <h2>Счёт ${account.name}</h2>
    <form action="<c:url value="/addincome"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Добавить доход</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form action="<c:url value="/addoutcome"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Добавить расход</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <br/>
    <form action="<c:url value="/income/list"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Посмотреть все доходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form action="<c:url value="/outcome/list"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Посмотреть все расходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</t:master-page>
</html>
