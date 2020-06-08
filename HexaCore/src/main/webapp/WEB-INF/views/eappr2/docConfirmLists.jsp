<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
</head>
<body>
	<%@include file="./../../header.jsp"%>
	<div id="container">
	<div>
		<form class="navbar-form navbar-right" role="search"
			action="./totalSearch.do" method="get">
			<div class="form-group input-group">
				<span class="input-group-btn"> <select name="type"
					class="form-control">
						<option value="title/con">제목+내용</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="author">기안자</option>
				</select>
				</span> <input type="text" class="form-control" placeholder="Search.."
					name="keyword" style="position: relative; float: right;"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</span>
			</div>
		</form>
		</div>
		<form action="#" method="post">
			<table  class="table table-bordered" style="width: 75%">
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
						<c:choose>
 						<c:when test="${name ne Ddto.author && (Ddto.appr_turn != '0') && (Adto.turn eq Ddto.appr_turn)}"> 
							<td><input type="button" value="결재" data-backdrop='static'	data-keyboard='false' data-toggle="modal" data-target="#apprDoc" onclick="apprDoc('${dto.seq}','${dto.appr_turn}')"></td>
						</c:when> 
						<c:when test="${Ddto.appr_turn ne Adto.turn}">
							<td>결재중</td>
						</c:when>
						<c:when test="${Ddto.state eq '4'}">
							<td>반려</td>
						</c:when>
						<c:otherwise>
							<td>승인</td>
						</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" name="index" id="index" value="${row.index }">
			<input type="hidden" name="pageNum" id="pageNum"
				value="${row.pageNum }"> <input type="hidden" name="listNum"
				id="listNum" value="${row.listNum }">
			<div class="center" style="text-align: center; position: relative;">
				<ul class="pagination">
					<li><a href="./docLists.do?page=0" onclick="pageFirst()">&laquo;</a></li>
					<li><a href="./docLists.do?page=${row.index-1}"
						onclick="pagePre()">&lt;</a></li>
					<c:forEach var="i" begin="${row.pageNum }" end="${row.count }"
						step="1">
						<li><a href="./docLists.do?page=${i-1}">${i }</a></li>
					</c:forEach>
					<li><a href="./docLists.do?page=${row.index+1}">&gt;</a></li>
					<li><a href="./docLists.do?page=${row.lastPage-1}">&raquo;</a></li>
				</ul>
			</div>
		</form>
		<div id="apprDoc" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							data-toggle='modal'>&times;</button>
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