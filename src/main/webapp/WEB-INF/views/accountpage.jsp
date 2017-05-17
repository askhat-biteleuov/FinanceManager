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


    <div class="container">
        <h2 align="center" class="page-header">Счёт ${account.name}</h2>
        <div class="row center">
            <div class="article col-sm-5">
                <div align="center">
                    <a class="btn btn-blue" href="<c:url value="/account/income/page?itemId=${account.id}"/>">
                        Посмотреть все приходы
                    </a>
                    <a class="btn btn-blue" href="<c:url value="/outcome/page?itemId=${account.id}"/>">
                        Посмотреть все расходы
                    </a>
                </div>
                <br>
                <div id="addingOnPage"  align="center">
                    <div id="incomeAdd" class="adding">
                        <button type="button" class="btn btn-green btn-block showBtn">Добавить приход</button>
                        <form method="POST" action="<c:url value="/account/income/add"/>" id="incomeForm" class="trans" role="form">
                            <br/>
                            <div class="form-group">
                                <input type="text" id="incomeNote" name="note" placeholder="Note" class="form-control"
                                       maxlength="256"/>
                            </div>
                            <div class="form-group">
                                <input type="number" id="incomeAmount" name="amount" class="form-control"
                                       min="0.01" step="0.01" placeholder="Amount" required/>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="input-group date" id="datepicker-income">
                                    <input type="text" class="form-control" id="incomeDate" name="date" readonly required>
                                    <span class="input-group-addon">
                                        <i class="glyphicon glyphicon-calendar"></i>
                                    </span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <input type="hidden" name="hashTags" value="">
                            <input type="hidden" id="accountId" name="accountId" value="${account.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-default">Добавить</button>
                        </form>
                    </div>
                    <br>
                    <div id="outcomeAdd" class="adding">
                        <button type="button" class="btn btn-green btn-block showBtn outBtn">Добавить расход</button>
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
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="text" id="outcomeNote" name="note" placeholder="Note" class="form-control"
                                       maxlength="256"/>
                            </div>
                            <div class="form-group">
                                <input type="number" name="amount" class="form-control amountOutcome"
                                       min="0.01" step="0.01" placeholder="Amount" required/>
                                <div class="help-block with-errors"></div>
                            </div>
                            <script type="text/javascript" src="<c:url value="/resources/js/changeCurrency.js"/>"></script>
                            <div class="form-group currExchangeOutcome" hidden>
                                <div class="form-group">
                                    <input type="hidden" value="${account.currency.nominal}" class="fromNominalOutcome">
                                    <input type="hidden" value="${account.currency.curs}" class="fromCursOutcome">
                                    <input type="hidden" value="${user.info.currency.nominal}" class="toNominalOutcome">
                                    <input type="hidden" value="${user.info.currency.curs}" class="toCursOutcome">
                                    <input type="hidden" value="${user.info.currency.characterCode}" class="userCurOutcome">
                                    <input type="hidden" value="${account.currency.characterCode}" class="accountCurOutcome">
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <input class="checkboxOutcome" type="checkbox"/>
                                        </div>
                                        <input type="text" class="form-control customCursOutcome" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">${user.info.currency.characterCode}</div>
                                        <input type="text" name="defaultAmount" class="form-control defaultAmountOutcome"
                                               readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group date" id="datepicker-outcome">
                                    <input type="text" class="form-control" id="outcomeDate" name="date" readonly required>
                                    <span class="input-group-addon">
                                        <i class="glyphicon glyphicon-calendar"></i>
                                    </span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <input type="hidden" name="hashTags" value="">
                            <input type="hidden" name="accountId" value="${account.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-default">Добавить</button>
                        </form>
                    </div>
                    <br>
                    <div id="transfer" class="adding">
                        <button type="submit" class="btn btn-green btn-block showBtn">Перевести на другой счет</button>
                        <form method="POST" action="<c:url value="/transfer/add"/>" class="trans">
                            <br/>
                            <div class="form-group">
                                <select id="accountSelect" name="toAccountId" class="form-control">
                                    <option value="1" disabled>--- Select ---</option>
                                    <c:forEach items="${accounts}" var="acc">
                                        <c:if test="${acc.id != account.id}">
                                            <option value="${acc.id}">${acc.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="number" id="outcomeTransferAmount" name="outcomeAmount" class="form-control"
                                       min="0.01" step="0.01" placeholder="Amount" required/>
                                <div class="help-block with-errors"></div>
                            </div>
                            <script type="text/javascript" src="<c:url value="/resources/js/accountAndCurrencySelection.js"/>"></script>
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
                                        <input id="changeCursCheckBox" type="checkbox"/>
                                    </div>
                                    <input type="text" name="customCurs" class="form-control" disabled/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <div name="currencyCharacterCode" class="input-group-addon"></div>
                                    <input type="text" id="incomeTransferAmount" name="incomeAmount" class="form-control"
                                           readonly/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group date" id="datepicker-transfer">
                                    <input type="text" class="form-control" id="transferDate" name="date" readonly required>
                                    <span class="input-group-addon">
                                        <i class="glyphicon glyphicon-calendar"></i>
                                    </span>
                                </div>
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
            <div class="article col-sm-6">
                <div id="chart" align="center">
                    <form id="rangeForm">
                        <div class="input-daterange input-group datepicker" id="datepicker-range">
                            <input type="text" class="input-sm form-control" name="start" id="start" readonly/>
                            <span class="input-group-addon">to</span>
                            <input type="text" class="input-sm form-control" name="end" id="end" readonly/>
                        </div>
                        <button type="submit" class="btn btn-blue btn-block">Показать</button>
                        <input type="hidden" name="accountName" value="${account.name}"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <script src="<c:url value="/resources/js/datePickerConfig.js"/>"></script>
                    <script src="<c:url value="/resources/js/outcomesPiechart.js"/>"></script>
                    <script src="<c:url value="/resources/js/drawOutcomesPiechart.js"/>"></script>
                    <div id="piechart" style="width: 550px; height: 400px; margin: 0 auto"></div>
                </div>
            </div>
        </div>
    </div>
</t:master-page>
</html>
