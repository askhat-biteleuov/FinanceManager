<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="${outcomeTypeDto.name}">
    <h1>${outcomeTypeDto.name}</h1>

    <c:forEach var="i" begin="1" end="${count}">
        <c:url value="/outcometype/page" var="pageUrl">
            <c:param name="pageId" value="${i}"/>
            <c:param name="typeId" value="${outcomeTypeDto.id}"/>
        </c:url>
        <a href="${pageUrl}">${i}</a>
    </c:forEach>
    <table class="table">
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
            <th></th>
            <th></th>
        </tr>
        <tbody>
        <c:forEach var="outcome" items="${outcomeTypeDto.outcomes}">
            <tr>
                <td>
                        ${outcome.date} ${outcome.time}
                </td>
                <td>
                        ${outcome.amount}
                </td>
                <td>
                        ${outcome.account.name}
                </td>
                <td>
                        ${outcome.note}
                </td>
                <td></td>
                <td>
                    <form action="<c:url value="/outcome/delete"/>" method="POST"
                          onsubmit="return confirm('После удаления, категория и расходы по ней будут удалены! Вы хотите продолжить?')">
                        <input type="hidden" name="outcomeId" value="${outcome.id}">
                        <button type="submit">Удалить расход</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="<c:url value="/outcometype/delete"/>" method="POST"
          onsubmit="return confirm('После удаления, категория и расходы по ней будут удалены! Вы хотите продолжить?')">
        <input type="hidden" name="outcomeTypeId" value="${outcomeTypeDto.id}">
        <button type="submit">Удалить категорию</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</t:master-page>
