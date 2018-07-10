<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/src/main/webapp/WEB-INF/views/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Главная">
    <script src="<c:url value="/static/js/sendAccountFormViaAjax.js"/>"></script>
    <script src="<c:url value="/static/js/sendIncomeAndOutcomeForm.js"/>"></script>
    <script src="<c:url value="/static/js/changeCurrency.js"/>"></script>
    <script src="<c:url value="/static/js/formSlideAndDefaultDate.js"/>"></script>
    <script src="<c:url value="/static/js/goalFromIncome.js"/>"></script>
    <div class="container">
        <c:if test="${user == null}">
            <div align="center">
                <div class="col-xs-12 col-sm-10 col-md-6 col-sm-offset-1 col-md-offset-3">
                    <h1 class="page-header">Добрый день!</h1>
                    <p>Вы можете войти в свой аккаунт или пройти регистрацию.</p>
                    <div class="row">
                        <a class="btn btn-default col-xs-12 col-sm-8" href="<c:url value="/registration"/>"
                           type="submit">Пройти регистрацию</a>
                        <a class="btn btn-green col-xs-12 col-sm-4" href="<c:url value="/login"/>" type="submit">Войти в
                            аккаунт</a>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${user != null}">
            <div align="center">
                    <%--<script src="<c:url value="/static/js/hashtagsAutocomplete.js"/>"></script>
                    <div class="dropdown">
                        <input type="text" class="hashtagSearchInput form-control dropdown-toggle" placeholder="Начните ввод хэштегов через пробел" data-toggle="dropdown">
                    </div>--%>
                <h1 class="page-header">Счета</h1>
                <br>
                <script src="<c:url value="/static/js/editAccount.js"/>"></script>
                <div class="container">
                    <div class="row">
                        <c:forEach var="account" items="${accounts}">
                            <div class="col-sm-4 col-lg-3 ">
                                <div class="panel panel-shadow-1">
                                    <a href="<c:url value="/account/page?id=${account.id}"/>">
                                        <div class="panel-body">
                                            <div class="editAccountDiv">
                                                <h3 class=" edit editAccountName blue" contenteditable="false">
                                                    <c:out value="${account.name}"/>
                                                </h3>
                                                <input type="text" hidden class="oldAccountName">
                                                <input hidden class="accountId" value="${account.id}">
                                                <input hidden class="accountBalance" value="${account.balance}">
                                                <button href="#" hidden class="acceptAccountBtn dark-green btn-link">
                                                    <span class="glyphicon glyphicon-ok-circle"
                                                          aria-hidden="true"></span>
                                                </button>
                                                <button href="#" hidden class="cancelAccountBtn red btn-link">
                                                    <span class="glyphicon glyphicon-remove-circle"
                                                          aria-hidden="true"></span>
                                                </button>
                                                <button href="#" class="editAccountBtn dark-grey btn-link">
                                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                                </button>
                                                <div class="memo">
                                                    <c:choose>
                                                        <c:when test="${user.info.currency.characterCode==account.currency.characterCode}">
                                                            ${account.balance}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatNumber type="currency"
                                                                              currencySymbol="${account.currency.characterCode}"
                                                                              value="${account.balance}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                    <div class="panel-footer">
                                        <button class="btn btn-default btn-green showBtn outBtn" type="button" data-toggle="modal"
                                                data-target="#outcomeAdd${account.id}">
                                            Расход
                                        </button>
                                        <%@include file="outcome-add.jsp"%>
                                        <button class="btn btn-default btn-blue showBtn" type="button" data-toggle="modal"
                                                data-target='#incomeAdd${account.id}'>
                                            Приход
                                        </button>
                                        <%@include file="income-add.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="col-sm-4 col-lg-3 ">
                            <a href="#" data-toggle="modal" data-target="#accountAdd">
                                <div class="panel panel-add panel-shadow-1">
                                    <h3>Новый счет</h3>
                                    <h3 class="glyphicon glyphicon-plus"></h3>
                                </div>
                            </a>
                            <jsp:include page="account-add.jsp"/>
                        </div>
                    </div>
                </div>
                <h1 class="page-header">Категории расходов</h1>
                <br>
                <script src="<c:url value="/static/js/editOutcometype.js"/>"></script>
                <div class="container">
                    <div class="row">
                        <c:forEach var="outcomeType" items="${outcomeTypes}">
                            <div class="col-sm-4 col-lg-3 ">
                                <c:url value="/outcome/all" var="outcomeTypeUrl">
                                    <c:param name="outcomeTypeId" value="${outcomeType.key.id}"/>
                                </c:url>
                                <div class="panel panel-shadow-1">
                                    <a href="<c:out value="${outcomeTypeUrl}"/>">
                                        <div class="panel-body">
                                            <div class="editOutcometypeDiv">
                                                <h3 class="edit editOutcometypeName dark-green" contenteditable="false">
                                                    <c:out value="${outcomeType.key.name}"/>
                                                </h3>
                                                <div hidden class=" edit editOutcometypeLimit">
                                                        ${outcomeType.key.limit}
                                                </div>
                                                <input type="text" hidden class="outcometypeId"
                                                       value="${outcomeType.key.id}">
                                                <input type="text" hidden class="oldOutcometypeLimit">
                                                <input type="text" hidden class="oldOutcometypeName">
                                                <button href="#" hidden
                                                        class="acceptOutcometypeBtn dark-green btn-link">
                                                    <span class="glyphicon glyphicon-ok-circle"
                                                          aria-hidden="true"></span>
                                                </button>
                                                <button href="#" hidden class="cancelOutcometypeBtn red btn-link">
                                                    <span class="glyphicon glyphicon-remove-circle"
                                                          aria-hidden="true"></span>
                                                </button>
                                                <button href="#" class="editOutcometypeBtn dark-grey btn-link">
                                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                                </button>

                                                <div class="defaultViewOfLimit">
                                                    <c:if test="${outcomeType.key.limit>0}">
                                                        <c:choose>
                                                            <c:when test="${outcomeType.value > outcomeType.key.limit}">
                                                        <span style="color:red">
                                                            ${outcomeType.value} / ${outcomeType.key.limit}
                                                        </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                        <span class="memo">
                                                           ${outcomeType.value} / ${outcomeType.key.limit}
                                                        </span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                    <div class="panel-footer">
                                        <button class="btn btn-default btn-green showBtn outBtnWithAccounts" type="button" data-toggle="modal"
                                                data-target="#outcometypeOutcomeAdd${outcomeType.key.id}">
                                            Расход
                                        </button>
                                        <%@include file="outcometype-outcome-add.jsp"%>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="col-sm-4 col-lg-3">
                            <a href="#" data-toggle="modal" data-target="#outcometype-add" >
                                <div class="panel panel-add panel-shadow-1">
                                    <h3>Новая категория</h3>
                                    <h3 class="glyphicon glyphicon-plus"></h3>
                                </div>
                            </a>
                            <jsp:include page="outcometype-add.jsp"/>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</t:master-page>