<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
  <link rel="stylesheet" href="./css/doc.css">
<meta charset="UTF-8">
<title>전자</title>
</head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="./js/sweetalert.js"></script>
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<body>
<div class="container">
	<c:choose>
		<c:when test="${state eq '0'}"><h1>임시보관함</h1></c:when>
		<c:when test="${state eq '1'}"><h1>결재대기문서함</h1></c:when>
		<c:when test="${state eq '2' || (state eq '6')}"><h1>결재중문서함</h1></c:when>
		<c:when test="${state eq '3'}"><h1>승인문서함</h1></c:when>
		<c:when test="${state eq '4'}"><h1>반려문서함</h1></c:when>
		<c:when test="${state eq '7'}"><h1>결재필요문서함</h1></c:when>
	</c:choose>
		<form  method="post">
			<table id="myTable" class="table table-bordered table-striped" >
				<tr>
					<th>문서번호</th>
					<th>기안자</th>
					<th>제목</th>
					<th>기안일</th>
					<th>문서상태</th>
				</tr>
				<c:if test="${empty lists}">
					<tr>
						<th colspan="5">결재 문서가 없습니다.</th>
					</tr>
				</c:if>
				<c:forEach var="dto" items="${lists}" varStatus="status">
					<tr>
						<td>${dto.seq}</td>
						<td>${dto.author}</td>
						<td><a href="./docDetail.do?seq=${dto.seq}">${dto.title}</a></td>
						<td>${dto.regdate}</td>
						<c:choose>
							<c:when	test="${(dto.a_turn eq dto.appr_turn) &&(id ne dto.author)&& ((dto.state eq '1') || (dto.state eq '2'))}">
								<td><input type="button" value="결재" data-backdrop='static'	data-keyboard='false' data-toggle="modal"
									data-target="#apprDoc" onclick="apprDoc('${dto.seq}','${dto.appr_turn}','${dto.a_turn}','${state}')">
								</td>
							</c:when>
							<c:when test="${dto.state eq '2'}">
								<td>결재중</td>
							</c:when>
							<c:when test="${dto.state eq '1'}">
								<td>결재대기</td>
							</c:when>
							<c:when test="${dto.state eq '4'}">
								<td>반려</td>
							</c:when>
							<c:when test="${dto.state eq '3'}">
								<td>승인</td>
							</c:when>
							<c:when test="${dto.state eq '0'}">
								<td>보관중</td>
							</c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" name="index" id="index" value="${row.index }">
			<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum}"> 
			<input type="hidden" name="listNum" id="listNum" value="${row.listNum}">
			<div class="center" style="text-align: center; position: relative; ">
				<ul class="pagination">
					<li><a href="./docLists.do?page=0&state=${state}">&laquo;</a></li>
					<li><a href="./docLists.do?page=${row.index-1}&state=${state}">&lt;</a></li>
					<c:forEach var="i" begin="${row.pageNum }" end="${row.count }" step="1">
						<li><a href="./docLists.do?page=${i-1}&state=${state}">${i }</a></li>
					</c:forEach>
					<li><a href="./docLists.do?page=${row.index+1}&state=${state}">&gt;</a></li>
					<li><a href="./docLists.do?page=${row.lastPage-1}&state=${state}">&raquo;</a></li>
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