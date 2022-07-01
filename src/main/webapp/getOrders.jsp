<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <c:forEach var="order" items="${requestScope.orders}">
        <h3><fmt:message key="label.orderNumber"/> ${order.getId()}</h3>
        <h4><fmt:message key="label.orderStatus"/> ${order.getStatus().getName()}</h4>
        <h4><fmt:message key="label.orderDate"/> ${order.getDateStart()}</h4>
        <h4><fmt:message key="label.orderDateFinish"/> ${order.getDateFinish()}</h4>
        <c:if test="${sessionScope.isAdmin == true}">
            <form action="/changeOrderStatus" method="post">
                <select name="statusId" required>
                    <label><fmt:message key="label.changeOrderStatus"/></label>
                    <c:forEach var="status" items="${requestScope.statusList}">
                        <option value="${status.getId()}">${status.getName()}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="orderId" value="${order.getId()}"/>
                <button type="submit"><fmt:message key="button.edit"/></button>
            </form>
        </c:if>
        <c:forEach var="orderDetail" items="${order.getOrderDetails()}">
            <p><fmt:message key="label.productName"/> ${orderDetail.getProduct().getName()}</p>
            <p><fmt:message key="label.productCount"/> ${orderDetail.getCount()}</p>
            <p><fmt:message key="label.productCost"/> ${orderDetail.getCost()}</p>
        </c:forEach>
        <h4><fmt:message key="label.totalCost"/> ${order.getTotalCost()}</h4>
    </c:forEach>
</body>
</html>
