<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <form action="signup" method="post">
        <label for="login"><fmt:message key="label.login"/></label>
        <input type="text" name="login" id="login" required/><br>

        <label for="firstName"><fmt:message key="label.firstName"/></label>
        <input type="text" name="firstName" id="firstName" required/><br>

        <label for="lastName"><fmt:message key="label.lastName"/></label>
        <input type="text" name="lastName" id="lastName" required/><br>

        <label for="birthday"><fmt:message key="label.birthday"/></label>
        <input type="date" name="birthday" id="birthday" required/><br>

        <c:if test="${requestScope.phoneNumberFormatIncorrect != null}">
            <fmt:message key="error.phoneNumber"/><br/>
        </c:if>
        <label for="phoneNumber"><fmt:message key="label.phoneNumber"/></label>
        <input type="text" name="phoneNumber" id="phoneNumber" required/><br>

        <label for="address"><fmt:message key="label.address"/></label>
        <input type="text" name="address" id="address" required/><br>

        <c:if test="${requestScope.passwordFormatIncorrect != null}">
            <fmt:message key="error.password"/><br/>
        </c:if>
        <label for="psw"><fmt:message key="label.password"/></label>
        <input type="password" name="password" id="psw" required/><br>

        <button type="submit"><fmt:message key="button.signup"/></button>
    </form>
</body>
</html>
