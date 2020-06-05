<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TEST용</title>
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
</head>
<body>
<%@include file="./../../header.jsp" %>
<div id="container">
<table id="table" class="table">
		<tr><th>문서번호</th><td>${Ddto.seq}</td></tr>
		<tr><th>기안자</th><td>${Ddto.author}</td></tr>
		<tr><th>제목</th><td>${Ddto.title}</td></tr>
		<tr><th>내용</th><td>${Ddto.content}</td></tr>
		<tr><th>기안일</th><td>${Ddto.regdate}</td></tr>
</table>

<c:if test="${empty comment}">
</c:if>
<table id="commentTable" class="commentTable">
<c:forEach var="Cdto" items="${comment}">
		<tr><th>No.</th><th>성함</th><th>코멘트</th><th>결재일</th></tr>
		<tr><td>${Cdto.comment_seq}</td><td>${Cdto.name}</td><td>${Cdto.content}</td><td>${Cdto.regdate}</td></tr>
</c:forEach>
</table>
	<div>
	
	<form name="formDoc" id="formDoc">
	<c:if test="${name eq Ddto.author && Ddto.state eq '0' || '1' }">
		<input  type="button" id="modifyDoc" value="수정" onclick="modifyFormDoc('${Ddto.seq}')">
		<input  type="button" id="upApprDoc" value="상신" onclick="upApprDoc()">
		<input  type="button" id="deleteDoc" value="삭제" onclick="deleteDocc(${Ddto.seq})">
	</c:if>
	<c:if test="${name ne Ddto.author && turn != '0' && turn eq Ddto.appr_turn}">
		<input  type="button" value="결재" data-toggle="modal" data-target="#apprDoc" data-backdrop='static' data-keyboard='false' onclick="apprDoc('${Ddto.seq}','${Ddto.appr_turn}')">
	</c:if>
	</form>
	</div>
</div>
	<div id="apprDoc" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">결재</h4>
		      </div>
		      <div class="modal-body">
		      <!-- ajax를 통해서 수정하고 넘길 데이터를 표출해줌 -->
		      <form id="apprDocUp"></form>
		      </div>
		    </div>
		 </div>
	</div>
	</body>
</html>