<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
		<div id="left-content">
			<iframe id="mngTree" src="./deptMngTree.do" style="width: 300px; height: 500px;">/</iframe>
		</div>
		<div id="right-content">
			<span>수정영역</span>
			<table>
				<tr>
					<th>부서 이름</th><td><input type="text" id="department_name" name="name"></td>
				</tr>
				<tr>
					<th>팩스 번호</th><td><input type="text" id="department_fax" name="faxnum"></td>
				</tr>
				<tr>
					<th>전화 번호</th><td><input type="text" id="department_phone" name="d_phone"></td>
				</tr>
			</table>
			
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
</body>
</html>