<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                <td>
                        ${income.amount}
                </td>
                <td>
                        ${income.account.name}
                </td>
                <td>
                        ${income.date}
                </td>
                <td>
                    <c:forEach var="hashtag" items="${income.hashTags}">
                        <c:url value="/income/all" var="hashtagUrl">
                            <c:param name="hashTag" value="${hashtag.text}"/>
                            <c:if test="${accountId != null}">
                                <c:param name="accountId" value="${accountId}"/>
                            </c:if>
                        </c:url>
                        <a class="badge" href="<c:out value="${hashtagUrl}"/>">${hashtag.text}</a>
                    </c:forEach>
                </td>
                <td class="note" data-name="tableNote" contenteditable="false">
                        ${income.note}
                </td>
                <td>
                    <div class="editBar">
                        <div>
                            <form action="<c:url value="/income/delete"/>" method="POST">
                                <input type="hidden" name="incomeId" value="${income.id}">
                                <button type="submit" class="btn-link">
                                    <span class="red glyphicon glyphicon-trash"></span>
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
                            <button class="cancelBtn gold btn-link" hidden>
                                <span class="glyphicon glyphicon-remove-circle"></span>
                            </button>
                        </div>
                        <div>
                            <form class="saveNote" action="<c:url value="/income/update"/>"
                                  method="POST">
                                <input type="text" hidden class="oldVal">
                                <input type="hidden" name="accountId" value="${income.account.id}">
                                <input type="hidden" name="incomeId" value="${income.id}">
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
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
