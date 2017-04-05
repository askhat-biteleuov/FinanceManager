<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Sign In</title>
</head>
<body>
<c:url var="loginUrl" value="/login"/>
<form action="${loginUrl}" method="post">
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
</body>
</html>
