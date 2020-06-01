<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<div id="container">
	${Ddto}
<table id="table" class="table">
		
		<tr><th>문서번호</th><td>${Ddto.seq}</td></tr>
		<tr><th>아이디</th><td>${Ddto.author}</td></tr>
		<tr><th>제목</th><td>${Ddto.title}</td></tr>
		<tr><th>내용</th><td>${Ddto.content}</td></tr>
</table>
	<!--javascript를 통한 form 전송  -->
	<div style="text-align: center; margin-top: 10px; margin-bottom: 100px;">
	<form name="form1" id="form1">
		<input  type="button" name="btn" id="modifyDoc" value="수정" onclick="modifyForm(1)">
		<input  type="button" name="btn" id="deleteBoard" value="삭제">
	</form>
	</div>
</div>
</html>