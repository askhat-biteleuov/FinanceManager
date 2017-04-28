<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Список доходов">
    <script src="<c:url value="/resources/js/editNotes.js"/>"></script>
    <style>
        [contenteditable]:hover:after {
            content: ' click to edit';
            font-style: italic;
            font-size: 12px;
            font-family: sans-serif;
            color: #ccc;
        .text-stroke(0);
        }

        [contenteditable]:hover, [contenteditable]:focus {
            background: #FFFFD3;
        }

        [contenteditable]:focus {
            padding: 5px;
        }

        [contenteditable]:focus:after {
            content: '';
        }
    </style>
    <div align="center">
    <h2>Доходы</h2>
    <jsp:include page="../fragments/pagination.jsp"/>
    <table id="incomes" class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Заметка</th>
            <th></th>
        </tr>
        <tbody>
        <c:forEach var="income" items="${accountDto.incomes}">
            <tr id="row">
                <td>
                        ${income.date} ${income.time}
                </td>
                <td>
                        ${income.amount}
                </td>
                <td>
                        ${income.account.name}
                </td>
                <td id="incomeNote" contenteditable="false">
                        ${income.note}
                </td>
                <td>
                    <button id="editBtn">
                        <span class="glyphicon glyphicon-edit"></span>
                    </button>
                </td>
                <td>
                    <form action="<c:url value="/account/income/delete"/>" method="POST">
                        <input type="hidden" name="incomeId" value="${income.id}">
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

