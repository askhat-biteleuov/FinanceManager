<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/src/main/webapp/WEB-INF/views/tags" %>

<t:master-page title="Регистрация">
    <div align="center" class="container">
        <div class="col-xs-12 col-sm-10 col-md-6 col-sm-offset-1 col-md-offset-3">
            <c:url var="registrationURL" value="/registration"/>
            <form:form method="POST" action="${registrationURL}" modelAttribute="registrationDto">
                <h1 class="page-header">Регистрация</h1>
                <%--<label for="email">Email:</label>--%>
                <input class="form-control" name="email" value="${registrationDto.email}" placeholder="Email" required/>
                <form:errors path="email" cssClass="red"/><br>
                <%--<label for="firstName">Имя:</label>--%>
                <input class="form-control" name="firstName" placeholder="Имя"/>
                <form:errors path="firstName" cssClass="red"/><br>
                <%--<label for="lastName">Фамилия:</label>--%>
                <input class="form-control" name="lastName" placeholder="Фамилия"/>
                <form:errors path="lastName" cssClass="red"/><br>
                <label for="currency">Выберите вашу валюту:</label>
                <select class="form-control" name="currency">
                    <option disabled>--- Выберите валюту ---</option>
                    <c:forEach items="${currencies}" var="currency">
                        <option value="${currency.characterCode}" id="currency">${currency.name}</option>
                    </c:forEach>
                </select><br>
                <%--<label for="password">Пароль:</label>--%>
                <input class="form-control" name="password" placeholder="Пароль" type="password" required/>
                <form:errors path="password" cssClass="red"/><br>
                <%--<label for="confirm">Подтвердите пароль:</label>--%>
                <input class="form-control" name="confirm" placeholder="Подтвердите пароль" type="password" required/>
                <form:errors path="confirm" cssClass="red"/><br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-green col-xs-12" type="submit">Зарегистрироваться</button>
            </form:form>
        </div>
    </div>
</t:master-page>
