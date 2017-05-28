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
                    <%@include file="../fragments/tags-select.jsp" %>
                    <input type="hidden" id="accountId" name="accountId" value="${account.id}">
                    <input type="hidden" id="hashTags" name="hashTags" value="">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">
                            <input class="checkboxGoal" type="checkbox"/>
                        </div>
                        <input type="text" class="form-control" value="Перевести на цель" disabled/>
                    </div>
                </div>
                <div class="form-group goals" hidden>
                    <select name="goal" class="form-control">
                        <option disabled>--- Выберите цель ---</option>
                        <c:forEach items="${goals}" var="goal">
                            <option value="${goal.id}">${goal.name}</option>
                        </c:forEach>
                    </select><br/>
                </div>
                <div class="form-group goals" hidden>
                    <div class="input-group">
                        <div class="input-group-addon">
                            <input name="toGoal" class="radio-button percentToGoal" type="radio" checked/>
                        </div>
                        <input type="text" class="form-control" value="В процентах" disabled/>
                    </div>
                </div>
                <div class="form-group goals" hidden>
                    <input type="number" name="percents" class="form-control percents"
                           min="0" max="100" step="0.01" value="0"/><br/>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group goals" hidden>
                    <div class="input-group">
                        <div class="input-group-addon">
                            <input name="toGoal" class="radio-button amountToGoal" type="radio"/>
                        </div>
                        <input type="text" class="form-control" value="Указать размер перевода" disabled/>
                    </div>
                </div>
                <div class="form-group goals" hidden>
                    <input type="number" name="amountToGoal" class="form-control amountGoal"
                           min="0" step="0.01" value="0" disabled/><br/>
                    <div class="help-block with-errors"></div>
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
