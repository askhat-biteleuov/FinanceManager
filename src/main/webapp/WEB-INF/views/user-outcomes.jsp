<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список расходов">
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <h2 align="center" class="page-header">Расходы</h2>
        <div class="article">
            <c:choose>
                <c:when test="${not empty outcomes}">
                    <form:form method="get" action="/outcome/all" modelAttribute="rangeDto" id="rangeForm"
                               cssClass="form-inline">
                        <div class="input-daterange input-group" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="input-sm form-control" name="start"
                                        id="start" readonly="true"/>
                            <span class="input-group-addon">to</span>
                            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end"
                                        readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue">Показать</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <script>
                            $('#datepicker-range').datepicker({
                                format: "yyyy-mm-dd",
                                todayBtn: "linked",
                                clearBtn: true
                            });
                        </script>
                    </form:form>
                    <br>
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
                    <div align="center">
                        <jsp:include page="../fragments/pagination.jsp"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <form:form method="get" action="/outcome/all" modelAttribute="rangeDto" id="rangeForm"
                                   cssClass="form-inline">
                            <div class="input-daterange input-group" id="datepicker-range">
                                <form:input path="start" type="text" cssClass="input-sm form-control" name="start"
                                            id="start" readonly="true"/>
                                <span class="input-group-addon">to</span>
                                <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end"
                                            readonly="true"/>
                            </div>
                            <button type="submit" class="btn btn-blue">Показать</button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <script>
                                $('#datepicker-range').datepicker({
                                    format: "yyyy-mm-dd",
                                    todayBtn: "linked",
                                    clearBtn: true
                                });
                            </script>
                        </form:form>
                        <br>
                        <h3>Пока нет расходов по данному счету.</h3>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</t:master-page>
