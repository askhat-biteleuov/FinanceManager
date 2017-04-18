<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<t:master-page title="Список доходов">
    <div align="center">
        <h2>Доходы</h2>
        <div id="pagination">
            <table border="1px" cellpadding="0" cellspacing="0">
                <thead>
                <tr>
                    <th width="10%">id</th>
                    <th width="15%">date</th>
                    <th width="10%">amount</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="income" items="${incomes}">
                <tr>
                    <td>${income.id}</td>
                    <td>${income.date}</td>
                    <td>${income.amount}</td>
                </tr>
                </c:forEach>
                <c:url value="/income/list" var="prev">
                    <c:param name="page" value="${page-1}"/>
                    <c:param name="accountId" value="${accountId}"/>
                </c:url>
                <c:if test="${page > 1}">
                    <a href=<c:out value="${prev}"/>>Prev</a>
                </c:if>

                <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
                <c:choose>
                <c:when test="${page == i.index}">
                <span>${i.index}</span>
                </c:when>
                <c:otherwise>
                <c:url value="/income/list" var="url">
                    <c:param name="page" value="${i.index}"/>
                    <c:param name="accountId" value="${accountId}"/>
                </c:url>
                <a href=<c:out value="${url}"/>>${i.index}</a>
                </c:otherwise>
                </c:choose>
                </c:forEach>
                <c:url value="/income/list" var="next">
                    <c:param name="page" value="${page + 1}"/>
                    <c:param name="accountId" value="${accountId}"/>
                </c:url>
                <c:if test="${page + 1 <= maxPages}">
                <a href=<c:out value="${next}" />>Next</a>
                </c:if>
        </div>
    </div>
</t:master-page>
</html>
