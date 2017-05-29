<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Цели">
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <div class="col-sm-11 col-sm-offset-1" align="center">
            <div class="row">
                <c:forEach var="goal" items="${goals}">
                    <div class="col-sm-4 col-lg-3 ">
                        <div class="panel panel-shadow-1">
                            <c:url value="/goal" var="goalUrl">
                                <c:param name="goalId" value="${goal.id}"/>
                            </c:url>
                            <a href="<c:url value="${goalUrl}"/>">
                                <div class="panel-body">
                                    <div class="editGoalDiv">
                                        <h3 class=" edit editGoalName gold" contenteditable="false">
                                            <c:out value="${goal.name}"/>
                                        </h3>
                                        <button href="#" hidden class="acceptGoalBtn dark-green btn-link">
                                                        <span class="glyphicon glyphicon-ok-circle"
                                                              aria-hidden="true"></span>
                                        </button>
                                        <button href="#" hidden class="cancelGoalBtn red btn-link">
                                                        <span class="glyphicon glyphicon-remove-circle"
                                                              aria-hidden="true"></span>
                                        </button>
                                        <button href="#" class="editGoalBtn dark-grey btn-link">
                                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                        </button>
                                        <div class="progress progress-striped active">
                                            <fmt:parseNumber value="${goal.balance}" var="balance"/>
                                            <fmt:parseNumber value="${goal.goalAmount}" var="goalAmount"/>
                                            <div class="progress-bar progress-bar-warning" aria-valuemin="0"
                                                 aria-valuemax="100" style="width: ${balance/goalAmount*100}%;">
                                                <span class="progress-value">${goal.balance}/${goal.goalAmount}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <div class="panel-footer">
                                <button class="btn btn-default btn-green showBtn outBtn" type="button"
                                        data-toggle="modal"
                                        data-target="#outcomeAdd${goal.id}">
                                    Расход
                                </button>
                                <%@include file="outcome-add.jsp" %>
                                <button class="btn btn-default btn-blue showBtn" type="button" data-toggle="modal"
                                        data-target='#incomeAdd${goal.id}'>
                                    Приход
                                </button>
                                    <%--<%@include file="income-add.jsp"%>--%>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="col-sm-4 col-lg-3">
                    <a href="#" data-toggle="modal" data-target="#goal-add">
                        <div class="panel panel-add panel-shadow-1">
                            <h3>Новая цель</h3>
                            <h3 class="glyphicon glyphicon-plus"></h3>
                        </div>
                    </a>
                    <jsp:include page="goal-add.jsp"/>
                </div>
            </div>
        </div>
</t:master-page>