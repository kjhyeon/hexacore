<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function addDocType(){
		location.href="./goAddTypeForm.do";
	}	
</script>
<body>
	<%@include file="./../../header.jsp" %>
	<form>
		<table>
		<thead>
			<tr>
				<td>선택<br><input name="chkAll" type="checkbox" onclick="chkAll()"><td>
				<td>문서번호</td>
				<td>문서명</td>
				<td>카테고리</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="type" items="${list}">
				<tr>
					<td><input name="chk" type="checkbox" value="${type.type_seq}"></td>
					<td>${type.type_seq}</td>
					<td>${type.name}</td>
					<td>${type.category}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="button" value="문서 양식 추가" onclick="addDocType()">
	<input type="button" value="선택된 문서 양식 삭제" onclick="chkDel()">
	</form>
	<%@include file="./../../footer.jsp" %>
</body>
</html>