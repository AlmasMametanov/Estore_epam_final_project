<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>


<c:forEach var="subcategory" items="${category.getSubcategories()}">
    <c:if test="${subcategory.getSubcategories().size() == 0}">
        <option value="${subcategory.getCategoryId()}">${subcategory.getName()}</option>

        <c:set var="category" value="${subcategory}" scope="request"/>
        <jsp:include page="getSubcategoriesForAddProduct.jsp"/>
    </c:if>
    <c:if test="${subcategory.getSubcategories().size() != 0}">
        <c:set var="category" value="${subcategory}" scope="request"/>
        <jsp:include page="getSubcategoriesForAddProduct.jsp"/>
    </c:if>
</c:forEach>
