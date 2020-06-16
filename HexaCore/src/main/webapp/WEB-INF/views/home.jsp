<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./css/home.css">
</head>
<body>
	<div class="wrap">
		<%@include file="./header.jsp"%>
		<div class="container">
			<div class="content">
				<div class="container_div1" id="item">
					<p>내용</p>
				<!-- 권한 -->
				<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth">${auth}</sec:authorize>
				<!-- username : id  -->
				<sec:authentication property="principal.username" />
				<!-- name : 사원이름 -->
				<sec:authentication property="principal.name" />
				<!-- department_name : 부서이름 -->
				<sec:authentication property="principal.department_name" />
				<!-- e_rank_name : 사원직위 -->
				<sec:authentication property="principal.e_rank_name" />
				</div>
				<div class="container_div2" id="item">
					<p>내용</p>
					<a href="./employeeList.do">유저 관리</a><br>
					<a href="./updateDepartment.do">부서 관리</a><br>
					<a href="./totalIndex.do">인덱싱</a><br>
				</div>
				<div class="container_div3" id="item">
					<div id="Notice_Title_Header">
						<h3 id="Title_h">공지사항</h3> 
					</div>
					<div id="Notice_Title_Header">
						<a href="./goBbs.do?category=1" id="Title_a">
							more
						</a>
					</div>
					<hr>
				</div>
				<div class="container_div4" id="item">
					<div id="Board_Title_Header">
						<h3 id="Title_h">자료실</h3> 
					</div>
					<div id="Board_Title_Header">
						<a href="./goBbs.do?category=2" id="Title_a">
							more
						</a>
					</div>
					<hr>
				</div>
				<div class="container_div5" id="item">
					<p>내용</p>
				</div>
				<div class="container_div6" id="item">
					<p>내용</p>
				</div>
			</div>
		</div>
	</div>
	<%@include file="./footer.jsp"%>
</body>
</html>