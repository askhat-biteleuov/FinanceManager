<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Регистрация">
    <c:url var="registrationURL" value="/registration"/>
    <form:form method="POST" action="${registrationURL}" modelAttribute="registrationDto">
        <h1>Регистрация</h1>
        <form:input path="email" placeholder="Email"/><br>
        <form:errors path="email" cssStyle="color: red"/><br>
        <form:input path="firstName" placeholder="Name"/><br>
        <form:errors path="firstName" cssStyle="color: red"/><br>
        <form:input path="lastName" placeholder="Lastname"/><br>
        <form:errors path="lastName" cssStyle="color: red"/><br>
        <form:input path="password" placeholder="Password" type="password"/><br>
        <form:errors path="password" cssStyle="color: red"/><br>
        <form:input path="confirm" placeholder="Confirm" type="password"/><br>
        <form:errors path="confirm" cssStyle="color: red"/><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <form:button type="submit">Зарегистрироваться</form:button>
    </form:form>
</t:master-page>
