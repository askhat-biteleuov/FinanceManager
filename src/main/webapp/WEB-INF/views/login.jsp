<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Вход">
    <div class="container">
        <c:url var="loginUrl" value="/login"/>
        <form class="form-horizontal" action="${loginUrl}" method="post" class="form-inline">
            <c:if test="${param.error != null}">
                <p>Invalid username and password.</p>
            </c:if>
            <c:if test="${param.logout != null}">
                <p>You have been logged out successfully.</p>
            </c:if>
            <div class="form-group">
                <input class="form-control" type="text" id="username" name="email" placeholder="Enter Username"
                       required>
            </div>
            <div class="form-group">
                <input class="form-control" type="password" id="password" name="password" placeholder="Enter Password"
                       required>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" name="remember-me" />Remember me</label>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <a href="/registration">Пройти регистрацию</a>
                <input class="btn btn-primary" type="submit" value="Sign in">
            </div>
        </form>
    </div>
</t:master-page>
