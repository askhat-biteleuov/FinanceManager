<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="${outcomeTypeDto.name}">
    <h1>${outcomeTypeDto.name}</h1>
    <button>Добавить расход</button>
    <button>Удалить категорию</button>
    <br>
    <c:forEach var="i" begin="1" end="${count}">
        <c:url value="/outcometype/page" var="pageUrl">
            <c:param name="pageId" value="${i}"/>
            <c:param name="typeId" value="${outcomeTypeDto.id}"/>
        </c:url>
        <a href="${pageUrl}">${i}</a>
    </c:forEach>
    <table>
        <tr>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Счет</th>
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
            </tr>
            <tr>
                <td>
                        ${outcome.note}
                </td>
                <td></td>
                <td>
                    <button>Удалить расход</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:master-page>
