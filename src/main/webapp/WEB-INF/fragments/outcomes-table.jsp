<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:url value="/js/select2.min.js"/>"></script>
<link href="<c:url value="/css/select2.min.css"/>" rel="stylesheet">
<link href="<c:url value="/css/select2-bootstrap.min.css"/>" rel="stylesheet">
<script src="<c:url value="/js/editOutcome.js"/>"></script>
<script src="<c:url value="/js/formSlideAndDefaultDate.js"/>"></script>

<div class="table-responsive">
    <table id="outcomes" class="table notes table-hover">
        <thead>
        <tr>
            <th>Сумма</th>
            <th>Категория</th>
            <th>Дата</th>
            <th>Счет</th>
            <th>Хэштеги</th>
            <th>Заметка</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="outcome" items="${outcomes}">
            <tr class="tableRow">
                <form class="editOutcomeForm" action="<c:url value="/outcome/edit"/>" method="POST">
                    <td>
                            ${outcome.amount}
                    </td>
                    <td>
                        <div class="outcometype">
                                ${outcome.outcomeType.name}
                        </div>
                        <select name="outcomeTypeId" class="outcometypeSelect">
                            <c:forEach items="${outcometypes}" var="outcometype">
                                <c:choose>
                                    <c:when test="${outcome.outcomeType.id == outcometype.id}">
                                        <option selected value="${outcometype.id}">${outcometype.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${outcometype.id}">${outcometype.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <div class="outcomeDate">
                                ${outcome.date}
                        </div>
                        <div class="input-group date" id="datepicker-modal-outcomeadd">
                            <input type="text" class="form-control" id="outcomeDate" name="date" value="${outcome.date}"
                                   readonly required>
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-calendar"></i>
                            </span>
                        </div>
                        <script src="<c:url value="/js/datePickerConfig.js"/>"></script>
                    </td>
                    <td>
                            ${outcome.account.name}
                    </td>
                    <td>
                        <div class="hashtags">
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
                        </div>
                        <select name="hashTags" class="tags-select form-control hashtagsSelect" multiple>
                            <c:forEach items="${hashtags}" var="hashtag">
                                <c:set var="contains" value="false"/>
                                <c:forEach var="outcomeHashtag" items="${outcome.hashTags}">
                                    <c:if test="${outcomeHashtag.text == hashtag.text}">
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
                            ${outcome.note}
                    </td>
                    <input type="hidden" class="oldNote" value="${outcome.note}">
                    <input type="hidden" name="accountId" value="${outcome.account.id}">
                    <input type="hidden" name="outcomeId" value="${outcome.id}">
                    <input type="hidden" name="note" class="newNote">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <td>
                    <div class="editOutcomeDiv">
                        <div>
                            <form action="<c:url value="/outcome/delete"/>" method="POST">
                                <input type="hidden" name="outcomeId" value="${outcome.id}">
                                <button type="submit" class="btn-link">
                                    <span class="red glyphicon glyphicon-trash"></span>
                                </button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </div>
                        <div>
                            <button class="editOutcomeBtn dark-grey btn-link">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                        </div>
                        <div>
                            <button class="cancelOutcomeBtn gold btn-link" hidden>
                                <span class="glyphicon glyphicon-remove-circle"></span>
                            </button>
                        </div>

                        <button class="acceptOutcomeBtn dark-green btn-link" hidden>
                            <span class="glyphicon glyphicon-ok"></span>
                        </button>
                        </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>