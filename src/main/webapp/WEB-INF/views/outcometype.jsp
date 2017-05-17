<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="outcomeTypeDto" type="com.fm.internal.dtos.OutcomeTypeDto"--%>

<t:master-page title="${outcomeTypeDto.name}">
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <h1 align="center" class="page-header">${outcomeTypeDto.name}</h1>
        <div class="article">
            <c:choose>
                <c:when test="${not empty outcomeTypeDto.outcomes}">
                    <form:form method="get" action="/outcometype/page" modelAttribute="rangeDto" id="rangeForm" cssClass="form-inline">
                        <div class="input-daterange input-group" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="input-sm form-control" name="start" id="start" readonly="true"/>
                            <span class="input-group-addon">по</span>
                            <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end" readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue">Принять</button>
                        <input type="hidden" name="itemId" value="${outcomeTypeDto.id}">
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
                            <th>Сумма в валюте пользователя</th>
                            <th>Счет</th>
                            <th>Заметка</th>
                            <th></th>
                        </tr>
                        <tbody>
                        <c:forEach var="outcome" items="${outcomeTypeDto.outcomes}">
                            <tr>
                                <td>
                                        ${outcome.date} ${outcome.time}
                                </td>
                                <td>
                                    <fmt:formatNumber type="currency"
                                                      currencySymbol="${outcome.account.currency.characterCode}"
                                                      value="${outcome.amount}"/>
                                </td>
                                <td>
                                    <fmt:formatNumber type="currency"
                                                      currencySymbol="${outcome.account.user.info.currency.characterCode}"
                                                      value="${outcome.defaultAmount}"/>
                                </td>
                                <td>
                                        ${outcome.account.name}
                                </td>
                                <td>
                                        ${outcome.note}
                                </td>
                                <td>
                                    <form action="<c:url value="/outcome/delete"/>" method="POST"
                                          onsubmit="return confirm('Операция будет удалена! Вы хотите продолжить?')">
                                        <input type="hidden" name="outcomeId" value="${outcome.id}">
                                        <button class="btn btn-danger" type="submit"><span class="glyphicon glyphicon-trash"></span></button>
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
                        <form:form method="get" action="/outcometype/page" modelAttribute="rangeDto" id="rangeForm" cssClass="form-inline">
                            <div class="input-daterange input-group" id="datepicker-range">
                                <form:input path="start" type="text" cssClass="input-sm form-control" name="start" id="start" readonly="true"/>
                                <span class="input-group-addon">по</span>
                                <form:input path="end" type="text" cssClass="input-sm form-control" name="end" id="end" readonly="true"/>
                            </div>
                            <button type="submit" class="btn btn-blue">Принять</button>
                            <input type="hidden" name="itemId" value="${outcomeTypeDto.id}">
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
                        <h3>Пока нет расходов по данной категории.</h3>
                    </div>
                </c:otherwise>
            </c:choose>
            <br>
            <div>
                <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#outcometype-delete">
                    Удалить категорию
                </button>
                <jsp:include page="outcometype-delete.jsp"/>
            </div>
        </div>
    </div>
</t:master-page>
