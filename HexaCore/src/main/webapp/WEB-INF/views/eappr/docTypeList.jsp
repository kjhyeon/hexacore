<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
문서 양식 리스트 출력
${list}
<ul>
	<c:forEach var="type" items="${list}">
		<li><a href="./goDocTypePreview.do?type_seq=${type.type_seq}">${type.name}</a></li>
	</c:forEach>
</ul>

</body>
</html>