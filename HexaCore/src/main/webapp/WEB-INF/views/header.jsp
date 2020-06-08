<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="./css/common.css">
	<title>Home</title>
</head>
<script type="text/javascript">
function empPop() {
	var treeWindow = window.open("./empInfo.do", "사원 정보", "width=1024,height=760");
}
</script>
<body>
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
	<header>
		<div class="topmenu" style="font-size:0.5em"><a href="./result.do"><img alt="logo" src="./image/hexa64.png"></a></div>
		<div class="topmenu" onclick="location.href='./goEapprHome.do'">전자결재</div>
		<div class="topmenu" onclick="location.href=''">게시판</div>
		<div class="topmenu" onclick="location.href='./employeeList.do'">관리</div>
		<div class="topmenu">
			<form class="navbar-form navbar-right" role="search" action="./totalSearch.do" method="get">
				<div class="form-group input-group">
					<span class="input-group-btn">
					<select name="type" class="form-control" >
						<option value="title/con">제목+내용</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="author">글쓴이</option>
					</select>
					</span>
					<input type="text" class="form-control" placeholder="Search.." name="keyword" style="position: relative; float: right;">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</form>
		</div>
		<div class="topmenu" onclick="empPop()">My Page</div>
		<div class="topmenu" onclick="location.href='./logout'">logout</div>
	</header>
</body>
</html>