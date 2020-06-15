<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 목록</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
	#container{
		margin-top: 100px;
	}
	
	#btns{
		text-align: right;
	}
</style>
<script type="text/javascript">
function gotoCreate(){
	location.href="./insertEmployee.do";
}
document.onkeydown = function(e){
	/* F5, Ctrl+r, Ctrl+F5 */
	    if(e.keyCode == 116 || e.ctrlKey == true && (e.keyCode == 82)){
	    	location.reload(location.href);
	 		   return false;
	    }
 }
</script>
</head>
<body>
	<div id="container">
	<h1 style="padding-left: 50px;">유저 리스트</h1>
	<hr>
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
		<input type="hidden" name="index" id="index" value="${row.index }">
		<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum }">
		<input type="hidden" name="listNum" id="listNum" value="${row.listNum }">
		<div id="btns">
			<input type="button" value="생성" onclick="gotoCreate()" class="btn">
		</div>
		<div class="center" style="text-align: center; position: relative;">
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
	
</body>
</html>