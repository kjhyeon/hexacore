<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/apprTree.css">
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
				var chkval = $(":input:radio[name=kind]:checked").val();
				if(chkval == '참조'){
					var tr1 = "<tr class='refernode' onclick='selectNode(this)' id='referss'><td>"+node.li_attr['deptname']+"</td><td>"+node.li_attr['e_rank_name']+"</td><td>"+node.text+"</td><td>"+chkval+"</td><td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
					$(".refertable").append(tr1);
				}else{
					var tr2 = "<tr class='apprnode' onclick='selectNode(this)' id='apprss'><td>"+node.li_attr['deptname']+"</td><td>"+node.li_attr['e_rank_name']+"</td><td>"+node.text+"</td><td>"+chkval+"</td><td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
					$(".apprtable").append(tr2);
				}
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
		if($(".apprtable > tbody > tr").length != 4){
			alert("결재자는 3명을 선택해야합니다.");
		} else{
			var apprnodes = $(".apprnode");
			var refernodes = $(".refernode");
			opener.setChildValue(apprnodes);
			opener.setChildValue2(refernodes);
			window.close();
		}
	}
	
	function cancelEmp(){
		window.close();
	}
</script>
</head>
<body>
	<div class="leftBox">
	<%@include file="../mng/allTree.jsp" %><br>
	</div>
	<div class="centerBox">
		<h1>결재 유형 선택</h1>
		<input type="radio" id="kind" name="kind" value="참조" checked="checked">참조<br>
		<input type="radio" id="kind" name="kind" value="합의">합의<br>
		<input type="radio" id="kind" name="kind" value="결재">결재<br>
		<input type="button" value="추가" onclick="addEmp()"><br>
		<input type="button" value="삭제" onclick="delEmps()">
		<div class="bottombtn">
			<input type="button" value="확인" onclick="closeEmp()">
			<input type="button" value="취소" onclick="cancelEmp()">
		</div>
	</div>
	<div class="rightBox">
		<div class="apprBox">
			<h3>결재자</h3>
			<div class="apprs">
				<table class="apprtable">
					<tr style="background: #E1E2E1; height: 30px">
						<th>부서</th><th>직급</th><th>이름</th><th>종류</th><th>삭제</th>
					</tr>
				</table>
			</div>
		</div>
			<br><hr>
		<div class="referBox">
			<h3>참조자</h3>
			<div class="refers">
				<table class="refertable">
					<tr style="background: #E1E2E1; height: 30px">
						<th>부서</th><th>직급</th><th>이름</th><th>종류</th><th>삭제</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>