<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Survey</title>
</head>
<body>
<% String login = (String)session.getAttribute("user_login"); %>

<% if (login == null || "".equals(login)) {
    response.sendRedirect("login.jsp");
} %>
<h1>What programming language would you like to learn?</h1>
<form method="post" action="/statistics" >
    <input type="radio" name="language" value="java"/>Java
    <input type="radio" name="language" value="cplusplus"/>C++
    <input type="submit" value="Submit"/>
</form>

<h1>Statistics</h1>
<p>Current user: </p>

<c:if test="${not empty sessionCounter}">
<c:forEach  items="${sessionCounter}" var="entry">
    <p>Key: <c:out value="${entry.key}"/></p>
    <p>Value: <c:out value="${entry.value}"/></p>
</c:forEach>
</c:if>

<p>All users: </p>
<c:if test="${not empty generalCounter}">
<c:forEach  items="${generalCounter}" var="entry2">
    <p>Key: <c:out value="${entry2.key}"/></p>
    <p>Value: <c:out value="${entry2.value}"/></p>
</c:forEach>
</c:if>
<br>
<h1>You are logged in as: <%= login %></h1>
<br>Click this link to <a href="/login?a=exit">logout</a>
</body>
</html>
