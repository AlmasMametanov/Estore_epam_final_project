<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <h2><fmt:message key="label.totalCost"/> ${requestScope.totalCost}</h2>
    <c:forEach var="product" items="${requestScope.productsFromBasket}">
        <fmt:message key="label.productName"/>${product.name}<br/>
        <fmt:message key="label.productCount"/>${product.count}<br/>
        <fmt:message key="label.productCost"/>${product.cost}<br/>
    </c:forEach>
    <form action="confirmOrder" method="post">
        <button name="totalCost" value="${requestScope.totalCost}"><fmt:message key="button.confirmOrder"/></button>
    </form>
</body>
</html>
