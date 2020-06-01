<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
</head>
<body>


	<div id="container">
		<table>
			<thead>
				<tr>
					<td>No.</td><td>아이디</td><td>이름</td><td>상태</td><td>입사일</td>
				</tr>
			</thead>
			<tbody>
			
				<tr>
					<td>${dto.employee_number}</td><td>${dto.id}</td><td>${dto.name}</td>
					
						<td>${dto.state}</td>
					
					<td>${dto.join_date}</td>
				</tr>
				
			</tbody>
		</table>
	</div>
	
	
</body>
</html>