<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<form action="/addNewProduct" method="post">
    <input type="text" name="productName" placeholder="<fmt:message key="label.enterProductName"/>" required><br/>
    <textarea name="description" placeholder="<fmt:message key="label.enterProductDescription"/>" required></textarea><br/>
    <input type="number" name="cost" placeholder="<fmt:message key="label.enterProductCost"/>" required><br/>
    <input type="number" name="count" placeholder="<fmt:message key="label.enterProductCount"/>" required><br/>

    <c:if test="${requestScope.countries != null}">
        <select name="countryId" required>--%>
            <label><fmt:message key="label.chooseCountryName"/></label>
            <c:forEach var="country" items="${requestScope.countries}">
                <option value="${country.getId()}">${country.getName()}</option>
            </c:forEach>
        </select><br/>
    </c:if>

    <c:if test="${requestScope.categories != null}">
        <select name="categoryId" required>
            <label><fmt:message key="label.chooseParentCategory"/></label>
            <c:forEach var="category" items="${requestScope.categories}">
                <c:set var="category" value="${category}" scope="request"/>
                    <jsp:include page="getSubcategoriesForAddProduct.jsp"/>
            </c:forEach>
        </select><br/>
    </c:if>

    <button type="submit"><fmt:message key="button.addNewProduct"/></button>
</form>
</html>
