<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="outcomeTypeDto" type="com.fm.internal.dtos.OutcomeTypeDto"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <form action="<c:url value="/outcometype/delete/move"/>" method="POST">
                <input type="hidden" name="outcomeTypeId" value="${outcomeTypeDto.id}">
                <button class="btn btn-primary btn-block" type="submit">Переместить все операции в "Без категории"
                </button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <form action="<c:url value="/outcometype/delete/all"/>" method="POST">
                <input type="hidden" name="outcomeTypeId" value="${outcomeTypeDto.id}">
                <button class="btn btn-primary btn-block" type="submit">Стереть все операции</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
        </div>
    </div>
</div>