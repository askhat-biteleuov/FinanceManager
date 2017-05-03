<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Список расходов">
    <div align="center">
    <h2>Расходы</h2>
    <jsp:include page="../fragments/pagination.jsp"/>
    <table id="outcomes" class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Заметка</th>
            <th>Категория</th>
            <th>Редактировать заметку</th>
            <th>Удалить расход</th>
        </tr>
        <tbody>
        <c:forEach var="outcome" items="${accountDto.outcomes}">
            <tr class="outcomeRow">
                <td>
                        ${outcome.date} ${outcome.time}
                </td>
                <td>
                        ${outcome.amount}
                </td>
                <td>
                        ${outcome.account.name}
                </td>
                <td class="outcomeNote" contenteditable="false">
                        ${outcome.note}
                </td>
                <td>
                        ${outcome.outcomeType.name}
                </td>
                <td>
                    <div class="row">
                        <div class="col-xs-1">
                            <form id="saveOutcome" action="<c:url value="/account/income/update"/>" method="POST">
                                <input type="hidden" name="accountId" value="${outcome.account.id}">
                                <input type="hidden" name="amount" value="${outcome.amount}">
                                <input type="hidden" name="date" value="${outcome.date}">
                                <input type="hidden" name="note">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" id="saveBtn">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </button>
                            </form>
                        </div>
                        <div class="col-xs-1">
                            <button id="editBtn">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                        </div>
                    </div>
                </td>
                <td>
                    <form action="<c:url value="/outcome/delete"/>" method="POST">
                        <input type="hidden" name="outcomeId" value="${outcome.id}">
                        <button type="submit">
                            <span class="glyphicon glyphicon-trash"></span>
                        </button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="submit" onclick="history.back()" class="btn">Назад</button>
</t:master-page>
