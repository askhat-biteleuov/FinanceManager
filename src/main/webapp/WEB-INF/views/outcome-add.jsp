<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="outcomeAdd${account.id}" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Новый расход</h4>
            </div>
            <form method="POST" id="outcomeForm" role="form" action="<c:url value="/outcome/add"/>">
                <div class="modal-body">
                    <div class="form-group">
                        <select name="outcomeTypeId" class="form-control">
                            <option disabled>--- Выберите категорию ---</option>
                            <c:forEach items="${types}" var="type">
                                <option value="${type.id}">${type.name}</option>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <div class="form-group">
                        <input type="text" id="outcomeNote" name="note" placeholder="Заметка" class="form-control"
                               maxlength="256"/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <input type="number" name="amount" class="form-control amountOutcome"
                               min="0.01" step="0.01" placeholder="Сумма" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <div class="input-group date" id="datepicker-modal-outcomeadd">
                            <input type="text" class="form-control" id="outcomeDate" name="date" readonly required>
                            <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                        </div>
                        <div class="help-block with-errors"></div>
                    </div>
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
                    <input type="hidden" id="hashTags" name="hashTags" value="">
                    <input type="hidden" id="accountId" name="accountId" value="${account.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success btn-block" type="submit">Добавить</button>
                    <br>
                    <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
                </div>
            </form>
            <script src="<c:url value="/resources/js/datePickerConfig.js"/>"></script>
        </div>
    </div>
</div>
