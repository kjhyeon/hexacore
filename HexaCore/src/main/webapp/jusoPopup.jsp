<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
function apprSearch() {
	var treeWindow = window.open("./TreeTest.jsp", "부서목록", "width=600,height=1000");
}

function setChildValue(nodes){
	for (var i = 0; i < nodes.length; i++) {
		var child = nodes[i].children;
// 		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3X 4아이디
// 			alert(child[j].innerHTML);
// 		}
		var app = "<span>"+child[0].innerHTML+" "+child[1].innerHTML+" "+child[2].innerHTML +"</span>";
		$("#approval").append(app);
	}
	
}
</script>
<body onload="init();">
	<button onclick="apprSearch()">팝업</button>
	<div id="approval"></div>
</body>
</html>