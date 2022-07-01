<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <c:forEach var="product" items="${requestScope.productsFromBasketByUserId}">
        <fmt:message key="label.productName"/> ${product.name}<br/>
        <fmt:message key="label.productCount"/> ${product.count}<br/>
        <form action="removeProductFromBasket" method="post">
            <button type="submit" name="productId" value="${product.id}"><fmt:message key="button.deleteProductFromBasket"/></button>
        </form>
    </c:forEach>
    <c:if test="${requestScope.productsFromBasketByUserId.size() != 0}">
        <form action="createOrder" method="post">
            <button type="submit"><fmt:message key="button.createOrder"/></button>
        </form>
    </c:if>
</body>
</html>
