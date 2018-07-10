<%@tag description="This is a master page" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="title" %>

<c:set var="userName" value="${pageContext.request.userPrincipal.name}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>${title}</title>
    <link href="<c:url value="/static/css/design.css" />" rel="stylesheet">
    <link href="http://faviconka.ru/ico/faviconka_ru_1613.ico" rel="shortcut icon" type="image/x-icon">
    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<c:url value="/static/js/jquery-3.2.1.min.js"/>"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
    <!-- Bootstrap Validator Plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>
    <%--Bootstrap Datepicker--%>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
    <link href="<c:url value="/static/css/navbar.css"/>" rel="stylesheet"/>

</head>
<body class="background">
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-toogle"
                            aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<c:url value="/"/>">FinanceManager</a>
                </div>
                <div class="collapse navbar-collapse" id="navbar-toogle">
                    <ul class="nav navbar-nav">
                        <c:if test="${userName == null}">
                            <li><a href="<c:url value="/login"/>">Войти в аккаунт</a></li>
                            <li><a href="<c:url value="/registration"/>">Пройти регистрацию</a></li>
                        </c:if>

                        <c:if test="${userName != null}">
                            <li class="navbar-center">
                                <p class="text-center">
                                    Баланс: <fmt:formatNumber type="currency"
                                                              currencySymbol="${statusBarDto.info.currency.characterCode}"
                                                              value="${statusBarDto.sumOfAllBalancesOfAccounts}"/>
                                    Расходы: <fmt:formatNumber type="currency"
                                                               currencySymbol="${statusBarDto.info.currency.characterCode}"
                                                               value="${statusBarDto.sumOfAllOutcomesForMonthForUser}"/>
                                </p>
                            </li>
                            <li><a href="<c:url value="/outcome/all"/>">Расходы</a></li>
                            <li><a href="<c:url value="/income/all"/>">Приходы</a></li>
                            <li><a href="<c:url value="/statistics"/>">Статистика</a></li>
                        </c:if>
                    </ul>
                    <c:if test="${userName != null}">
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a href="#" data-toggle="dropdown" class="dropdown-toggle" role="button"
                                   aria-haspopup="true" aria-expanded="false">
                                    Цели
                                    <span class="badge badge-notification">${goalsMessages.size()}</span>
                                    <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu">
                                    <c:forEach var="goal" items="${goalsMessages}">
                                        <c:if test="${goalsMessages.size()!=0}">
                                            <li>
                                                <span class="message-notification">
                                                    В этом месяце, вы еще не приблизились к своей цели:
                                                    <span style="font-weight: bold"><c:out value="${goal.name}"/></span>
                                                </span>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${goalsMessages.size()==0}">
                                        <li><span class="message-notification">Пока нет уведомлений</span></li>
                                    </c:if>
                                    <li class="divider"></li>
                                    <li><a class="blue" href="<c:url value="/goal"/>">Перейти к целям</a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" role="button"
                                   aria-haspopup="true" aria-expanded="false">Добрый день, ${userName}!<span
                                        class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="<c:url value="/profile"/>">
                                            Редактировать профиль
                                        </a>
                                    </li>
                                    <li>
                                        <a>
                                            <form action="<c:url value="/logout"/>" method="post">
                                                <button class=" btn btn-link btn-block" type="submit">Выход</button>
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                            </form>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </c:if>
                </div>
            </div>
        </nav>
        <div class="dobody">
            <jsp:doBody/>
        </div>
        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container">
                <p align="center" class="navbar-text-footer">Designed by JavaLab Spring 2017</p>
            </div>
        </nav>
    </body>
</html>