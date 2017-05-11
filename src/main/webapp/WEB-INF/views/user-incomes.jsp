<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список приходов">
    <div align="center">
    <h2>Приходы</h2>
    <form:form method="get" action="/account/income/all" modelAttribute="rangeDto" id="rangeForm">
        <div class="input-daterange input-group col-xs-2" id="datepicker-range">
            <form:input path="start" type="text" cssClass="input-sm form-control" name="start" id="start" readonly="true"/>
            <span class="input-group-addon">to</span>
            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end" readonly="true"/>
        </div>
        <br/>
        <button type="submit" class="btn btn-default" id="submitRange">Submit</button>
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
    <table class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Заметка</th>
            <th></th>
        </tr>
        <tbody>
        <c:forEach var="income" items="${incomes}">
            <tr>
                <td>
                        ${income.date} ${income.time}
                </td>
                <td>
                        ${income.amount}
                </td>
                <td>
                        ${income.account.name}
                </td>
                <td>
                        ${income.note}
                </td>
                <td>
                    <form action="<c:url value="/account/income/delete"/>" method="POST">
                        <input type="hidden" name="incomeId" value="${income.id}">
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

