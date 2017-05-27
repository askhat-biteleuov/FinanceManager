<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список расходов">
    <script src="<c:url value="/resources/js/editNotes.js"/>"></script>
    <style>
        [contenteditable]:focus {
            padding: 5px;
        }
    </style>
    <jsp:include page="../fragments/back-button.jsp"/>
    <div class="container">
        <div class="col-sm-11 col-sm-offset-1">
            <h2 align="center" class="page-header">Расходы</h2>
            <div class="article">
                <div class="row">
                    <form class="form-group form-inline col-md-5 col-sm-5" method="get" action="/outcome/all"
                          id="searchForm">
                        <input class="form-control col-sm-3" placeholder="Hashtag" type="text" name="hashTag"/>
                        <button type="submit" class="btn btn-green col-xs-12 col-sm-2">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                        <c:if test="${accountId != null}">
                            <input type="hidden" name="accountId" value="${accountId}"/>
                        </c:if>
                        <c:if test="${outcomeTypeId != null}">
                            <input type="hidden" name="outcomeTypeId" value="${outcomeTypeId}"/>
                        </c:if>
                    </form>
                    <form:form method="get" action="/outcome/all" modelAttribute="rangeDto" id="rangeForm"
                               cssClass="form-inline col-md-7 col-sm-7">
                        <div class="input-daterange input-group col-sm-9 col-sm-pull-3" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="form-control" name="start"
                                        id="start"
                                        readonly="true"/>
                            <span class="input-group-addon">to</span>
                            <form:input path="end" type="text" cssClass="form-control" name="end" id="end"
                                        readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue col-xs-12 col-sm-3 col-sm-push-9">Показать</button>
                        <input type="hidden" name="itemId" value="${rangeDto.id}">
                        <c:if test="${accountId != null}">
                            <input type="hidden" name="accountId" value="${accountId}"/>
                        </c:if>
                        <c:if test="${hashTag != null}">
                            <input type="hidden" name="hashTag" value="${hashTag}"/>
                        </c:if>
                        <c:if test="${outcomeTypeId != null}">
                            <input type="hidden" name="outcomeTypeId" value="${outcomeTypeId}"/>
                        </c:if>
                        <script>
                            $('#datepicker-range').datepicker({
                                format: "yyyy-mm-dd",
                                todayBtn: "linked",
                                clearBtn: true
                            });
                        </script>
                    </form:form>
                </div>
                <c:choose>
                    <c:when test="${not empty outcomes}">
                        <br>
                        <div class="table-responsive">
                            <table id="outcomes" class="table notes table-hover">
                                <thead>
                                <tr>
                                    <th>Редактировать заметку</th>
                                    <th>Заметка</th>
                                    <th>Хэштеги</th>
                                    <th>Сумма</th>
                                    <th>Категория</th>
                                    <th>Дата</th>
                                    <th>Счет</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="outcome" items="${outcomes}">
                                    <tr class="tableRow">
                                        <td>
                                            <div class="row editBar">
                                                <div>
                                                    <form action="<c:url value="/outcome/delete"/>" method="POST">
                                                        <input type="hidden" name="outcomeId" value="${outcome.id}">
                                                        <button type="submit" class="btn-link">
                                                            <span class="glyphicon glyphicon-trash"></span>
                                                        </button>
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}"/>
                                                    </form>
                                                </div>
                                                <div>
                                                    <button class="editBtn dark-grey btn-link">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                    </button>
                                                </div>
                                                <div>
                                                    <button class="cancelBtn red btn-link" hidden>
                                                        <span class="glyphicon glyphicon-remove-circle"></span>
                                                    </button>
                                                </div>
                                                <div>
                                                    <form class="saveNote" action="<c:url value="/outcome/update"/>"
                                                          method="POST">
                                                        <input type="text" hidden class="oldVal">
                                                        <input type="hidden" name="accountId" value="${outcome.account.id}">
                                                        <input type="hidden" name="outcomeId" value="${outcome.id}">
                                                        <input type="hidden" name="note">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}"/>
                                                        <button type="submit" class="saveBtn dark-green btn-link" hidden>
                                                            <span class="glyphicon glyphicon-ok"></span>
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="note" data-name="tableNote" contenteditable="false">
                                                ${outcome.note}
                                        </td>
                                        <td>
                                            <c:forEach var="hashtag" items="${outcome.hashTags}">
                                                <c:url value="/outcome/all" var="hashtagUrl">
                                                    <c:param name="hashTag" value="${hashtag.text}"/>
                                                    <c:if test="${accountId != null}">
                                                        <c:param name="accountId" value="${accountId}"/>
                                                    </c:if>
                                                    <c:if test="${outcomeTypeId != null}">
                                                        <c:param name="outcomeTypeId" value="${outcomeTypeId}"/>
                                                    </c:if>
                                                </c:url>
                                                <a class="badge" href="<c:out value="${hashtagUrl}"/>">${hashtag.text}</a>
                                            </c:forEach>
                                        </td>
                                        <td>
                                                ${outcome.amount}
                                        </td>
                                        <td>
                                                ${outcome.outcomeType.name}
                                        </td>
                                        <td>
                                                ${outcome.date} ${outcome.time}
                                        </td>
                                        <td>
                                                ${outcome.account.name}
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div align="center">
                            <jsp:include page="../fragments/pagination.jsp"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <br>
                            <h3>Пока нет расходов по данному счету.</h3>
                        </div>
                    </c:otherwise>
                </c:choose>
                <c:if test="${outcomeTypeId != null}">
                    <div>
                        <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#outcometype-delete">
                            Удалить категорию
                        </button>
                        <jsp:include page="outcometype-delete.jsp"/>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</t:master-page>
