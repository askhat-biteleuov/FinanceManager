<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    google.charts.load('current', {packages: ['corechart']});
</script>

<t:master-page title="${account.name}">
    <script src="<c:url value="/resources/js/formSlideAndDefaultDate.js"/>"></script>
    <script src="<c:url value="/resources/js/sendFormViaAjax.js"/>"></script>

    <style>
        .trans {
            display: none;
        }
    </style>

    <button type="submit" onclick="history.back()" class="btn">Назад</button>
    <h2 align="center">Счёт ${account.name}</h2>
    <div id="adding" class="container" align="center">
        <div id="incomeAdd">
            <button type="button">Добавить доход</button>
            <form method="POST" action="<c:url value="/account/income/add"/>" id="incomeForm" class="trans" role="form">
                <div class="form-group">
                    <input type="text" id="incomeNote" name="note" placeholder="Note" class="form-control"
                           maxlength="256"/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <input type="text" id="incomeAmount" name="amount" placeholder="Amount" class="form-control"
                           pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label for="incomeDate">Дата дохода:<br/></label>
                    <input id="incomeDate" name="date" type="date" required/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <input type="hidden" id="accountId" name="accountId" value="${account.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-default">Добавить</button>
            </form>
        </div>
        <br/>
        <br/>
        <div class="outcomeAdd">
            <button type="button">Добавить расход</button>
            <form:form method="POST" action="/outcome/add" modelAttribute="outcomeDto" id="outcomeForm" class="trans"
                       role="form">
                <div class="form-group">
                    <form:select path="outcomeTypeId" cssClass="form-control">
                        <form:option value="0" disabled="true" label="--- Select ---"/>
                        <form:options items="${types}" itemValue="id" itemLabel="name"/>
                    </form:select><br/>
                </div>
                <div class="form-group">
                    <input type="text" id="outcomeNote" name="note" placeholder="Note" class="form-control"
                           maxlength="256"/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <input type="text" id="outcomeAmount" name="amount" placeholder="Amount" class="form-control"
                           pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label for="outcomeDate">Дата дохода:<br/></label>
                    <input id="outcomeDate" name="date" type="date" required/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <input type="hidden" name="accountId" value="${account.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-default">Добавить</button>
                <br/>
            </form:form>
        </div>
        <br/>
        <br/>
        <div id="transfer">
            <button type="submit">Перевести на другой счет</button>
            <form:form method="POST" action="/transfer" modelAttribute="transferDto" cssClass="trans">
                <form:select path="toAccountId">
                    <form:option value="1" disabled="true" label="--- Select ---"/>
                    <c:forEach items="${accounts}" var="acc">
                        <c:if test="${acc.id != transferDto.accountId}">
                            <form:option value="${acc.id}" label="${acc.name}"/>
                        </c:if>
                    </c:forEach>
                </form:select><br/>
                <form:errors path="toAccountId" cssStyle="color: red"/><br/>
                <form:input path="amount" placeholder="Amount"/><br/>
                <form:errors path="amount" cssStyle="color: red"/><br/>
                <input type="hidden" name="accountId" value="${account.id}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-default">Перевести</button>
                <br>
            </form:form>
        </div>
        <br/>
        <br/>
    </div>
    <form action="<c:url value="/account/income/page"/>" method="GET">
        <input type="hidden" name="itemId" value="${account.id}">
        <button type="submit">Посмотреть все доходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form action="<c:url value="/outcome/page"/>" method="GET">
        <input type="hidden" name="itemId" value="${account.id}">
        <button type="submit">Посмотреть все расходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form id="rangeForm">
        <input type="date" name="start"/><br/>
        <input type="date" name="end"/><br/>
        <input type="hidden" name="accountName" value="${account.name}"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit">Submit</button>
    </form>
        <script>
            function drawDefaultChart() {
                $('#piechart').hide();
                // Define the chart to be drawn.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Type');
                data.addColumn('number', 'Amount');
                <c:forEach items="${outcomes}" var="outcome">
                data.addRow(["${outcome.key}", ${outcome.value}]);
                </c:forEach>
                // Set chart options
                var options = {
                    'title': 'Статистика за текущий месяц'
                };
                // Instantiate and draw the chart.
                var chart = new google.visualization.PieChart(document.getElementById('defaultPiechart'));
                chart.draw(data, options);
            }
            google.charts.setOnLoadCallback(drawDefaultChart);
        </script>
    <script src="<c:url value="/resources/js/outcomesPiechart.js"/>"></script>
    <script src="<c:url value="/resources/js/drawOutcomesPiechart.js"/>"></script>
    <div id="defaultPiechart" style="width: 580px; height: 430px; margin: 0 auto"></div>
    <div id="piechart" style="width: 550px; height: 400px; margin: 0 auto"></div>
</t:master-page>
</html>
