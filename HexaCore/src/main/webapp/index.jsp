<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
</head>
<script type="text/javascript">
	var treeWindow;
	function treePopup() {
		var treeWindow = window.open("./deptMngTree.do", "부서목록", "width=500,height=600");
		
	}
	
	function setChildText(){
        document.getElementById("name").value =  openWin.document.getElementById("cInput").value;
    }

</script>
<body>
<button onclick="treePopup()">부서선택</button>
부서 이름 : <input type="text" id="department_name">
부서 ID : <input type="text" id="department_id">
상위 부서 ID : <input type="text" id="upper_id">
</body>
</html>
