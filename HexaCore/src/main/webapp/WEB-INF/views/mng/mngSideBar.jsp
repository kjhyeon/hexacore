<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<div class="wrap">
		<div class="sidebar">
			<div class="menu01">유저 관리</div>
			<div class="menu">부서 관리</div>
		</div>
	</div>
	<div>
		<iframe src="./employeeList.do"></iframe>
	</div>
</body>
</html>