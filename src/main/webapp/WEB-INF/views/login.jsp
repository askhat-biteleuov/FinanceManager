<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Вход">
    <div  class="container">
        <div align="center">
        <div class="col-sm-3">
            <h1 class="page-header">Вход</h1>
            <c:url var="loginUrl" value="/login"/>
            <form class="form-horizontal" action="${loginUrl}" method="post" class="form-inline">
                <c:if test="${param.error != null}">
                    <p class="red">Invalid username or password.</p>
                </c:if>
                <c:if test="${param.logout != null}">
                    <p>You have been logged out successfully.</p>
                </c:if>
                <div class="form-group">
                    <input class="form-control" type="text" id="username" name="email" placeholder="Enter Username"
                           required>
                </div>
                <div class="form-group">
                    <input class="form-control" type="password" id="password" name="password"
                           placeholder="Enter Password"
                           required>
                </div>
                <div class="checkbox" align="left">
                    <label><input type="checkbox" name="remember-me"/>Remember me</label>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group" align="right">
                    <input class="btn btn-default" type="button" value="Пройти регистрацию" onclick="location.href ='/registration';">
                    <input class="btn btn-green" type="submit" value="Войти">
                </div>
            </form>
        </div>
    </div>
    </div>
</t:master-page>
