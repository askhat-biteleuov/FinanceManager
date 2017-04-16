<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Добавление новой категории расходов</title>
</head>
<body>

<form:form method="POST" action="/addouttype" modelAttribute="outcometypeDto">
    <h1>Добавьте новую категорию расходов</h1>
    <form:input path="name" placeholder="Name"/><br>
    <form:errors path="name" cssStyle="color: red"/><br>
    <form:input path="limit" placeholder="Limit"/><br>
    <form:errors path="limit" cssStyle="color: red"/><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <form:button type="submit">Добавить</form:button>
    <br>
</form:form>

</body>
</html>
