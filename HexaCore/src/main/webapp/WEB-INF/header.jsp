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
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>TopMenu</title>
<style type="text/css">
	.navbar{
		margin-bottom: 0px;
	}
</style>
</head>
<script type="text/javascript">
function empPop() {
	var treeWindow = window.open("./empInfo.do", "사원 정보", "width=1024,height=760");
}
</script>
<body>
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Logo</a>
			</div>
<!-- 			<div class="collapse navbar-collapse" id="myNavbar"> -->
				<ul class="nav navbar-nav">
					<li class="active"><a href="./result.do">Home</a></li>
					<c:if test="${auth eq true }">
					<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">관리<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="./employeeList.do">유저 관리</a></li>
							<li><a href="./updateDepartment.do">부서 관리</a></li>
						</ul>
					</li>
					<li><a href="./totalIndex.do">인덱싱</a></li>
					</c:if>
				</ul>
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
				<ul class="nav navbar-nav navbar-right">
					<li><a href="./logout"><span
							class="glyphicon glyphicon-log-out"></span>logout</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a onclick="empPop()"><span
							class="glyphicon glyphicon-user"></span> My Account</a></li>
				</ul>
<!-- 			</div> -->
		</div>
	</nav>

</body>
</html>