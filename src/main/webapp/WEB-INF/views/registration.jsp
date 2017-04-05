<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form method="POST" action="<c:url value="/registration"/>">
    <h1>Registration</h1>
    <input name="email" type="text" placeholder="Email*" autofocus="true" value="${registrationDTO.email}"/><br>
    <input name="firstName" type="text" placeholder="First name" value="${registrationDTO.firstName}"/><br>
    <input name="lastName" type="text" placeholder="Last name" value="${registrationDTO.lastName}"/><br>
    <input name="password" type="password" placeholder="Password"/><br>
    <input name="confirm" type="password" placeholder="Confirm Password"/><br>
    <span style="color: red">${error}</span><br>
    <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
    <button type="submit">Sign Up</button>
    <br>
</form>
</body>
</html>
