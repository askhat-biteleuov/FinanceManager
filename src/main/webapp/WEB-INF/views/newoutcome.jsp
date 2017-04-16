<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Добавление расхода</title>
</head>
<body>
<form:form method="POST" action="/addoutcome" modelAttribute="outcomeDto">
    <h1>Добавление расхода</h1>
    <form:select path="outcomeTypeId">
        <form:option value="1" label="--- Select ---"/>
        <form:options items="${types}" itemValue="id" itemLabel="name"/>
    </form:select><br/>
    <form:errors path="outcomeTypeId" cssStyle="color: red"/><br/>
    <form:input path="note" placeholder="Note"/><br/>
    <form:input path="amount" placeholder="Amount"/><br/>
    <form:errors path="amount" cssStyle="color: red"/><br/>
    <form:input path="date" type="date" placeholder="Date"/><br/>
    <form:errors path="date" cssStyle="color: red"/><br/>
    <form:hidden path="accountId"/>
    <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
    <form:button type="submit">Добавить</form:button>
    <br>
</form:form>
</body>
</html>
