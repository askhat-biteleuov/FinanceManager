<%@tag description="This is a master page" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="title" %>

<c:set var="userName" value="${pageContext.request.userPrincipal.name}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>${title}</title>
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body>
<nav>
    <a href="/">Главная</a> |
    <c:if test="${userName == null}">
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
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/resourсes/js/jquery-3.2.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resourсes/js/bootstrap.min.js"></script>
<jsp:doBody/>
</body>
</html>