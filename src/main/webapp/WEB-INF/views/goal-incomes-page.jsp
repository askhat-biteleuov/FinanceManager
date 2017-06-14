<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="${goal.name}">
    <script src="<c:url value="/resources/js/editNotes.js"/>"></script>
    <style>
        [contenteditable]:focus {
            padding: 5px;
        }
    </style>
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <div class="col-sm-11 col-sm-offset-1">
            <h2 align="center" class="page-header">Приходы</h2>
            <div class="article">
                <div class="row">
                    <form:form method="get" action="/goal/page/incomes" modelAttribute="rangeDto" id="rangeForm"
                               cssClass="form-inline col-md-7 col-sm-7">
                        <div class="input-daterange input-group col-sm-9 col-sm-pull-3" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="form-control" name="start"
                                        id="start"
                                        readonly="true"/>
                            <span class="input-group-addon">to</span>
                            <form:input path="end" type="text" cssClass="form-control" name="end" id="end"
                                        readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue col-xs-12 col-sm-3 col-sm-push-9">Показать</button>
                        <input type="hidden" name="itemId" value="${rangeDto.id}">
                        <c:if test="${goalId != null}">
                            <input type="hidden" name="goalId" value="${goalId}"/>
                        </c:if>
                        <script>
                            $('#datepicker-range').datepicker({
                                format: "yyyy-mm-dd",
                                todayBtn: "linked",
                                clearBtn: true
                            });
                        </script>
                    </form:form>
                </div>
                <br>
                <ul class="nav nav-tabs nav-justified">
                    <c:url value="/goal/page/incomes" var="incomesTabUrl">
                        <c:if test="${goalId != null}">
                            <c:param name="goalId" value="${goalId}"/>
                        </c:if>
                    </c:url>
                    <li class="active"><a href="${incomesTabUrl}">Приходы</a></li>
                    <c:url value="/goal/page/outcomes" var="outcomesTabUrl">
                        <c:if test="${goalId != null}">
                            <c:param name="goalId" value="${goalId}"/>
                        </c:if>
                    </c:url>
                    <li><a href="${outcomesTabUrl}">Расходы</a></li>
                </ul>
                <c:choose>
                    <c:when test="${not empty incomes}">
                        <br>
                        <jsp:include page="../fragments/incomes-table.jsp"/>
                        <div align="center">
                            <jsp:include page="../fragments/pagination.jsp"/>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <div>
                            <br>
                            <h3>Пока нет приходов на данную цель.</h3>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</t:master-page>
