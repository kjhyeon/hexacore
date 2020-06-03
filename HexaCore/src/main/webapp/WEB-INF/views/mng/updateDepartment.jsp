<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	#container{
		text-align: center;
	}
	#left-content{
		border: 3px solid grey;
  		border-radius: 5px;
		position: fixed;
		padding : auto;
		width : 300px;
		height : 600px;
		margin-left: 300px;
		margin-right : 250px;
		margin-top : 100px;
	}
	
	#mngTree{
	  width: 300px;
	  height: 600px; 
	  border : none;
	  margin-top : 50px;
	  margin-right: 100px;
	 }
	
	#right-content{
		border: 3px solid grey;
  		border-radius: 5px;
		position: fixed;
		margin : auto;
		margin-left : 650px;
		padding : 100px 100px 100px 100px;
		margin-top:100px;
		width: 500px;
		height: 600px;
	}
	
</style>
<title>Insert title here</title>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<div id="container">
		<div id="left-content">
			<iframe id="mngTree" src="./deptMngTree.do">/</iframe>
		</div>
		<div id="right-content">
			<h1>수정영역</h1>
			<table class="table">
				<tr>
					<th>부서 이름</th><td><input class="form-control" type="text" id="department_name" name="name"></td>
				</tr>
				<tr>
					<th>팩스 번호</th><td><input class="form-control" type="text" id="department_fax" name="faxnum"></td>
				</tr>
				<tr>
					<th>전화 번호</th><td><input class="form-control" type="text" id="department_phone" name="d_phone"></td>
				</tr>
			</table>
			<hr>
			<input type="button" value="생성" onclick="createDept()">
			<input type="button" value="삭제" onclick="deleteDept()">
			<input type="button" value="수정" onclick="updateDept()">
		</div>
		<script type="text/javascript" async="true">
			function updateDept() {
				var iframe = document.getElementById("mngTree");
				iframe.contentWindow.updateChk();
			}
			function deleteDept() {
				var iframe = document.getElementById("mngTree");
				iframe.contentWindow.deleteDept();
			}
			function createDept() {
				var iframe = document.getElementById("mngTree");
				iframe.contentWindow.createDept();
			}
		</script>

	</div>
	<%@include file="./../../footer.jsp" %>
</body>
</html>