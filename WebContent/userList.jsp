<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url">${req.requestURL}</c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>List of users</title>
<base
	href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />
<link rel="stylesheet" href="styles.css" />
<body>

	<script>
		function showHideLiks() {
			var x = document.getElementsByClassName("showSingleEmail");
			var y = document.getElementById("someInput");
			var z = document.getElementsByClassName("container");
			if (x[0].style.display === "none") {
				y.style.display = "none";
				for (var i = 0; i < x.length; i++) {
					x[i].style.display = "inline";
					z[i].style.display = "none";
					document.getElementById("someLink").innerHTML = "Send email to multiple users";
				}
			} else {
				y.style.display = "inline";
				for (var i = 0; i < x.length; i++) {
					x[i].style.display = "none";
					z[i].style.display = "inline-block";
					document.getElementById("someLink").innerHTML = "Send email to single user";
				}
			}
		}
	</script>

	<div class="menu">
		<a class="topLink" href="/servlet/insert">Add new User</a> <a
			class="topLink" href="/servlet/list">get list</a> <a class="topLink"
			id="someLink" onclick="showHideLiks()">Send email to multiple
			users</a>
			
	</div>
	<c:if test="${info} != null">
		<h2>${info}</h2>
	</c:if>
	<form action="email" method="post">
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Action</th>
			</tr>

			<c:forEach var="item" items="${myList}">
				<c:url var="deleteLink" value="/servlet/delete">
					<c:param name="itemId" value="${item.id}" />
				</c:url>
				<c:url var="updateLink" value="/servlet/update">
					<c:param name="itemId" value="${item.id}" />
				</c:url>

				<c:url var="sendEmail" value="/emailSender/emailForm">
					<c:param name="itemId" value="${item.id}" />
				</c:url>

				<tr>
					<td>${item.firstName}</td>
					<td>${item.lastName}</td>
					<td>${item.email}</td>
					<td><a href="${updateLink}">Update</a> <a href="${deleteLink}">Delete</a>
						<a href="${sendEmail}" class="showSingleEmail">Send email</a> <label
						class="container"> <input type="checkbox" name="itemId"
							value="${item.id}"> <span class="checkmark"></span></label></td>
				</tr>

			</c:forEach>
		</table>
		<input id="someInput" type="submit" value="Send">
	</form>
</body>
</html>