<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:master-page title="Цели">
    <script src="<c:url value="/resources/js/editGoal.js"/>"></script>
    <script src="<c:url value="/resources/js/formSlideAndDefaultDate.js"/>"></script>
    <script src="<c:url value="/resources/js/datePickerConfig.js"/>"></script>
    <script src="<c:url value="/resources/js/sendGoalTransferForm.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/accountAndCurrencySelectionGoalOutcome.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/accountAndCurrencySelectionGoalIncome.js"/>"></script>

    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <div class="col-sm-11 col-sm-offset-1" align="center">
            <div class="row">
                <c:forEach var="goal" items="${goals}">
                    <div class="col-sm-4 col-lg-3 ">
                        <div class="panel panel-shadow-1">
                            <c:url value="/goal/page" var="goalUrl">
                                <c:param name="goalId" value="${goal.id}"/>
                            </c:url>
                            <a href="<c:url value="${goalUrl}"/>">
                                <div class="panel-body-goal">
                                    <div class="editGoalDiv">
                                        <h3 class=" edit editGoalName gold" contenteditable="false">
                                            <c:out value="${goal.name}"/>
                                        </h3>
                                        <div class="defaultGoalDate">
                                                ${goal.date}
                                        </div>
                                        <div hidden class="editGoalDate">
                                            <div class="input-group date" id="datepicker-modal-goaladd">
                                                <input type="text" class="form-control" id="goalDate" name="date"
                                                       readonly required>
                                                <span class="input-group-addon">
                                                <i class="glyphicon glyphicon-calendar"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <button href="#" class="editGoalBtn dark-grey btn-link">
                                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                        </button>
                                        <div hidden class="edit editGoalAmount">
                                                ${goal.goalAmount}
                                        </div>
                                        <div class="defaultGoalAmount">
                                            <div class="progress progress-striped active ">
                                                <fmt:parseNumber value="${goal.balance}" var="balance"/>
                                                <fmt:parseNumber value="${goal.goalAmount}" var="goalAmount"/>
                                                <div class="progress-bar  progress-bar-warning" aria-valuemin="0"
                                                     aria-valuemax="100" style="width: ${balance/goalAmount*100}%;">
                                                    <span class="progress-value">${goal.balance}/${goal.goalAmount}</span>
                                                </div>
                                            </div>
                                        </div>

                                        <button href="#" hidden class="acceptGoalBtn dark-green btn-link">
                                                        <span class="glyphicon glyphicon-ok-circle"
                                                              aria-hidden="true"></span>
                                        </button>
                                        <button href="#" hidden class="cancelGoalBtn red btn-link">
                                                        <span class="glyphicon glyphicon-remove-circle"
                                                              aria-hidden="true"></span>
                                        </button>
                                        <input type="text" hidden class="goalId" value="${goal.id}">
                                        <input type="text" hidden class="oldGoalAmount">
                                        <input type="text" hidden class="oldGoalName">
                                    </div>
                                </div>
                            </a>
                            <div class="panel-footer">
                                <button class="btn btn-default btn-blue showBtn" type="button" data-toggle="modal"
                                        data-target='#goalIncomeAdd${goal.id}'>
                                    Приблизиться
                                </button>
                                <%@include file="goal-income-add.jsp"%>
                                <button class="btn btn-danger showBtn outBtn" type="button"
                                        data-toggle="modal"
                                        data-target="#goalOutcomeAdd${goal.id}">
                                    Расход
                                </button>
                                <%@include file="goal-outcome-add.jsp" %>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="col-sm-4 col-lg-3">
                    <a href="#" class="showBtn" data-toggle="modal" data-target="#goal-add">
                        <div class="panel panel-add-goal panel-shadow-1">
                            <h3>Новая цель</h3>
                            <h3 class="glyphicon glyphicon-plus"></h3>
                        </div>
                    </a>
                    <jsp:include page="goal-add.jsp"/>
                </div>
            </div>
        </div>
</t:master-page>