<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<style>
    .form {
        display: inline-block;
    }
</style>

<c:if test="${requestScope.categories != null}">
    <form class="form" action="getProductsByCategoryId" method="get">
        <button type="submit" class="dropbtn" name="parentId" value="${category.getCategoryId()}">
            <span>${category.getName()}</span>
        </button>
    </form>
    <c:if test="${isAdmin == true}">
        <form class="form" action="deleteCategory" method="post">
            <button type="submit" value="${category.getCategoryId()}" name="categoryId"><fmt:message key="button.delete"/></button>
        </form>
    </c:if>
    <ul>
        <c:forEach var="subcategory" items="${category.getSubcategories()}">
            <li>
                <c:set var="category" value="${subcategory}" scope="request"/>
                <jsp:include page="subcategoriesOfRootCategory.jsp"/>
            </li>
        </c:forEach>
    </ul>
</c:if>

