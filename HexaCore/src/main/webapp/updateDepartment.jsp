<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
		<div id="left-content">
			<iframe src="./deptMngTree.do"></iframe>
		</div>
		<div id="right-content">
			<span>수정영역</span>
			<table>
				<tr>
					<th>부서 이름</th><td><input type="text" id="department_name" name="name"></td>
				</tr>
				<tr>
					<th>팩스 번호</th><td><input type="text" id="department_fax" name="faxnum"></td>
				</tr>
				<tr>
					<th>전화 번호</th><td><input type="text" id="department_phone" name="d_phone"></td>
				</tr>
			</table>
		</div>
	<div>
</div>
</body>
</html>