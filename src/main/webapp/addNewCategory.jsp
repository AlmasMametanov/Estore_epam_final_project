<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<style>
    table,th,td {
        border: 1px solid black;
    }
</style>
<body>
<form action="/addNewCategory" method="post">
    <c:forEach var="locale" items="${sessionScope.locales}">
        ${locale.name}<input name="categoryName" type="text" required/><br>
    </c:forEach>
    <c:if test="${requestScope.categories != null}">
        <select name="parentId" required>
            <label><fmt:message key="label.chooseParentCategory"/></label>
            <c:forEach var="category" items="${requestScope.categories}">
                <c:set var="category" value="${category}" scope="request"/>
                <jsp:include page="getCategoriesForAddCategory.jsp"/>
            </c:forEach>
        </select><br/>
    </c:if>
    <button type="submit"><fmt:message key="button.addNewCategory"/></button>
</form>
</body>
</html>
