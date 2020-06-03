<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
</head>
<body>

<div id="container">
<section id="intro">
	<div class="page-title">
		<h1>결재문서함</h1>
	</div>
	<div class="page-middle">
		<input type="text" id="text_in" placeholder="검색어를 입력해주세요">
		<input type="button" id="search" value="검색"/>
	</div>
</section>
<form action="#"  method="post">
			
	<table>
		<tr>
			<th>문서번호</th>
			<th>기안자</th>
			<th>제목</th>
			<th>내용</th>
			<th>기안일</th>
			<th>결재</th>
		</tr>
			 <c:if test="${empty lists}">
			 	<tr>
			 		<th colspan="5">결재 하실 문서가 없습니다.</th>
			 	</tr>
			 </c:if>
			 
			 <c:forEach var="dto" items="${lists}">
			 		<tr>
			 			<td>${dto.seq}</td>
			 			<td>${dto.author}</td>
			 			<td>${dto.title}</td>
			 			<td><a href="./docDetail.do?seq=${dto.seq}">${dto.content}</a></td>
			 			<td>${dto.regdate}</td>
			 			<td>
			 				<input  type="button" value="결재" data-toggle="modal" data-target="#apprDoc" onclick="apprDoc('${dto.seq}','${dto.appr_turn}')">
			 			</td>
			 		</tr>
			 </c:forEach>
	</table>
</form>
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
</div>
</body>
</html>