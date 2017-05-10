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
                    <br/>
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
                    <br/>
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
                        <input type="text" id="amountOutcome" name="amount" placeholder="Amount" class="form-control"
                               pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <script type="text/javascript" src="/resources/js/changeCurrency.js"></script>
                    <div class="form-group" id="currExchangeOutcome" hidden>
                        <div class="form-group">
                            <input type="hidden" value="${account.currency.nominal}" id="fromNominalOutcome">
                            <input type="hidden" value="${account.currency.curs}" id="fromCursOutcome">
                            <input type="hidden" value="${user.info.currency.nominal}" id="toNominalOutcome">
                            <input type="hidden" value="${user.info.currency.curs}" id="toCursOutcome">
                            <input type="hidden" value="${user.info.currency.characterCode}" id="userCurOutcome">
                            <input type="hidden" value="${account.currency.characterCode}" id="accountCurOutcome">
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <input id="checkboxOutcome" type="checkbox"/>
                                </div>
                                <input type="text" id="customCursOutcome" class="form-control" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">${user.info.currency.characterCode}</div>
                                <input type="text" id="defaultAmountOutcome" name="defaultAmount" class="form-control"
                                       readonly/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="outcomeDate">Дата расхода:<br/></label>
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
                <form method="POST" action="/transfer/add" modelAttribute="transferDto" class="trans">
                    <br/>
                    <div class="form-group">
                        <select id="accountSelect" name="toAccountId" class="form-control">
                            <option value="1" disabled>--- Select ---</option>
                            <c:forEach items="${accounts}" var="acc">
                                <c:if test="${acc.id != account.id}">
                                    <option value="${acc.id}">${acc.name}</option>
                                </c:if>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <div class="form-group">
                        <input type="text" id="outcomeTransferAmount" name="outcomeAmount" placeholder="Amount" class="form-control"
                               pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <script type = "text/javascript" src="/resources/js/accountAndCurrencySelection.js"></script>
                    <div class="form-group">
                        <input type="hidden" value="${account.currency.nominal}" id="fromAccountNominal">
                        <input type="hidden" value="${account.currency.curs}" id="fromAccountCurs">
                        <input type="hidden" value="${account.user.info.currency.nominal}" id="userCurrencyNominal">
                        <input type="hidden" value="${account.user.info.currency.curs}" id="userCurrencyCurs">
                        <c:forEach items="${accounts}" var="acc">
                            <c:if test="${acc.id != account.id}">
                                <div class="form-group" id="${acc.id}">
                                    <input type="hidden" value="${acc.currency.curs}" id="curs">
                                    <input type="hidden" value="${acc.currency.nominal}" id="nominal">
                                    <input type="hidden" value="${acc.currency.characterCode}" id="characterCode">
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">
                                <input id="changeCursCheckBox" type="checkbox" />
                            </div>
                            <input  type="text" name="customCurs" class="form-control" disabled/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div name = "currencyCharacterCode" class="input-group-addon"></div>
                            <input type="text" id="incomeTransferAmount" name="incomeAmount" class="form-control" readonly />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="transferDate">Дата перевода:<br/></label>
                        <input id="transferDate" name="date" type="date" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <input type="hidden" name="accountId" value="${account.id}">
                    <input type="hidden" id="defaultTransferAmount" name="defaultAmount">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-default">Перевести</button>
                    <br>
                </form>
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
