<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function goList(){
		location.href="./goDocTypeMng.do";
	}
	function goModify(){
		$("#form1").attr("action", "./goDocTypeModifyForm.do");
		$("#form1").attr("method", "POST");
		document.form1.submit();
	}
	function deleteDocType(){
		if(confirm("해당 문서 양식을 삭제하시겠습니까?\n(삭제된 문서 양식은 복구할 수 없습니다.)")==true){
			location.href="./deleteDocType.do?seq="+${dto.type_seq}+"";
		}
	}
</script>
<body>
<%@include file="./../../header.jsp" %>
	<form id="form1" name="form1">
	<input type="hidden" name="type_seq" value="${dto.type_seq}">
		<table>
			<tr>
				<td>문서양식 이름:</td>
				<td>${dto.name}<input type="hidden" name="name" value="${dto.name}"></td>
			</tr>
			<tr>
				<td>내용:</td>
				<td>${dto.content}<input type="hidden" name="content" value="${dto.content}"></td>
			</tr>
		</table>
		<input type="button" value="목록으로" onclick="goList()">
		<input type="button" value="수정" onclick="goModify()">
		<input type="button" value="삭제" onclick="deleteDocType()">
	</form>
<%@include file="./../../footer.jsp" %>
</body>
</html>