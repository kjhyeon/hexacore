<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<form>
		<table>
			<tr>
				<td>seq</td>
				<td>제목</td>
				<td>결재상태</td>
				<td>기안일</td>
				<td>기안자</td>
				<td>결재순서</td>
			</tr>
			<c:forEach var="doc" items="${list}">
				<tr>
					<td>${doc.seq}</td>
					<td>${doc.title}</td>
					<td>${doc.state}</td>
					<td>${doc.regdate}</td>
					<td>${doc.author}</td>
					<td>${doc.appr_turn}</td>
				</tr>
			</c:forEach>
	</table>
	<input type="hidden" name="index" id="index" value="${row.index}">
	<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum}">
	<input type="hidden" name="listNum" id="listNum" value="${row.listNum}">
	</form>
	<div class="center" style="text-align: center; position: relative;">
			<ul class="pagination">
				<li><a href="./goMyDocList.do?page=0&state=${state}" onclick="pageFirst()">&laquo;</a></li>
				<li><a href="./goMyDocList.do?page=${row.index-1}&state=${state}" onclick="pagePre()">&lt;</a></li>
				<c:forEach var="i" begin="${row.pageNum }" end="${row.count }" step="1">
					<li><a href="./goMyDocList.do?page=${i-1}&state=${state}">${i }</a></li>
				</c:forEach>
				<li><a href="./goMyDocList.do?page=${row.index+1}&state=${state}" >&gt;</a></li>
				<li><a href="./goMyDocList.do?page=${row.lastPage-1}&state=${state}" >&raquo;</a></li>
			</ul>
		</div>
	<%@include file="./../../footer.jsp" %>
</body>
</html>