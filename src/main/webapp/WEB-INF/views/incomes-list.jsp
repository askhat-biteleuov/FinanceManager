<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список приходов">
    <script src="<c:url value="/resources/js/editNotes.js"/>"></script>
    <style>
        [contenteditable]:focus {
            background: #FFFFD3;
            padding: 5px;
        }
    </style>
    <div align="center">
    <h2>Приходы</h2>
    <form:form method="get" action="/account/income/page" modelAttribute="rangeDto" id="rangeForm">
        <div class="input-daterange input-group col-xs-2" id="datepicker-range">
            <form:input path="start" type="text" cssClass="input-sm form-control" name="start" id="start" readonly="true"/>
            <span class="input-group-addon">to</span>
            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end" readonly="true"/>
        </div>
        <br/>
        <button type="submit" class="btn btn-default">Submit</button>
        <input type="hidden" name="itemId" value="${rangeDto.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <script>
            $('#datepicker-range').datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                clearBtn: true
            });
        </script>
    </form:form>
    <jsp:include page="../fragments/pagination.jsp"/>
    <table id="incomes" class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Заметка</th>
            <th>Редактировать заметку</th>
            <th>Удалить доход</th>
        </tr>
        <tbody>
        <c:forEach var="income" items="${incomes}">
            <tr class="incomeRow">
                <td>
                        ${income.date} ${income.time}
                </td>
                <td>
                        ${income.amount}
                </td>
                <td>
                        ${income.account.name}
                </td>
                <td class="incomeNote" data-name="tableNote" contenteditable="false">
                        ${income.note}
                </td>
                <td>
                    <div class="row">
                        <div class="col-xs-1">
                            <form class="saveIncome" action="<c:url value="/account/income/update"/>" method="POST">
                                <input type="hidden" name="accountId" value="${income.account.id}">
                                <input type="hidden" name="amount" value="${income.amount}">
                                <input type="hidden" name="date" value="${income.date}">
                                <input type="hidden" name="note">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="saveBtn">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </button>
                            </form>
                        </div>
                        <div class="col-xs-1">
                            <button class="editBtn">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                        </div>
                    </div>
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

