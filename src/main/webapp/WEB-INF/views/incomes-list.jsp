<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Список доходов">
    <div align="center">
    <h2>Доходы</h2>
    <jsp:include page="../fragments/pagination.jsp"/>
    <table class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Заметка</th>
            <th></th>
        </tr>
        <tbody>
        <c:forEach var="outcome" items="${accountDto.incomes}">
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
                    <form action="<c:url value="/income/delete"/>" method="POST">
                        <input type="hidden" name="incomeId" value="${outcome.id}">
                        <button type="submit">Удалить доход</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="submit" onclick="history.back()" class="btn">Назад</button>
</t:master-page>

