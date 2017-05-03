<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="modal fade" id="accountAdd" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title">Добавьте новый аккаунт</h4>
            </div>
            <form class="form-horizontal" method="POST" id="accountForm" role="form" action="<c:url value="/account/add"/>">
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" id="accountName" name="name" placeholder="Name" class="form-control"
                               maxlength="256"/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <select class="form-control" name="currency">
                            <option disabled>--- Select ---</option>
                            <c:forEach items="${currencies}" var="currency">
                                    <option value="${currency.characterCode}" id="accountCurrency">${currency.name}</option>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <div class="form-group">
                        <input type="text" id="accountBalance" name="balance" placeholder="Balance" class="form-control"
                               pattern="^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\.[0-9]{2})?$" required/><br/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success btn-block" type="submit">Добавить</button>
                    <br>
                    <button class="btn btn-default btn-block" type="button" data-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>