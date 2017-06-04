<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="goalOutcomeAdd${goal.id}" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">
                    <i class="glyphicon glyphicon-remove"></i>
                </button>
                <h4 class="modal-title">Приблизиться к цели</h4>
            </div>
            <div id="transferToAccount" class="adding">
                <form method="POST" action="<c:url value="/transfer/to"/>" class="trans">
                    <div class="modal-body">
                        <div class="form-group">
                            <select id="accountSelectGoalOutcome" name="toAccountId" class="form-control">
                                <option value="1" disabled>--- Выберите аккаунт ---</option>
                                <c:forEach items="${accounts}" var="acc">
                                    <c:if test="${acc.id != goal.id}">
                                        <option value="${acc.id}">${acc.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="number" id="outcomeTransferAmountGoalOutcome" name="outcomeAmount" class="form-control"
                                   min="0.01" step="0.01" placeholder="Сумма (${goal.user.info.currency.characterCode})" required/>
                            <div class="help-block with-errors"></div>
                        </div>
                        <script type="text/javascript"
                                src="<c:url value="/resources/js/accountAndCurrencySelectionGoalOutcome.js"/>"></script>
                        <div class="form-group">
                            <input type="hidden" value="${goal.currency.nominal}" id="fromAccountNominalGoalOutcome">
                            <input type="hidden" value="${goal.currency.curs}" id="fromAccountCursGoalOutcome">
                            <input type="hidden" value="${goal.user.info.currency.nominal}" id="userCurrencyNominalGoalOutcome">
                            <input type="hidden" value="${goal.user.info.currency.curs}" id="userCurrencyCursGoalOutcome">
                            <c:forEach items="${accounts}" var="acc">
                                <c:if test="${acc.id != goal.id}">
                                    <div class="form-group" id="${acc.id}">
                                        <input type="hidden" value="${acc.currency.curs}" id="cursGoalOutcome">
                                        <input type="hidden" value="${acc.currency.nominal}" id="nominalGoalOutcome">
                                        <input type="hidden" value="${acc.currency.characterCode}" id="characterCodeGoalOutcome">
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <input id="changeCursCheckBoxGoalOutcome" type="checkbox"/>
                                </div>
                                <input type="text" name="customCursOutcome" class="form-control" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div name="currencyCharacterCodeOutcome" class="input-group-addon"></div>
                                <input type="text" id="incomeTransferAmountGoalOutcome" name="incomeAmount" class="form-control"
                                       readonly/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group date" id="datepicker-transfer-GoalOutcome">
                                <input type="text" class="form-control" id="transferDateGoalOutcome" name="date" readonly required>
                                <span class="input-group-addon">
                                        <i class="glyphicon glyphicon-calendar"></i>
                                    </span>
                            </div>
                            <div class="help-block with-errors"></div>
                        </div>
                        <input type="hidden" name="accountId" value="${goal.id}">
                        <input type="hidden" id="defaultTransferAmountGoalOutcome" name="defaultAmount">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-success btn-block" type="submit">Приблизиться</button>
                        <br>
                        <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
                    </div>
                </form>
            </div>
            <script src="<c:url value="/resources/js/datePickerConfig.js"/>"></script>
        </div>
    </div>
</div>
