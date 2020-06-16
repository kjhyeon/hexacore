<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	function goList(){
		location.href="./goDocTypeMng.do";
	}
	function goModify(){
		$("#form1").attr("action", "./goDocTypeModifyForm.do");
		$("#form1").attr("method", "GET");
		document.form1.submit();
	}
	function deleteDocType(){
		if(confirm("해당 문서 양식을 삭제하시겠습니까?\n(삭제된 문서 양식은 복구할 수 없습니다.)")==true){
			location.href="./deleteDocType.do?seq="+${dto.type_seq}+"";
		}
	}
</script>
<body>
	<div style="width: 50%; margin: auto;">
	<h1 style="text-align: center;">${dto.name}</h1>
		<form id="form1" name="form1">
			<input type="hidden" name="type_seq" value="${dto.type_seq}">
			${dto.content}<br>
			<div style="text-align: center;">
			<input class="btn" type="button" value="목록으로" onclick="goList()">
			<input class="btn" type="button" value="수정" onclick="goModify()">
			<input class="btn" type="button" value="삭제" onclick="deleteDocType()">
			</div>
		</form>
	</div>
</body>
</html>