<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Рамиль
  Date: 22.04.2017
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transfer</title>
</head>
<body>
<form:form method="POST" action="/transfer" modelAttribute="transferDto">
    <h1>Добавление расхода</h1>
    <form:select path="toAccountId">
        <form:option value="1" label="--- Select ---"/>
        <form:options items="${accounts}" itemValue="id" itemLabel="name"/>
    </form:select><br/>
    <form:errors path="toAccountId" cssStyle="color: red"/><br/>
    <form:input path="amount" placeholder="Amount"/><br/>
    <form:errors path="amount" cssStyle="color: red"/><br/>
    <form:hidden path="accountId"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <form:button type="submit">Добавить</form:button>
    <br>
</form:form>
</body>
</html>
