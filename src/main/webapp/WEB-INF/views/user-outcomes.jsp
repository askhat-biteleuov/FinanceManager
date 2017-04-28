<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Список расходов">
    <div align="center">
    <h2>Расходы</h2>
    <jsp:include page="../fragments/pagination.jsp"/>
    <table class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Заметка</th>
            <th>Категория</th>
            <th>Счёт</th>
        </tr>
        <tbody>
        <c:forEach var="outcome" items="${outcomes}">
            <tr>
                <td>
                        ${outcome.date} ${outcome.time}
                </td>
                <td>
                        ${outcome.amount}
                </td>
                <td>
                        ${outcome.account.name}
                </td>
                <td>
                        ${outcome.note}
                </td>
                <td>
                        ${outcome.outcomeType.name}
                </td>
                <td>
                        ${outcome.account.name}
                </td>
                <td>
                    <form action="<c:url value="/outcome/delete"/>" method="POST">
                        <input type="hidden" name="outcomeId" value="${outcome.id}">
                        <button type="submit">Удалить расход</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:master-page>
