<%@tag description="This is a master page" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="title" %>

<c:set var="userName" value="${pageContext.request.userPrincipal.name}"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<nav>
    <c:if test="${userName == null}">
        <a href="/">Главная</a> |
        <a href="/login">Войти в аккаунт</a> | <a href="/registration">Пройти регистрацию</a>
    </c:if>
    <c:if test="${userName != null}">
        <a>Добрый день, ${userName}!</a>
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit">Выход</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </c:if>
</nav>
<jsp:doBody/>
</body>
</html>