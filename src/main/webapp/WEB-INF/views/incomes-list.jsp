<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>

<html>
<t:master-page title="Список доходов">
    <div align="center">
        <h2>Доходы</h2>
        <spring:url value="/income/list" var="incomeList"/>
        <display:table name="incomes" requestURI="${incomeList}" pagesize="5" class="table">
            <display:column property="id" title="ID"/>
            <display:column property="date" title="Date"/>
            <display:column property="amount" title="Amount"/>
        </display:table>
</t:master-page>
</html>
