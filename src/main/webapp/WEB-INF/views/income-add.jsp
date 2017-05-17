<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="incomeAdd${account.id}" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Новый приход</h4>
            </div>
            <form method="POST" id="incomeForm" role="form" action="<c:url value="/account/income/add"/>">
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" id="incomeNote" name="note" placeholder="Заметка" class="form-control"
                               maxlength="256"/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <input type="number" id="incomeAmount" name="amount" class="form-control"
                               min="0.01" step="0.01" placeholder="Сумма" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <div class="input-group date" id="datepicker-modal-incomeadd">
                            <input type="text" class="form-control" id="outcomeDate" name="date" readonly required>
                            <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                        </div>
                        <div class="help-block with-errors"></div>
                    </div>
                    <input type="hidden" id="accountId" name="accountId" value="${account.id}">
                    <input type="hidden" id="hashTags" name="hashTags" value="">
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
