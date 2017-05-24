<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="paginationDto" type="com.fm.internal.dtos.PaginationDto"--%>

<ul class="pagination">
    <c:url value="${paginationDto.url}" var="firstPageUrl">
        <c:param name="pageId" value="${1}"/>
        <c:if test="${accountId != null}">
            <c:param name="accountId" value="${accountId}"/>
        </c:if>
        <c:if test="${outcomeTypeId != null}">
            <c:param name="outcomeTypeId" value="${outcomeTypeId}"/>
        </c:if>
        <c:if test="${hashTag != null}">
            <c:param name="hashTag" value="${hashTag}"/>
        </c:if>
        <c:if test="${rangeDto.end != null}">
            <c:param name="end" value="${rangeDto.end}"/>
        </c:if>
        <c:if test="${rangeDto.start != null}">
            <c:param name="start" value="${rangeDto.start}"/>
        </c:if>
    </c:url>
    <c:choose>
        <c:when test="${paginationDto.selectedPage == 1}">
            <li class="disabled"><a href="${firstPageUrl}"><<</a></li>
        </c:when>
        <c:otherwise>
            <li class=""><a href="${firstPageUrl}"><<</a></li>
        </c:otherwise>
    </c:choose>

    <c:forEach var="i" begin="${paginationDto.startPage}" end="${paginationDto.endPage}">
        <c:url value="${paginationDto.url}" var="pageUrl">
            <c:param name="pageId" value="${i}"/>
            <c:if test="${accountId != null}">
                <c:param name="accountId" value="${accountId}"/>
            </c:if>
            <c:if test="${outcomeTypeId != null}">
                <c:param name="outcomeTypeId" value="${outcomeTypeId}"/>
            </c:if>
            <c:if test="${hashTag != null}">
                <c:param name="hashTag" value="${hashTag}"/>
            </c:if>
            <c:if test="${rangeDto.end != null}">
                <c:param name="end" value="${rangeDto.end}"/>
            </c:if>
            <c:if test="${rangeDto.start != null}">
                <c:param name="start" value="${rangeDto.start}"/>
            </c:if>
        </c:url>
        <c:choose>
            <c:when test="${paginationDto.selectedPage == i}">
                <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
            </c:when>
            <c:otherwise>
                <li class="${paginationDto.selectedPage - i<2 && paginationDto.selectedPage - i>-2 ? "":"hidden-xs"}">
                    <a href="${pageUrl}"><c:out value="${i}"/></a>
                </li>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:url value="${paginationDto.url}" var="lastPageUrl">
        <c:param name="pageId" value="${paginationDto.pageCount}"/>
        <c:if test="${accountId != null}">
            <c:param name="accountId" value="${accountId}"/>
        </c:if>
        <c:if test="${hashTag != null}">
            <c:param name="hashTag" value="${hashTag}"/>
        </c:if>
        <c:if test="${outcomeTypeId != null}">
            <c:param name="outcomeTypeId" value="${outcomeTypeId}"/>
        </c:if>
        <c:if test="${rangeDto.end != null}">
            <c:param name="end" value="${rangeDto.end}"/>
        </c:if>
        <c:if test="${rangeDto.start != null}">
            <c:param name="start" value="${rangeDto.start}"/>
        </c:if>
    </c:url>
    <c:choose>
        <c:when test="${paginationDto.selectedPage == paginationDto.pageCount}">
            <li class="disabled"><a href="${lastPageUrl}">>></a></li>
        </c:when>
        <c:otherwise>
            <li class=""><a href="${lastPageUrl}" name="pageId">>></a></li>
        </c:otherwise>
    </c:choose>
</ul>