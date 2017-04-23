<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="${outcomeTypeDto.name}">
    <div class="container">
        <h1>${outcomeTypeDto.name}</h1>
        <c:choose>
            <c:when test="${not empty outcomeTypeDto.outcomes}">
                <jsp:include page="../fragments/pagination.jsp"/>
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
                            <td>
                                <form action="<c:url value="/outcome/delete"/>" method="POST"
                                      onsubmit="return confirm('После удаления, категория будет удалена! Вы хотите продолжить?')">
                                    <input type="hidden" name="outcomeId" value="${outcome.id}">
                                    <button class="btn btn-danger" type="submit">Удалить расход</button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div>
                    <span class="lead">Пока нет расходов по данной категории.</span>
                </div>
            </c:otherwise>
        </c:choose>

        <div>
            <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#myModal">Удалить категорию
            </button>
                <%--Modal window--%>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
                <jsp:include page="outcometype-delete.jsp"/>
            </div>
        </div>
    </div>
</t:master-page>
