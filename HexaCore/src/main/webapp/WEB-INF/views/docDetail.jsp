<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TEST용</title>
<link type="tex/css" rel="stylesheet" href="./css/bootstrap-theme.css">
<link type="tex/css" rel="stylesheet" href="./css/bootstrap.min.css">

<script type="text/javascript" src="./js/eAppr_js.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/bootstrap-theme.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>

</head>
<body>
<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal.username" var="name"/>
<div id="container">
<table id="table" class="table">
		<tr><th>문서번호</th><td>${Ddto.seq}</td></tr>
		<tr><th>성함</th><td>${name}</td></tr>
		<tr><th>제목</th><td>${Ddto.title}</td></tr>
		<tr><th>내용</th><td>${Ddto.content}</td></tr>
</table>

<c:if test="${empty comment}">
</c:if>
<table id="commentTable" class="commentTable">
<c:forEach var="Cdto" items="${comment}">
		<tr><th>No.</th><th>성함</th><th>코멘트</th><th>결재일</th></tr>
		<tr><td>${Cdto.name}</td><td>${Cdto.content}</td><td>${Cdto.regdate}</td><td>${Cdto.comment_seq}</td></tr>
</c:forEach>
</table>
	<!--javascript를 통한 form 전송  -->
	<div style="text-align: center; margin-top: 10px; margin-bottom: 100px;">
	<form name="formDoc" id="formDoc">
		<input  type="button" name="btn" id="modifyDoc" value="수정">
		<input  type="button" name="btn" id="deleteDoc" value="삭제">
		<input  type="button" name="btn" id="apprDoc" value="상신">
		
	</form>
	</div>
</div>
	<script type="text/javascript">
		var btn = document.getElementsByName('btn');
		var form = document.getElementById('formDoc');
		onload = function() {
			for (var i = 0; i < btn.length; i++) {
				btn[i].onclick = function() {
					form.action = './'+this.id+'.do?seq='+${Ddto.seq};
					form.method = 'post';
					form.submit();
				}
			}
		}
	</script>
	</sec:authorize>
	</body>
</html>