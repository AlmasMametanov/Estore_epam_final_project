<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <c:if test="${requestScope.products != null}">
        <h3><fmt:message key="label.products"/></h3>
        <c:forEach var="product" items="${requestScope.products}">
            <p><fmt:message key="label.productName"/> ${product.name}</p>
            <p><fmt:message key="label.productDescription"/> ${product.description}</p>
            <p><fmt:message key="label.productCost"/> ${product.cost}</p>
            <p><fmt:message key="label.productCount"/> ${product.count}</p>
            <p><fmt:message key="label.countryName"/> ${product.countryName}</p>
            <p><fmt:message key="label.categoryName"/> ${product.categoryName}</p>
            <c:if test="${isAdmin == true}">
                <form action="deleteProduct" method="post">
                    <button value="${product.id}" name="productId"><fmt:message key="button.delete"/></button>
                </form>
                <form action="changeProduct" method="post">
                    <input type="hidden" name="productId" value="${product.getId()}" />
                    <button><fmt:message key="button.editProduct"/></button>
                </form>
            </c:if>
            <c:if test="${isAdmin == false}">
                <form action="addProductInBasket" method="post">
                    <input type="number" name="count" value="${product.count}"/>
                    <button name="productId" value="${product.id}"><fmt:message key="button.addToBasket"/></button>
                </form>
            </c:if>
        </c:forEach>
    </c:if>
</body>
</html>
