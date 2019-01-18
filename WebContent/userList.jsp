<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of users</title>
</head>
<body>
<a href="/insert">Add new User</a>
<a href="/list">get list</a>
<c:if test="${info} != null">
	<h2>${info}</h2>
</c:if>
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
		</tr>

		<c:forEach var="item" items="${myList}">
		<c:url var="deleteLink" value="/delete">
			<c:param name="itemId" value="${item.id}" />
		</c:url>
		<c:url var="updateLink" value="/update">
			<c:param name="itemId" value="${item.id}" />
		</c:url>
		<c:url var="sendEmail" value="/email">
			<c:param name="itemId" value="${item.id}" />
		</c:url>
			<tr>
				<td>${item.firstName}</td>
				<td>${item.lastName}</td>
				<td>${item.email}</td>
				<td>
				<a href="${updateLink}">Update</a>
				<a href="${deleteLink}">Delete</a>
				<a href="${sendEmail}">Send email</a></td>
			</tr>
			
		</c:forEach>
	</table>

</body>
</html>