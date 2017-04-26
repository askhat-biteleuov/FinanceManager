<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="outcometype-add" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Добавьте новую категорию расходов</h4>
            </div>
            <form:form cssClass="form-horizontal" method="POST" action="/outcometype/add"
                       modelAttribute="outcometypeDto">
                <div class="modal-body">
                    <form:input cssClass="form-control" path="name" placeholder="Name"/><br>
                    <form:errors path="name" cssStyle="color: red"/>
                    <form:input cssClass="form-control" path="limit" placeholder="Limit"/><br>
                    <form:errors path="limit" cssStyle="color: red"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="modal-footer">
                    <form:button class="btn btn-success btn-block" type="submit">Добавить</form:button>
                    <br>
                    <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
                </div>
            </form:form>
        </div>
    </div>
</div>