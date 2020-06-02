<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<base href="./css"> 
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
						<input type="text" name="username">
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="password">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input name ="remember-me" type = "checkbox">
						<input type="submit" value="로그인">
					</td>
				</tr>
			</table>
	</form>
	</div>
</body>
</html>