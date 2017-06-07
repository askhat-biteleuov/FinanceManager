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
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#byMonth">За месяц</a></li>
            <li><a data-toggle="tab" href="#byYear">За год</a></li>
        </ul>
        <div class="tab-content">
            <div id="byMonth" class="tab-pane fade in active">
                <br>
                <form id="linechartForm">
                    <div class="form-inline">
                        <div class="form-group">
                            <select id="accountSelect" name="accountName" class="form-control">
                                <option value="1" disabled>--- Выберите аккаунт ---</option>
                                <c:forEach items="${accounts}" var="acc">
                                    <option value="${acc.name}">${acc.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="month" id="monthSelect">
                                <option disabled>-- -Выберите месяц ---</option>
                                <option value="1">Январь</option>
                                <option value="2">Февраль</option>
                                <option value="3">Март</option>
                                <option value="4">Апрель</option>
                                <option value="5">Май</option>
                                <option value="6">Июнь</option>
                                <option value="7">Июль</option>
                                <option value="8">Август</option>
                                <option value="9">Сентябрь</option>
                                <option value="10">Октябрь</option>
                                <option value="11">Ноябрь</option>
                                <option value="12">Декабрь</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="year" id="yearSelect">
                                <script>
                                    var myDate = new Date();
                                    var year = myDate.getFullYear();
                                    for (var i = year; i > 2010; i--) {
                                        document.write('<option value="' + i + '">' + i + '</option>');
                                    }
                                </script>
                            </select>
                        </div>
                            <%--<button type="submit" class="btn btn-blue">Показать</button>--%>
                        <br>
                        <div class="form-group checkboxTypes">
                            <c:forEach items="${types}" var="type">
                                <div class="checkbox">
                                    <label><input class="checkboxType" type="checkbox" value="${type.name}"
                                                  checked><span
                                            class="checkboxTypesLabels">${type.name}</span></label>
                                </div>
                            </c:forEach>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                </form>
                <div class="col-md-12">
                    <div id="linechart" class="linechart"></div>
                </div>
                <script src="<c:url value="/resources/js/outcomesLinechart.js"/>"></script>
                <script src="<c:url value="/resources/js/drawOutcomesLinechart.js"/>"></script>
            </div>
            <div id="byYear" class="tab-pane fade">
                <br>
                <form id="linechartFormYear">
                    <div class="form-inline">
                        <div class="form-group">
                            <select id="accountSelectYear" name="accountName" class="form-control">
                                <option value="1" disabled>--- Выберите аккаунт ---</option>
                                <c:forEach items="${accounts}" var="acc">
                                    <option value="${acc.name}">${acc.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="year" id="yearSelectYear">
                                <script>
                                    var myDate = new Date();
                                    var year = myDate.getFullYear();
                                    for (var i = year; i > 2010; i--) {
                                        document.write('<option value="' + i + '">' + i + '</option>');
                                    }
                                </script>
                            </select>
                        </div>
                        <br>
                        <div class="form-group checkboxTypesYear">
                            <c:forEach items="${types}" var="type">
                                <div class="checkbox">
                                    <label><input class="checkboxType" type="checkbox" value="${type.name}"
                                                  checked><span
                                            class="checkboxTypesLabels">${type.name}</span></label>
                                </div>
                            </c:forEach>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                </form>
                <div class="col-md-12">
                    <div id="linechartYear" class="linechart"></div>
                </div>
                <script src="<c:url value="/resources/js/outcomesLinechartYear.js"/>"></script>
                <script src="<c:url value="/resources/js/drawOutcomesLinechartYear.js"/>"></script>
            </div>
        </div>
    </div>
    </body>
</t:master-page>
</html>
