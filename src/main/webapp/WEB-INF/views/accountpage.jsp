<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<t:master-page title="${account.name}">
    <script src="<c:url value="/resources/js/formSlideAndDefaultDate.js"/>"></script>
    <script src="<c:url value="/resources/js/sendFormViaAjax.js"/>"></script>
    <link href="<c:url value="/resources/css/back-button.css"/>" rel="stylesheet"/>
    <style>
        .trans {
            display: none;
        }
    </style>


    <div id="scroiller">
        <div id="back" onclick="history.back()">◄ Назад</div>
    </div>

    <h2 align="center">Счёт ${account.name}</h2>
    <div id="adding" class="container" align="center">
        <div class="row">
            <div id="incomeAdd" class="col-xs-4">
                <button type="button" class="btn btn-default">Добавить доход</button>
                <form method="POST" action="<c:url value="/account/income/add"/>" id="incomeForm" class="trans"
                      role="form">
                    <div class="form-group">
                        <input type="text" id="incomeNote" name="note" placeholder="Note" class="form-control"
                               maxlength="256"/><br/>
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
            <div id="outcomeAdd" class="col-xs-4">
                <button type="button" class="btn btn-default">Добавить расход</button>
                <form method="POST" action="<c:url value="/outcome/add"/>" id="outcomeForm"
                      class="trans"
                      role="form">
                    <div class="form-group">
                        <select name="outcomeTypeId" class="form-control">
                            <option disabled>--- Select ---</option>
                            <c:forEach items="${types}" var="type">
                                <option value="${type.id}">${type.name}</option>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <div class="form-group">
                        <input type="text" id="outcomeNote" name="note" placeholder="Note" class="form-control"
                               maxlength="256"/><br/>
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
                </form>
            </div>
            <div id="transfer" class="col-xs-4">
                <button type="submit" class="btn btn-default">Перевести на другой счет</button>
                <form:form method="POST" action="/transfer" modelAttribute="transferDto" cssClass="trans">
                    <div class="form-group">
                        <form:select path="toAccountId" cssClass="form-control">
                            <form:option value="1" disabled="true" label="--- Select ---"/>
                            <c:forEach items="${accounts}" var="acc">
                                <c:if test="${acc.id != transferDto.accountId}">
                                    <form:option value="${acc.id}" label="${acc.name}"/>
                                </c:if>
                            </c:forEach>
                        </form:select><br/>
                    </div>
                    <div class="form-group">
                        <input type="text" id="transferAmount" name="amount" placeholder="Amount" class="form-control"
                               pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <input type="hidden" name="accountId" value="${account.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-default">Перевести</button>
                    <br>
                </form:form>
            </div>
        </div>
    </div>
    <br/>
    <br/>
    <br/>
    <div align="center">
        <a href="<c:url value="/account/income/page?itemId=${account.id}"/>">
            <button class="btn btn-default">
                Посмотреть все доходы
            </button>
        </a>
        <a href="<c:url value="/outcome/page?itemId=${account.id}"/>">
            <button class="btn btn-default">
                Посмотреть все расходы
            </button>
        </a>
    </div>
    <br/>
    <br/>
    <br/>
    <div id="chart" align="center">
        <form id="rangeForm">
            <div class="input-daterange input-group col-xs-2" id="datepicker-range">
                <input type="text" class="input-sm form-control" name="start" id="start" readonly/>
                <span class="input-group-addon">to</span>
                <input type="text" class="input-sm form-control" name="end" id="end" readonly/>
            </div>
            <br/>
           <button type="submit" class="btn btn-default">Submit</button>
            <input type="hidden" name="accountName" value="${account.name}"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <script>
                $('#datepicker-range').datepicker({
                    format: "yyyy-mm-dd",
                    todayBtn: "linked",
                    clearBtn: true
                });
            </script>
        </form>

        <script src="<c:url value="/resources/js/outcomesPiechart.js"/>"></script>
        <script src="<c:url value="/resources/js/drawOutcomesPiechart.js"/>"></script>
        <div id="piechart" style="width: 550px; height: 400px; margin: 0 auto"></div>
    </div>

</t:master-page>
</html>
