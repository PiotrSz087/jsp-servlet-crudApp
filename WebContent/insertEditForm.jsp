<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Update here</title>
</head>
<body>
	 <c:if test="${userFromDb != null}">
            <form action="updateProcessing" method="get">
        </c:if>
        <c:if test="${userFromDb == null}">
            <form action="addProcessing" method="get">
          </c:if>
		<c:if test="${userFromDb != null}">
			<input type="hidden" name="userId" value="<c:out value='${userFromDb.id}' />"/> 
		</c:if>
			<input type="text" name="firstName" value="<c:out value='${userFromDb.firstName}' />"/> 
			<input type="text" name="lastName" value="<c:out value='${userFromDb.lastName}' />"/> 
			<input type="text" name="email" value="<c:out value='${userFromDb.email}' />"/> 
			<input type="submit" value="Save"/>
	</form>
</body>
</html>