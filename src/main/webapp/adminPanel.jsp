<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<body>
    <h2><fmt:message key="label.adminPanel"/></h2>
    <jsp:include page="header.jsp"/>

    <form action="getAllUsers" method="get">
        <button><fmt:message key="button.getAllUsers"/></button>
    </form>

    <form action="getUser" method="get">
        <input name="userId" type="text" placeholder="<fmt:message key="label.enterUserId"/>..."/>
        <button><fmt:message key="button.getUser"/></button>
    </form>

    <form action="addNewCategory" method="post">
        <button><fmt:message key="button.addNewCategory"/></button>
    </form>

    <form action="addNewProduct" method="post">
        <button><fmt:message key="button.addNewProduct"/></button>
    </form>
</body>
</html>
