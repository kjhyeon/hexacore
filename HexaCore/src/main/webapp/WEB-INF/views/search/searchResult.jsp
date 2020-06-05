<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<div id="container">
		<h2>검색 결과</h2>
		<hr>
		<div id="eDocContainer">
			<h3>상신함</h3>
			<button class="btn" style="float: right;">더 보기</button>
			<table class="table table-bordered">
				<tr><th>seq</th><th>제목</th><th>글쓴이</th><th>작성일</th></tr>
				<c:forEach items="${eDocList }" var="eDoc">
					<tr>
						<td>${eDoc.seq }</td>
						<td><a href="./docDetail.do?seq=${eDoc.seq }">${eDoc.title }</a></td>
						<td>${eDoc.author }</td><td>${eDoc.regdate }</td>
					</tr>
					<tr style="background-color: #EEEEEE;"><td colspan="4">${eDoc.content }</td></tr>
				</c:forEach>
			</table>
		</div>
		<hr>
		<div id="noticeContainer">
			<table class="table table-bordered">
			</table>
		</div>
		<hr>
		<div id="freeContainer">
			<h3>자유게시판</h3>
			<button class="btn" style="float: right;">더 보기</button>
			<table class="table table-bordered" id="freeBbs">
				<tr><th>seq</th><th>제목</th><th>글쓴이</th><th>작성일</th></tr>
				<c:forEach items="${freeBbsList }" var="free">
					<tr>
						<td>${free.seq }</td>
						<td><a href="./bbsDetail.do?seq=${free.seq}">${free.title }</a></td>
						<td>${free.name }</td><td>${free.regdate }</td>
					</tr>
					<tr style="background-color: #EEEEEE;"><td colspan="4">${free.content }</td></tr>
				</c:forEach>
			</table>
		</div>
		<hr>
		<div id="fileContainer">
			<table class="table table-bordered">
			</table>
		</div>
	</div>
	
	
	<%@include file="./../../footer.jsp" %>
</body>
</html>