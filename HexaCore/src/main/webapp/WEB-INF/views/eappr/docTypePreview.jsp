<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	${dto}
	<form action="./goDocWriteForm.do" method="POST">
		<input type="hidden" name="type_seq" value="${dto.type_seq}">
		<input type="hidden" name="name" value="${dto.name}">
		<input type="hidden" name="content" value="${dto.content}">
		<input type="submit" value="선택완료">
	</form>
</body>
</html>