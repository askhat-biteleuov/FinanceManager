<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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