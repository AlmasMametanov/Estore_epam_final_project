<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <c:if test="${sessionScope.login != null}">
        <h3><fmt:message key="label.userProfile"/></h3>
        <p><fmt:message key="label.login"/>: ${login}</p>
        <p><fmt:message key="label.firstName"/>: ${firstName}</p>
        <p><fmt:message key="label.lastName"/>: ${lastName}</p>
        <p><fmt:message key="label.birthday"/>: ${birthday}</p>
        <p><fmt:message key="label.phoneNumber"/>: ${phoneNumber}</p>
        <p><fmt:message key="label.address"/>: ${address}</p>
        <jsp:include page="logout.jsp"/>
        <form action="index.jsp">
            <button><fmt:message key="button.mainPage"/></button>
        </form>
    </c:if>
</body>
</html>
