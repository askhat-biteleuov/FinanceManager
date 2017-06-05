<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="outcomeTypeDto" type="com.fm.internal.dtos.OutcomeTypeDto"--%>

<%--First modal dialog--%>
<div class="modal fade" id="outcometype-delete" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">
                    Удалить "${outcomeTypeDto.name}"
                </h4>
            </div>
            <div class="modal-body">
                Удаляя "${outcomeTypeDto.name}", вы хотите:
            </div>
            <div class="modal-footer">
                <button class="btn btn-warning btn-block" type="button" data-dismiss="modal" data-toggle="modal"
                        data-target="#outcometype-move-outcomes">
                    Переместить все операции в другую категорию
                </button>
                <br>
                <form action="<c:url value="/outcometype/delete/all"/>" method="POST">
                    <input type="hidden" name="currentOutcomeTypeId" value="${outcomeTypeDto.id}">
                    <button class="btn btn-danger btn-block" type="submit">Сделать категорию неактивной</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <br>
                <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
            </div>
        </div>
    </div>
</div>
<%--Second modal dialog--%>
<div class="modal fade" id="outcometype-move-outcomes" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-content">
        <div class="modal-header">
            <button class="close" type="button" data-dismiss="modal">×</button>
            <h4 class="modal-title">
                Удалить "${outcomeTypeDto.name}"
            </h4>
        </div>
        <form action="<c:url value="/outcometype/delete/move"/>" method="POST">
            <div class="modal-body">
                Выберите куда переместить расходы:
                <select name="newOutcomeTypeId" size="1">
                    <c:forEach var="outcomeType" items="${user.outcomeTypes}">
                        <c:if test="${outcomeType.id!=outcomeTypeDto.id}">
                            <option value="${outcomeType.id}">${outcomeType.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success btn-block" type="submit">Принять</button>
                <br>
                <button class="btn btn-default btn-block" type="button" data-dismiss="modal" data-toggle="modal"
                        data-target="#outcometype-delete">
                    Отмена
                </button>
            </div>
            <input type="hidden" name="currentOutcomeTypeId" value="${outcomeTypeDto.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>