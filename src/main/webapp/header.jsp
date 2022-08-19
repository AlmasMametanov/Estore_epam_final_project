<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<h3>${requestScope.productMoreThanZero}</h3>
<h3>${requestScope.productEqualZero}</h3>

<form action="changeLocale" method="post">
        <input type="hidden" name="selectedLocale" value="RU" />
        <button>RU</button>
    </form>
    <form action="changeLocale" method="post">
        <input type="hidden" name="selectedLocale" value="EN" />
        <button>EN</button>
    </form>
<c:if test="${sessionScope.email == null}">
    <form action="login.jsp">
        <button><fmt:message key="button.login"/></button>
    </form>
    <form action="signup.jsp">
        <button><fmt:message key="button.signup"/></button>
    </form>
</c:if>
<c:if test="${sessionScope.email != null}">
    <jsp:include page="logout.jsp"/>
    <form action="userProfile.jsp">
        <button><fmt:message key="button.userProfile"/></button>
    </form>
</c:if>

<c:if test="${sessionScope.isAdmin == true}">
    <form action="adminPanel.jsp">
        <button><fmt:message key="button.adminPanel"/></button>
    </form>
</c:if>
<p>${language}</p>
<c:if test="${sessionScope.isAdmin == false}">
    <form action="getAllProductsFromBasket" method="get">
        <button><fmt:message key="button.getBasket"/></button>
    </form>
    <form action="getOrders" method="get">
        <button type="submit"><fmt:message key="button.getOrders"/></button>
    </form>
</c:if>

<form action="searchProduct" method="get">
    <input name="productName" type="text" placeholder="<fmt:message key="button.search"/>..."/>
    <button type="submit">
        <fmt:message key="button.search"/>
    </button>
</form>

<form action="categories" id="categories" method="get">
    <input type="hidden" name="localeShortName" value="${sessionScope.language}">
    <button type="submit" class="dropbtn" form="categories">
        <fmt:message key="button.menuCategory"/>
    </button><br>
    <jsp:include page="rootsOfCategory.jsp"/>
</form>
</html>


