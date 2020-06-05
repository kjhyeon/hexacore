<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/css_login.css">
</head>
<body>
	<img style="width: 100%; height:100%;" alt="" src="./image/Hexa_Core_login.png">
	<div id="login">
	<form action="./logingo.do" method="post">
		<table id="login_table">
				<tr>
					<td>아이디</td>
					<td>
						<input class="form-control" type="text" name="username">
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input class="form-control" type="password" name="password">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input class="btn" name ="remember-me" type = "checkbox">
						<input class="btn" type="submit" value="로그인">
					</td>
				</tr>
			</table>
	</form>
	</div>
</body>
</html>