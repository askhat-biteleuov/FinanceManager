<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список расходов">
    <div align="center">
    <h2>Расходы</h2>
    <script src="<c:url value="/resources/js/defaultDateForRange.js"/>"></script>
    <form:form method="get" action="/outcome/all" modelAttribute="rangeDto" id="rangeForm">
        <div class="input-daterange input-group col-xs-2" id="datepicker-range">
            <form:input path="start" type="text" cssClass="input-sm form-control" name="start" id="start" readonly="true"/>
            <span class="input-group-addon">to</span>
            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end" readonly="true"/>
        </div>
        <br/>
        <button type="submit" class="btn btn-default">Submit</button>
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
            <th>Категория</th>
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
