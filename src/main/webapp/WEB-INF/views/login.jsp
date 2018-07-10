<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Вход">
    <div  class="container">
        <div align="center">
            <div class="col-xs-12 col-sm-10 col-md-6 col-sm-offset-1 col-md-offset-3">
            <h1 class="page-header">Вход</h1>
            <c:url var="loginUrl" value="/login"/>
            <form class="form-horizontal" action="${loginUrl}" method="post" class="form-inline">
                <c:if test="${param.error != null}">
                    <p class="red">Invalid username or password.</p>
                </c:if>
                <c:if test="${param.logout != null}">
                    <p>You have been logged out successfully.</p>
                </c:if>
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input class="form-control" type="text" id="username" name="email" placeholder="Email"
                           required>
                </div>
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                    <input class="form-control" type="password" id="password" name="password"
                           placeholder="Пароль"
                           required>
                </div>
                <div class="checkbox" align="left">
                    <label><input type="checkbox" name="remember-me"/>Запомнить меня</label>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group row" align="right">
                    <input class="btn btn-default col-xs-offset-1 col-xs-10 col-sm-offset-0 col-sm-7" type="button"
                           value="Пройти регистрацию" onclick="location.href ='/registration';">
                    <input class="btn btn-green  col-xs-offset-1 col-xs-10 col-sm-offset-0 col-sm-5" type="submit"
                           value="Войти">
                </div>
            </form>
        </div>
    </div>
    </div>
</t:master-page>
