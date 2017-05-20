<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<t:master-page title="${account.name}">
    <head>
        <title>Статистика</title>
    </head>
    <body>
    <div class="container">
        <form id="linechartForm">
            <div class="form-group">
                <select id="accountSelect" name="accountName" class="form-control">
                    <option value="1" disabled>--- Выберите аккаунт ---</option>
                    <c:forEach items="${accounts}" var="acc">
                        <option value="${acc.name}">${acc.name}</option>
                    </c:forEach>
                </select>
                <select class="form-control" name="month" id="monthSelect">
                    <option disabled>-- -Выберите месяц ---</option>
                    <option value="01">Январь</option>
                    <option value="02">Февраль</option>
                    <option value="03">Март</option>
                    <option value="04">Апрель</option>
                    <option value="05">Май</option>
                    <option value="06">Июнь</option>
                    <option value="07">Июль</option>
                    <option value="08">Август</option>
                    <option value="09">Сентябрь</option>
                    <option value="10">Октябрь</option>
                    <option value="11">Ноябрь</option>
                    <option value="12">Декабрь</option>
                </select>
                <select class="form-control" name="year" id="yearSelect">
                    <script>
                        var myDate = new Date();
                        var year = myDate.getFullYear();
                        for (var i = 2010; i < year + 1; i++) {
                            document.write('<option value="' + i + '">' + i + '</option>');
                        }
                    </script>
                </select>
                <button type="submit" class="btn btn-default">Submit</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </form>
        <div id="linechart"></div>
        <script src="<c:url value="/resources/js/outcomesLinechart.js"/>"></script>
        <script src="<c:url value="/resources/js/drawOutcomesLinechart.js"/>"></script>
    </div>
    </body>
</t:master-page>
</html>
