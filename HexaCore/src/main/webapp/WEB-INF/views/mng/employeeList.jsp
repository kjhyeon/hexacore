<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
<script type="text/javascript">
function gotoCreate(){
	location.href="./insertEmployee.do";
}
</script>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<div id="container">
	<table class="table">
			<thead>
				<tr>
					<td>No.</td><td>아이디</td><td>이름</td><td>부서</td><td>직위</td><td>상태</td><td>권한</td><td>입사일</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.employee_number}</td>
						<td>
							<a href="./updateEmployee.do?id=${dto.id }">${dto.id}</a>
						</td>
						<td>${dto.name}</td>
						<td>${dto.department_name}</td>
						<td>${dto.e_rank_name}</td>
						<td>
							<c:choose>
								<c:when test="${dto.state eq -1}">퇴사</c:when>
								<c:when test="${dto.state eq 0}">재직</c:when>
								<c:when test="${dto.state eq 1}">휴가</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${dto.auth eq 'A'}">관리자</c:when>
								<c:when test="${dto.auth eq 'U'}">일반</c:when>
							</c:choose>
						</td>
						<td>${dto.join_date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="button" value="생성" onclick="gotoCreate()" class="btn" style="text-align : right;">
		<input type="hidden" name="index" id="index" value="${row.index }">
		<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum }">
		<input type="hidden" name="listNum" id="listNum" value="${row.listNum }">
		<div class="center" style="text-align: center;">
			<ul class="pagination">
				<li><a href="./employeeList.do?page=0" onclick="pageFirst()">&laquo;</a></li>
				<li><a href="./employeeList.do?page=${row.index-1}" onclick="pagePre()">&lt;</a></li>
				<c:forEach var="i" begin="${row.pageNum }" end="${row.count }" step="1">
					<li><a href="./employeeList.do?page=${i-1}">${i }</a></li>
				</c:forEach>
				<li><a href="./employeeList.do?page=${row.index+1}" >&gt;</a></li>
				<li><a href="./employeeList.do?page=${row.lastPage-1}" >&raquo;</a></li>
			</ul>
		</div>
		
	</div>
	
	<%@include file="./../../footer.jsp" %>
</body>
</html>