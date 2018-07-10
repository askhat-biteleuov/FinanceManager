<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:url value="/resources/js/select2.min.js"/>"></script>
<link href="<c:url value="/resources/css/select2.min.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/select2-bootstrap.min.css"/>" rel="stylesheet">
<script src="<c:url value="/resources/js/editIncome.js"/>"></script>
<script src="<c:url value="/resources/js/formSlideAndDefaultDate.js"/>"></script>
<div class="table-responsive">
    <table id="incomes" class="table notes table-hover">
        <thead>
        <tr>
            <th>Сумма</th>
            <th>Счет</th>
            <th>Дата</th>
            <th>Хэштеги</th>
            <th>Заметка</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="income" items="${incomes}">
            <tr class="tableRow">
                <form class="editIncomeForm" action="<c:url value="/income/edit"/>" method="POST">
                    <td>
                            ${income.amount}
                    </td>
                    <td>
                            ${income.account.name}
                    </td>
                    <td>
                        <div class="incomeDate">
                                ${income.date}
                        </div>
                        <div class="input-group date" id="datepicker-modal-incomeadd">
                            <input type="text" class="form-control" id="incomeDate" name="date" value="${income.date}"
                                   readonly required>
                            <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-calendar"></i>
                                </span>
                        </div>
                        <script src="<c:url value="/resources/js/datePickerConfig.js"/>"></script>
                    </td>
                    <td>
                        <div class="hashtags">
                            <c:forEach var="hashtag" items="${income.hashTags}">
                                <c:url value="${paginationDto.url}" var="hashtagUrl">
                                    <c:param name="hashTag" value="${hashtag.text}"/>
                                    <c:if test="${accountId != null}">
                                        <c:param name="accountId" value="${accountId}"/>
                                    </c:if>
                                </c:url>
                                <a class="badge" href="<c:out value="${hashtagUrl}"/>">${hashtag.text}</a>
                            </c:forEach>
                        </div>
                        <select name="hashTags" class="tags-select form-control hashtagsSelect" multiple>
                            <c:forEach items="${hashtags}" var="hashtag">
                                <c:set var="contains" value="false"/>
                                <c:forEach var="incomeHashtag" items="${income.hashTags}">
                                    <c:if test="${incomeHashtag.text == hashtag.text}">
                                        <c:set var="contains" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${contains==true}">
                                        <option selected value="${hashtag.text}">${hashtag.text}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${hashtag.text}">${hashtag.text}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="note" data-name="tableNote" contenteditable="false">
                            ${income.note}
                    </td>
                    <input type="hidden" class="oldNote" value="${income.note}">
                    <input type="hidden" name="accountId" value="${income.account.id}">
                    <input type="hidden" name="incomeId" value="${income.id}">
                    <input type="hidden" name="note" class="newNote">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <td>
                    <div class="editIncomeDiv">
                        <div>
                            <form action="<c:url value="/income/delete"/>" method="POST">
                                <input type="hidden" name="incomeId" value="${income.id}">
                                <button type="submit" class="btn-link">
                                    <span class="red glyphicon glyphicon-trash"></span>
                                </button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </div>
                        <div>
                            <button class="editIncomeBtn dark-grey btn-link">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                        </div>
                        <div>
                            <button class="cancelIncomeBtn gold btn-link" hidden>
                                <span class="glyphicon glyphicon-remove-circle"></span>
                            </button>
                        </div>
                        <div>
                            <button class="acceptIncomeBtn dark-green btn-link" hidden>
                                <span class="glyphicon glyphicon-ok"></span>
                            </button>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
