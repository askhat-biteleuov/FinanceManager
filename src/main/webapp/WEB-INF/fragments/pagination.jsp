<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="paginationDto" type="com.fm.internal.dtos.PaginationDto"--%>

<ul class="pagination">
    <c:url value="${paginationDto.url}" var="firstPageUrl">
        <c:param name="pageId" value="${1}"/>
        <c:param name="itemId" value="${paginationDto.itemId}"/>
    </c:url>
    <c:choose>
        <c:when test="${paginationDto.selectedPage == 1}">
            <li class="disabled"><a href="${firstPageUrl}">Первая</a></li>
        </c:when>
        <c:otherwise>
            <li class=""><a href="${firstPageUrl}">First</a></li>
        </c:otherwise>
    </c:choose>

    <c:forEach var="i" begin="${paginationDto.startPage}" end="${paginationDto.endPage}">
        <c:url value="${paginationDto.url}" var="pageUrl">
            <c:param name="pageId" value="${i}"/>
            <c:param name="itemId" value="${paginationDto.itemId}"/>
        </c:url>
        <c:choose>
            <c:when test="${paginationDto.selectedPage == i}">
                <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
            </c:when>
            <c:otherwise>
                <li class=""><a href="${pageUrl}"><c:out value="${i}"/></a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:url value="${paginationDto.url}" var="lastPageUrl">
        <c:param name="pageId" value="${paginationDto.pageCount}"/>
        <c:param name="itemId" value="${paginationDto.itemId}"/>
    </c:url>
    <c:choose>
        <c:when test="${paginationDto.selectedPage == paginationDto.pageCount}">
            <li class="disabled"><a href="${lastPageUrl}">Последняя</a></li>
        </c:when>
        <c:otherwise>
            <li class=""><a href="${lastPageUrl}" name="pageId">Last</a></li>
        </c:otherwise>
    </c:choose>
</ul>