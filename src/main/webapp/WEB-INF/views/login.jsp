<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Вход">
    <c:url var="loginUrl" value="/login"/>
    <form action="${loginUrl}" method="post" class="form-inline">
        <c:if test="${param.error != null}">
            <p>Invalid username and password.</p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p>You have been logged out successfully.</p>
        </c:if>
        <input type="text" id="username" name="email" placeholder="Enter Username" required>
        <input type="password" id="password" name="password" placeholder="Enter Password" required>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Sign in">
    </form>
    <a href="/registration">Пройти регистрацию</a>
</t:master-page>
