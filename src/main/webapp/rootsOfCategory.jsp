<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<c:if test="${requestScope.categories != null}">
    <ul>
        <c:forEach var="category" items="${requestScope.categories}">
            <li>
                <c:set var="category" value="${category}" scope="request"/>
                <jsp:include page="subcategoriesOfRootCategory.jsp"/>
            </li>
        </c:forEach>
    </ul>
</c:if>




