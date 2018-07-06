<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="outcometypeOutcomeAdd${outcomeType.key.id}" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Новый расход</h4>
            </div>
            <form method="POST" action="<c:url value="/outcome/add"/>" role="form">
                <div class="modal-body outcomeForm">
                    <div class="form-group">
                        <select class="form-control selectedAccount">
                            <c:forEach items="${user.accounts}" var="account">
                                <option value="${account.id}">${account.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" id="outcomeNote" name="note" placeholder="Заметка" class="form-control"
                               maxlength="256"/>
                    </div>
                    <div class="form-group">
                        <input type="number" name="amount" class="form-control amountOutcome"
                               min="0.01" step="0.01" placeholder="Сумма" required/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group currExchangeOutcome" hidden>
                        <div class="form-group">
                            <input type="hidden" class="fromNominalOutcome">
                            <input type="hidden" class="fromCursOutcome">
                            <input type="hidden" value="${user.info.currency.nominal}" class="toNominalOutcome">
                            <input type="hidden" value="${user.info.currency.curs}" class="toCursOutcome">
                            <input type="hidden" value="${user.info.currency.characterCode}" class="userCurOutcome">
                            <input type="hidden" class="accountCurOutcome">
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
                                <input type="text" name="defaultAmount" class="form-control defaultAmountOutcome" readonly/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group date" id="datepicker-outcometype">
                            <input type="text" class="form-control" id="outcomeDate" name="date" readonly required>
                            <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                        </div>
                        <div class="help-block with-errors"></div>
                    </div>
                    <%@include file="fragments/tags-select.jsp"%>
                    <input type="hidden" id="accountId" name="accountId">
                    <input type="hidden" name="outcomeTypeId" value="${outcomeType.key.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success btn-block" type="submit">Добавить</button>
                    <br>
                    <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
                </div>
            </form>
            <script src="<c:url value="/static/js/datePickerConfig.js"/>"></script>
        </div>
    </div>
</div>
