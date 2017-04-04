<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>

<div style="text-align: center;">

    <h3>Login page</h3>

    <br/>

    <form method="POST" action="<c:url value="/login"/>">
        <h1>Log in</h1>
        <input name="email" type="text" placeholder="Email" autofocus="true" value="${loginDTO.email}"/><br>
        <input name="password" type="password" placeholder="Password"/><br>
        <span style="color: red">${error}</span><br>
        <button type="submit">Log In</button>
        <br>
        <span><a href="/registration">Create an account</a></span>
    </form>

</div>

</body>
</html>
