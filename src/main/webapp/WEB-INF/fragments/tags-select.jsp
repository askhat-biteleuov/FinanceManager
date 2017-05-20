<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="<c:url value="/resources/js/select2.min.js"/>"></script>
<link href="<c:url value="/resources/css/select2.min.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/select2-bootstrap.min.css"/>" rel="stylesheet">

<div class = "form-group">
    <select name="hashTags" class="tags-select form-control" multiple>
        <c:forEach items="${hashtags}" var="hashtag">
            <option value="${hashtag.text}">${hashtag.text}</option>
        </c:forEach>
    </select>
</div>
<script>
    $(document).ready(function () {
        $('.tags-select').select2({
            placeholder: "Хэштеги",
            tags: true,
            width: '100%',
            theme: "bootstrap"
        });
    });
</script>
