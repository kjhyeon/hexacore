<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	.selected{
		background-color: skyblue;
	}
</style>
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
	function addEmp() {
		var id = $("#deptTree").jstree("get_selected");
		var node = ($("#deptTree").jstree("get_node",id));
		var flag = false;
		if(node.type == 'people'){
			for (var i = 0; i < $("tr").length; i++) {
				var chr = $("tr").eq(i).children();
				if(chr.eq(0).text()==node.li_attr['deptname']&&chr.eq(1).text()==node.li_attr['e_rank_name']&&chr.eq(2).text()==node.text){
					flag = true;
				}
			}
			if(!flag){
				var tr = "<tr onclick='selectNode(this) id='appr''> <td>"+node.li_attr['deptname']+"</td> <td>"+node.li_attr['e_rank_name']+"</td> <td>"+node.text+"</td>"
						+"<td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
				$("#empTable").append(tr);
			}
		}
	}
	
	function delEmp(node) {
		var tr = node.parentElement.parentElement;
		tr.parentElement.removeChild(tr);
// 		alert(node.parentElement.parentElement);
// 		$("tr").eq(0).remove();
	}
	
	function delEmps() {
		$(".selected").remove();
	}
	
	function selectNode(node) {
		if(node.hasAttribute("class")){
			node.removeAttribute("class");
		}else{
			node.setAttribute("class","selected");
		}
	}
	
	function closeEmp(){
		var nodes = $("tr");
		opener.setChildValue(nodes);
		window.close();
	}
</script>
</head>
<body>
	<%@include file="../mng/allTree.jsp" %><br>
	<input type="button" value="추가" onclick="addEmp()">
	<input type="button" value="삭제" onclick="delEmps()">
	<input type="button" value="확인" onclick="closeEmp()">
	<table id="empTable">
	</table>
</body>
</html>