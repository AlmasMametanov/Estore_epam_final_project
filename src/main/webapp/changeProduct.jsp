<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<form action="changeProduct" method="post">
    <label><fmt:message key="label.changeProductName"/></label>
    <input type="text" placeholder="${product.name}" name="productName" required><br/>
    <label><fmt:message key="label.changeProductDescription"/></label>
    <textarea name="description" placeholder="${product.description}" required></textarea><br/>
    <label><fmt:message key="label.changeProductCost"/></label>
    <input type="number" name="cost" placeholder="${product.cost}" required><br/>
    <label><fmt:message key="label.changeProductCount"/></label>
    <input type="number" name="count" placeholder="${product.count}" required><br/>

    <c:if test="${requestScope.countries != null}">
        <select name="countryId" required>
            <label><fmt:message key="label.changeCountry"/></label>
            <c:forEach var="country" items="${requestScope.countries}">
                <option value="${country.getId()}">${country.getName()}</option>
            </c:forEach>
        </select><br/>
    </c:if>
    <c:if test="${requestScope.categories != null}">
        <select name="categoryId" required>
            <label><fmt:message key="label.changeCategory"/></label>
            <c:forEach var="category" items="${requestScope.categories}">
                <c:set var="category" value="${category}" scope="request"/>
                <jsp:include page="getSubcategoriesForAddProduct.jsp"/>
            </c:forEach>
        </select><br/>
    </c:if>
    <input type="hidden" name="productId" value="${product.getId()}">
    <button type="submit"><fmt:message key="button.edit"/></button>
</form>
</html>
