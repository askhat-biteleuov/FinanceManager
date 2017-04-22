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
    <script>
        $(document).ready(function () {
            $('#adding').on('click', 'button', function () {
                $(this).next('form').slideToggle();
                var utc_date = new Date();
                utc_date.setMinutes(utc_date.getMinutes() - utc_date.getTimezoneOffset());
                $('#date').valueAsDate(utc_date);
            });
        });
    </script>
    <style>
        .trans {
            display: none;
        }
    </style>
    <button type="submit" onclick="history.back()" class="btn">Назад</button>

    <form:form method="post" action="/account/page" modelAttribute="rangeDto">
        <form:input path="start" type="date" id="date"/><br/>
        <form:errors path="start" cssStyle="color: red"/><br/>
        <form:input path="end" type="date" id="date"/><br/>
        <form:errors path="end" cssStyle="color: red"/><br/>
        <form:hidden path="accountName"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <form:button type="submit">Submit</form:button>
    </form:form>
    <div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
    <script language="JavaScript">
        function drawChart() {
            // Define the chart to be drawn.
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Type');
            data.addColumn('number', 'Amount');
            <c:forEach items="${outcomes}" var="outcome">
            data.addRow(["${outcome.key}", ${outcome.value}]);
            </c:forEach>
            // Set chart options
            var options = {
                'title': 'Statistics',
                'width': 550,
                'height': 400
            };

            // Instantiate and draw the chart.
            var chart = new google.visualization.PieChart(document.getElementById('container'));
            chart.draw(data, options);
        }
        google.charts.setOnLoadCallback(drawChart);
    </script>
    <h2>Счёт ${account.name}</h2>
    <div id="adding">
        <button type="button">Добавить доход</button>
        <form:form method="POST" action="/addincome" modelAttribute="incomeDto" cssClass="trans">
            <form:input path="note" placeholder="Note"/><br/>
            <form:input path="amount" placeholder="Amount"/><br/>
            <form:errors path="amount" cssStyle="color: red"/><br/>
            <form:input path="date" type="date" id="date"/><br/>
            <form:errors path="date" cssStyle="color: red"/><br/>
            <input type="hidden" id="accountId" name="accountId" value="${account.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form:button type="submit">Добавить</form:button>
        </form:form>
        <br/>
        <button type="button">Добавить расход</button>
        <form:form method="POST" action="/addoutcome" modelAttribute="outcomeDto" cssClass="trans">
            <form:select path="outcomeTypeId">
                <form:option value="0" disabled="true" label="--- Select ---"/>
                <form:options items="${types}" itemValue="id" itemLabel="name"/>
            </form:select><br/>
            <form:errors path="outcomeTypeId" cssStyle="color: red"/><br/>
            <form:input path="note" placeholder="Note"/><br/>
            <form:input path="amount" placeholder="Amount"/><br/>
            <form:errors path="amount" cssStyle="color: red"/><br/>
            <form:input path="date" type="date" placeholder="Date" id="date"/><br/>
            <form:errors path="date" cssStyle="color: red"/><br/>
            <input type="hidden" name="accountId" value="${account.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form:button type="submit">Добавить</form:button>
            <br>
        </form:form>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form action="<c:url value="/transfer"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Перевести на другой счет</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <br/>
        </form>
        <br/>
    </div>
    <form action="<c:url value="/income/list"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Посмотреть все доходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form action="<c:url value="/outcome/list"/>" method="GET">
        <input type="hidden" name="accountId" value="${account.id}">
        <button type="submit">Посмотреть все расходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</t:master-page>
</html>
