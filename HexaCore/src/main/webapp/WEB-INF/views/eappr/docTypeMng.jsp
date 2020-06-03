<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function writeDocType(){
		location.href="./goDocTypeWriteForm.do";
	}	
</script>
<body>
	<%@include file="./../../header.jsp" %>
	<form>
		<table>
			<tr>
				<td>문서명</td>
			</tr>
			<c:forEach var="type" items="${list}">
				<tr>
					<td><a href="./goDocTypeDetail.do?seq=${type.type_seq}">${type.name}</a></td>
				</tr>
			</c:forEach>
	</table>
	<input type="button" value="문서 양식 추가" onclick="writeDocType()">
	</form>
	<%@include file="./../../footer.jsp" %>
</body>
</html>