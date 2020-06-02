<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<table>
		<thead>
			<tr>
				<td>문서번호</td>
				<td>문서명</td>
				<td>카테고리</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="type" items="${list}">
				<tr>
					<td>${type.type_seq }</td>
					<td>${type.name }</td>
					<td>${type.category }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<ul>
	<c:forEach var="type" items="${list}">
		<li><a href="./goDocTypePreview.do?type_seq=${type.type_seq}">${type.name}</a></li>
	</c:forEach>
</ul>
	<input type="button" value="생성">
	<%@include file="./../../footer.jsp" %>
</body>
</html>