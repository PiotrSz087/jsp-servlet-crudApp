<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Send email here</title>
</head>
<body>
		<form action="/emailSender/emailProceed" method="post">
			<input type="hidden" name="id" value="<c:out value='${ids}' />"/>
			<input type="text" name="subject" value="Enter subject here" /><br>
			<textarea rows="5" cols="20" name="message">Enter text message here...</textarea>
			<br> <input type="submit" value="Send">
		</form>
	
</body>
</html>