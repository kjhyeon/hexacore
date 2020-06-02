<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="container">
<section id="intro">
	<div class="page-title">
		<h1>게시판</h1>
	</div>
	<div class="page-middle">
		<input type="text" id="text_in" placeholder="검색어를 입력해주세요">
		<input type="button" id="search" value="검색"/>
	</div>
</section>
<form action="#"  method="post">
	<table style="height: 500px; width: 75%; margin: 0 auto;">
		<tr>
			<th>SEQ</th>
			<th>TITLE</th>
			<th>CONTENT</th>
			<th>REGDATE</th>
		</tr>
			 <c:if test="${empty lists}">
			 	<tr>
			 		<th colspan="5">결재 하실 문서가 없습니다.</th>
			 	</tr>
			 </c:if>
			 
			 <c:forEach var="dto" items="${lists}">
			 		<tr>
			 			<td>${dto.seq}</td>
			 			<td>${dto.title}</td>
			 			<td><a href="./docDetail.do?seq=${dto.seq}">${dto.content}</a></td>
			 			<td>${dto.regdate}</td>
			 		</tr>
			 </c:forEach>
	</table>
	
	<input  type="submit" value="승인">
</form>
</div>

</body>
</html>