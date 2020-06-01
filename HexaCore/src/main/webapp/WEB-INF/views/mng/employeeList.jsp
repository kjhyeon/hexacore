<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<div id="container">
		<table>
			<thead>
				<tr>
					<td>No.</td><td>아이디</td><td>이름</td><td>상태</td><td>입사일</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.employee_number}</td>
						<td>
							<a href="./updateEmployee.do?employee_number=${dto.employee_number }">${dto.id}</a>
						</td>
						<td>${dto.name}</td>
						<td><c:choose>
								<c:when test="${dto.state eq -1}">퇴사</c:when>
								<c:when test="${dto.state eq 0}">재직</c:when>
								<c:when test="${dto.state eq 1}">휴가</c:when>
							</c:choose></td>
						<td>${dto.join_date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<%@include file="./../../footer.jsp" %>
</body>
</html>