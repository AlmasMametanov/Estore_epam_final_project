<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <c:if test="${sessionScope.email == null}">
        <form action="login" method="post">
            <div class="field">
                <label for="email"><fmt:message key="label.email"/></label>
                <input type="text" placeholder="<fmt:message key="label.enterEmail"/>" name="email" id="email" required/>
            </div>
            <div class="field">
                <label for="psw"><fmt:message key="label.password"/></label>
                <input type="password" placeholder="<fmt:message key="label.enterPassword"/>" name="password" id="psw" required/>
            </div>
            <div class="field_button">
                <button type="submit"><fmt:message key="button.login"/></button>
            </div>
        </form>
    </c:if>
</body>
</html>
