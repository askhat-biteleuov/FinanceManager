<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script src="<c:url value="/resources/js/sendOutcomeTypeFormViaAjax.js"/>"></script>
<div id="outcometype-add" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Новая категория</h4>
            </div>
            <div id="add" align="center">
                <form method="POST" action="<c:url value="/outcometype/add"/>" role="form">
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="text" name="name" placeholder="Название категории" class="form-control"
                                   maxlength="256" required/>
                        </div>
                        <div class="form-group">
                            <input type="text" name="limit" placeholder="Планирую тратить" class="form-control"
                                   pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/>
                            <br/>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success btn-block">Добавить</button>
                        <br/>
                        <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Отмена</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>