<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Добавить счет">
    <c:url var="accountURL" value="/add-account"/>

    <form:form method="POST" action="${accountURL}" commandName="accountDto">
        <h1>Новый счет</h1>
        <form:input path="name" placeholder="Name"/><br>
        <form:errors path="name" cssStyle="color: red"/><br>
        <form:input path="balance" placeholder="Balance"/><br>
        <form:errors path="balance" cssStyle="color: red"/><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <form:button type="submit">Добавить</form:button>
        <br>
    </form:form>
</t:master-page>
