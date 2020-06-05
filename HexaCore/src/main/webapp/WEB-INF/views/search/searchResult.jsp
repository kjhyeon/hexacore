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
		<table class="table table-bordered">
			<tr><th>seq</th><th>제목</th><th>글쓴이</th><th>작성일</th></tr>
			<c:forEach items="${eDocList }" var="eDoc">
				<tr>
					<td>${eDoc.seq }</td><td>${eDoc.title }</td><td>${eDoc.author }</td><td>${eDoc.regdate }</td>
				</tr>
			</c:forEach>
		</table>
		<hr>
		<table class="table table-bordered">
		</table>
		<hr>
		<table class="table table-bordered">
		</table>
		<hr>
		<table class="table table-bordered">
		</table>
	</div>
	
	
	<%@include file="./../../footer.jsp" %>
</body>
</html>