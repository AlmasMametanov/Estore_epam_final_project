<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <table>
        <tr>
            <th><fmt:message key="label.userId"/></th>
            <th><fmt:message key="label.login"/></th>
            <th><fmt:message key="label.firstName"/></th>
            <th><fmt:message key="label.lastName"/></th>
            <th><fmt:message key="label.birthday"/></th>
            <th><fmt:message key="label.phoneNumber"/></th>
            <th><fmt:message key="label.address"/></th>
            <th><fmt:message key="label.isAdmin"/></th>
            <th><fmt:message key="label.isBanned"/></th>
        </tr>
        <tr>
            <td>${user.getId()}</td>
            <td>${user.getLogin()}</td>
            <td>${user.getFirstName()}</td>
            <td>${user.getLastName()}</td>
            <td>${user.getBirthday()}</td>
            <td>${user.getPhoneNumber()}</td>
            <td>${user.getAddress()}</td>
            <td>${user.getIsAdmin()}</td>
            <td>${user.getIsBanned()}</td>
        </tr>
    </table>
    <form action="changeUserBanStatus" method="post">
        <input type="hidden" name="userId" value="${user.getId()}">
        <c:if test="${requestScope.user.isBanned == false}">
            <input type="submit" value=<fmt:message key="button.banUser"/>>
        </c:if>
        <c:if test="${requestScope.user.isBanned == true}">
            <input type="submit" value=<fmt:message key="button.unbanUser"/>>
        </c:if>
    </form>
    <form action="getOrders" method="get">
        <button name="userId" value="${user.getId()}"><fmt:message key="button.getOrders"/></button>
    </form>
</body>
</html>
