<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<option value="${category.getCategoryId()}">
    ${category.getName()}
</option>
<c:forEach var="subcategory" items="${category.getSubcategories()}">
    <c:set var="category" value="${subcategory}" scope="request"/>
    <jsp:include page="getCategoriesForAddCategory.jsp"/>
</c:forEach>

