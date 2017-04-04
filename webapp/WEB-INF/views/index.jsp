<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="user" value="${sessionScope.user}"/>

<html>
<body>
<h2>Hello ${user.email} !</h2>
</body>
</html>
