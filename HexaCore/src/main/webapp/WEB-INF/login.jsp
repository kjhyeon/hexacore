<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/css_login.css">
</head>
<body>
	<!-- 	<img alt="logo" src="./image/hexa512.png"> -->
	<!-- 	<h1>Hexa-core Groupware</h1> -->
	<!-- 	<div id="login"> -->
	<!-- 		<form action="./logingo.do" method="post"> -->
	<!-- 			<input class="id" type="text" name="username" placeholder="USERNAME"><br> -->
	<!-- 			<input class="pw" type="password" name="password" placeholder="PASSWORD"><br><br> -->
	<!-- 			<input class="btn" type="submit" value="Log In"><br><br> -->
	<!-- 			<p><input class="chkbox" name ="remember-me" type = "checkbox"> 로그인 유지</p> -->
	<!-- 		</form> -->
	<!-- 	</div> -->
	<div class="login-box">
		<h2>Login</h2>
		<form action="./logingo.do" method="post">
			<div class="user-box">
				<input type="text" name="username" required="required"> <label>Username</label>
			</div>
			<div class="user-box">
				<input type="password" name="password" required="required"> <label>Password</label>
			</div>
			<a> <span></span> <span></span> <span></span> <span></span>
				<input id="hisub" type="submit" value="LOGIN">
			</a>
			<p><input class="chkbox" name ="remember-me" type = "checkbox"> Remember me</p>
		</form>
	</div>
	<script type="text/javascript" defer="defer">
	$(document).ready(function() {
		<c:if test="${not empty msg}">
			alert("${msg}");
		</c:if>
	});
</script>
</body>
</html>