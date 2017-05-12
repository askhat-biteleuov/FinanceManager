<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="incomeAdd${account.id}" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Новый приход</h4>
            </div>
            <form class="form-horizontal" method="POST" id="incomeForm" role="form" action="<c:url value="/account/income/add"/>">
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" id="incomeNote" name="note" placeholder="Note" class="form-control"
                               maxlength="256"/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <input type="text" id="incomeAmount" name="amount" placeholder="Amount" class="form-control"
                               pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label for="incomeDate">Дата прихода:<br/></label>
                        <input id="incomeDate" name="date" type="date" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <input type="hidden" id="accountId" name="accountId" value="${account.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success btn-block" type="submit">Добавить</button>
                    <br>
                    <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>
