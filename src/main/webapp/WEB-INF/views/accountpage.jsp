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
            var utc_date = new Date();
            utc_date.setMinutes(utc_date.getMinutes() - utc_date.getTimezoneOffset());
            $('#adding').on('click', 'button', function () {
                var form = $(this).next('form');
                form.slideToggle();
                form.submit(function (event) {
                    event.preventDefault(); // prevent form from POST to server

                    if (!$('#amount').val()) {
                        if ($("#amount").parent().next(".validation").length == 0) {
                            $("#amount").parent().after(
                                    "<div class='validation' style='color:red;margin-bottom: 20px;'>Please enter amount</div>"
                            );
                        }
                    } else {
                        var formData = {
                            'amount': $('input[name=amount]').val(),
                            'date': $('input[name=date]').val(),
                            'note': $('input[name=note]').val(),
                            'accountId': $('input[name=accountId]').val(),
                        };
                        $("#amount").parent().next(".validation").remove(); // remove it
                        sendAjax(formData, form);
                    }
                });
            });
            $('#incomeForm [name=date]').on('focus', function () {
                $(this).val(utc_date);
            });

            function sendAjax(data, form) {
                $.ajax({
                    type: 'POST',
                    beforeSend: function (request) {
                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        request.setRequestHeader(header, token);
                    },
                    contentType: 'application/json; charset=UTF-8',
                    url: form.attr('action'),
                    data: JSON.stringify(data)
                }).done(function (data) {
                    alert('SUCCESS');
                }).fail(function (error) {
                    alert('FAIL ' + error);
                });
            }

        });
    </script>










    <script>
        $(document).ready(function () {
            $('#rangeDto').submit(function (event) {
                event.preventDefault();

                $.ajax({
                    type: 'POST',
                    beforeSend: function (request) {
                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        request.setRequestHeader(header, token);
                    },
                    contentType: 'application/json; charset=UTF-8',
                    url: '/account/pagejson',
                    data: JSON.stringify({
                        "start": $("#rangeDto [name=start]").val(),
                        "end": $("#rangeDto [name=end]").val(),
                        "accountName": $('#rangeDto [name=accountName]').val()
                    })
                }).done(function (data) {
                    alert(data);
                    drawChart(data);
                }).fail(function (error) {
                    alert('FAIL ' + error);
                });
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
        <form:input path="start" type="date"/><br/>
        <form:errors path="start" cssStyle="color: red"/><br/>
        <form:input path="end" type="date"/><br/>
        <form:errors path="end" cssStyle="color: red"/><br/>
        <form:hidden path="accountName"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <form:button type="submit">Submit</form:button>
    </form:form>
    <div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
    <script language="JavaScript">
        function drawChart(data2) {
            // Define the chart to be drawn.
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Type');
            data.addColumn('number', 'Amount');
            <%--<c:forEach items="${outcomes}" var="outcome">--%>
            <%--data.addRow(["${outcome.key}", ${outcome.value}]);--%>
            <%--</c:forEach>--%>
            for (var key in data2) {
                data.addRow([key, data2[key]]);
            }
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
        <form:form method="POST" action="/addincome" modelAttribute="incomeDto" id="incomeForm" cssClass="trans">
            <div>
                <form:input path="note" placeholder="Note"/><br/>
            </div>
            <div>
                <form:input path="amount" placeholder="Amount"/><br/>
            </div>
            <form:errors id="incerror" path="amount" cssStyle="color: red"/><br/>
            <div>
                <form:input path="date" type="date"/><br/>
            </div>
            <form:errors id="incerror" path="date" cssStyle="color: red"/><br/>
            <input type="hidden" id="accountId" name="accountId" value="${account.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form:button type="submit">Добавить</form:button>
        </form:form>
        <br/>
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
            <form:input path="date" type="date"/><br/>
            <form:errors path="date" cssStyle="color: red"/><br/>
            <input type="hidden" name="accountId" value="${account.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form:button type="submit">Добавить</form:button>
            <br/>
        </form:form>
        <br/>
        <br/>
        <button type="submit">Перевести на другой счет</button>
        <form:form method="POST" action="/transfer" modelAttribute="transferDto" cssClass="trans">
            <h1>Добавление расхода</h1>
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
            <form:button type="submit">Добавить</form:button>
            <br>
        </form:form>
        <br/>
        <br/>
    </div>
    <form action="<c:url value="/income/page"/>" method="GET">
        <input type="hidden" name="itemId" value="${account.id}">
        <button type="submit">Посмотреть все доходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <form action="<c:url value="/outcome/page"/>" method="GET">
        <input type="hidden" name="itemId" value="${account.id}">
        <button type="submit">Посмотреть все расходы</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</t:master-page>
</html>
