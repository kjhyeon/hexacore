<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="./logingo.do" method="post">
		아이디:<input type="text" name="username">
		비밀번호:<input type="text" name="password">
		<input name ="remember-me" type = "checkbox">
		<input type="submit" value="제출">
	</form>
</body>
</html>