<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/src/main/webapp/WEB-INF/views/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:master-page title="Список расходов">
    <jsp:include page="fragments/back-button.jsp"/>
    <div class="container">
        <div class="col-sm-11 col-sm-offset-1">
            <h2 align="center" class="page-header">Расходы</h2>
            <div class="article">
                <div class="row">
                    <form class="form-group form-inline col-md-5 col-sm-5" method="get" action="/outcome/all"
                          id="searchForm">
                        <select name="hashTag" class=" col-sm-3 tags-select form-control hashtagsSearch" multiple>
                            <c:forEach items="${hashtags}" var="hashtag">
                                <option value="${hashtag.text}">${hashtag.text}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $(document).ready(function () {
                                $(".hashtagsSearch").select2({
                                    placeholder: "Хэштеги",
                                    tags: true,
                                    theme: "bootstrap",
                                    tokenSeparators: [',', ' ', '#']
                                });
                            });
                        </script>
                        <button type="submit" class="btn btn-green col-xs-12 col-sm-2">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                        <c:if test="${accountId != null}">
                            <input type="hidden" name="accountId" value="${accountId}"/>
                        </c:if>
                        <c:if test="${outcomeTypeId != null}">
                            <input type="hidden" name="outcomeTypeId" value="${outcomeTypeId}"/>
                        </c:if>
                    </form>
                    <form:form method="get" action="/outcome/all" modelAttribute="rangeDto" id="rangeForm"
                               cssClass="form-inline col-md-7 col-sm-7">
                        <div class="input-daterange input-group col-sm-9 col-sm-pull-3" id="datepicker-range">
                            <form:input path="start" type="text" cssClass="form-control" name="start"
                                        id="start"
                                        readonly="true"/>
                            <span class="input-group-addon">to</span>
                            <form:input path="end" type="text" cssClass="form-control" name="end" id="end"
                                        readonly="true"/>
                        </div>
                        <button type="submit" class="btn btn-blue col-xs-12 col-sm-3 col-sm-push-9">Показать</button>
                        <input type="hidden" name="itemId" value="${rangeDto.id}">
                        <c:if test="${accountId != null}">
                            <input type="hidden" name="accountId" value="${accountId}"/>
                        </c:if>
                        <c:if test="${hashTag != null}">
                            <input type="hidden" name="hashTag" value="${hashTag}"/>
                        </c:if>
                        <c:if test="${outcomeTypeId != null}">
                            <input type="hidden" name="outcomeTypeId" value="${outcomeTypeId}"/>
                        </c:if>
                        <script>
                            $('#datepicker-range').datepicker({
                                format: "yyyy-mm-dd",
                                todayBtn: "linked",
                                clearBtn: true
                            });
                        </script>
                    </form:form>
                </div>
                <c:choose>
                    <c:when test="${not empty outcomes}">
                        <br>
                        <jsp:include page="fragments/outcomes-table.jsp"/>
                        <div align="center">
                            <jsp:include page="fragments/pagination.jsp"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <br>
                            <h3>Пока нет расходов по данному счету.</h3>
                        </div>
                    </c:otherwise>
                </c:choose>
                <c:if test="${outcomeTypeId != null}">
                    <div>
                        <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#outcometype-delete">
                            Удалить категорию
                        </button>
                        <jsp:include page="outcometype-delete.jsp"/>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</t:master-page>
