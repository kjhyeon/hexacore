<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HEXACORE 문서양식 작성</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function cancelwrite(){
		if(confirm("양식 작성을 취소하시겠습니까?\n(작성한 내용은 저장되지 않습니다.)") == true){
			$("#addType").attr("action", "./goDocTypeMng.do");
			$("#addType").attr("method", "get");
			document.addType.submit();
		}else{
			return;
		}
	}
	function resetDoc(){
		if(confirm("모든 내용을 초기화하시겠습니까?\n(작성한 내용이 모두 초기화됩니다.)") == true){
			location.reload(true);
		}else{
			return;
		}
	}
	function savedoc(){
		$("#addType").attr("action", "./writeDocType.do");
		document.addType.submit();
	}
</script>
<body>
	<h1 style="padding-left: 50px;">문서 양식 추가</h1>
	<form id="addType" name="addType" method="POST">
		<table class="table" style="text-align: center;">
			<tr>
				<td>양식 이름</td>
				<td><input type="text" name="name" style="width:100%;"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea id="p_content" name="content" rows="5" cols="50"></textarea>
					<script type="text/javascript">CKEDITOR.replace('p_content', {height: 500});</script>
				</td>
			</tr>
		</table>
		<div style="text-align: center;">
		<input class="btn" type="button" value="취소" onclick="cancelwrite()">
		<input class="btn" type="button" value="초기화" onclick="resetDoc()">
		<input class="btn" type="button" value="저장" onclick="savedoc()">
		</div>
	</form>
</body>
</html>