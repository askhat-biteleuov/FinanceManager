<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master-page title="Личный кабинет">
    <script src="<c:url value="/resources/js/imageUpload.js"/>"></script>
    <script src="<c:url value="/resources/js/sendPasswordViaAjax.js"/>"></script>
    <link href="<c:url value="/resources/css/back-button.css"/>" rel="stylesheet"/>

    <div id="scroiller">
        <div id="back" onclick="history.back()">◄ Назад</div>
    </div>

    <div class="container">
        <h1>Edit Profile</h1>
        <hr>
        <div class="row">
            <!-- left column -->
            <div class="col-md-3">
                <div class="text-center">
                    <img src="<c:url value="/resources/img/placeholder.png"/>" class="avatar img-circle" alt="avatar">
                    <h6>Загрузите другое фото...</h6>
                    <form action="<c:url value="/profile/avatar"/>" class="uploadImage">
                        <input type="file" class="form-control" accept="image/*">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn-success">Сохранить</button>
                    </form>
                </div>
            </div>

            <!-- edit form column -->
            <div class="col-md-5 personal-info">
                <h3>Персональная информация</h3>

                <form action="<c:url value="/profile/update"/>" class="form-horizontal" role="form" method="POST">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Имя:</label>
                        <div class="col-lg-8">
                            <label>
                                <input class="form-control" name="firstName" type="text" value="${userInfo.firstName}">
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Фамилия:</label>
                        <div class="col-lg-8">
                            <label>
                                <input class="form-control" name="lastName" type="text" value="${userInfo.lastName}">
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Email:</label>
                        <div class="col-lg-8">
                            <label>
                                <input class="form-control" name="email" type="text" value="${user.email}" disabled>
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"></label>
                        <div class="col-md-8">
                            <input type="hidden" name="currency" value="${userInfo.currency}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="reset" class="btn btn-default" value="Отмена">
                            <span></span>
                            <input type="submit" class="btn btn-primary" value="Сохранить изменения">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-4 personal-info changePassword">
                <h3>Смена пароля</h3>
                <form action="<c:url value="/profile/password"/>" class="form-horizontal" role="form" method="POST">
                    <div class="form-group">
                        <label class="col-md-3 control-label">Старый пароль:</label>
                        <div class="col-md-8">
                            <label>
                                <input class="form-control" name="oldPassword" type="password">
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Новый пароль:</label>
                        <div class="col-md-8">
                            <label>
                                <input class="form-control" name="password" type="password">
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Подтвердите новый пароль:</label>
                        <div class="col-md-8">
                            <label>
                                <input class="form-control" name="confirm" type="password">
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"></label>
                        <div class="col-md-8">
                            <button type="reset" class="btn btn-default">Отмена</button>
                            <span></span>
                            <button type="submit" class="btn btn-primary">Сохранить новый пароль</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <hr>


</t:master-page>