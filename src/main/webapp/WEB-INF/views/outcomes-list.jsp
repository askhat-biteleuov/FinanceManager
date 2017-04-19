<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<t:master-page title="Список расходов">
    <div align="center">
    <h2>Расходы</h2>
    <spring:url value="/outcome/list" var="outcomeList"/>
    <display:table name="outcomes" requestURI="${outcomeList}" pagesize="5" class="table">
        <display:column property="id" title="ID"/>
        <display:column property="date" title="Date"/>
        <display:column property="amount" title="Amount"/>
    </display:table>
    <button type="submit" onclick="history.back()" class="btn">Назад</button>
</t:master-page>
