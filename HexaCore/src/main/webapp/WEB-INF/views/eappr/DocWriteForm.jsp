<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자문서 작성</title>
<link rel="stylesheet" href="./css/doc.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function apprSearch() {
		var treeWindow = window.open("./goApprTree.do", "결재루트 선택",
				"width=1000, height=750");
	}
	function setChildValue(nodes) {
		nodes = nodeSort(nodes);
		$(".apprtable").empty();
		var ap = "<tr class='index'>"
			   + "<td>부서직급</td><td>이름</td><td>종류</td><td>순서</td>"
			   + "</tr>";
		$(".apprtable").append(ap);
		for (var i = 0; i < nodes.length; i++) {
			var child = nodes[i].children;
// 				 		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3종류 4X 5아이디 6e_rank
// 				 			alert(child[j].innerHTML);
// 				 		}
			var app = "<tr>"
					+ "<td>"+child[0].innerHTML+" "+child[1].innerHTML+"<input type='hidden' name='lists["+i+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"
					+ "<td>"+child[2].innerHTML+"<input type='hidden' name='lists["+i+"].name' value='"+child[2].innerHTML+"'></td>"
					+ "<td>"+child[3].innerHTML+"<input type='hidden' name='lists["+i+"].appr_kind' value='"+child[3].innerHTML+"'></td>"
					+ "<td>"+(i+1)+"<input type='hidden' name='lists["+i+"].turn' value='"+(i+1)+"'>"
					+ "<input type='hidden' name='lists["+i+"].id' value='"+child[5].innerHTML+"'>"
					+ "</td>"
					+ "</tr>";

			$(".apprtable").append(app);

		}
	}
	function setChildValue2(nodes1) {
		nodes = nodeSort(nodes1);
		$(".refertable").empty();
		var ap = "<tr class='index'>"
			   + "<td>부서직급</td><td>이름</td>"
			   + "</tr>";
		$(".refertable").append(ap);
		for (var i = 0; i < nodes.length; i++) {
			var child = nodes[i].children;
// 				 		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3종류 4X 5아이디 6e_rank
// 				 			alert(child[j].innerHTML);
// 				 		}
			var app = "<tr>"
					+ "<td>"+child[0].innerHTML+" "+child[1].innerHTML+"<input type='hidden' name='lists["+(i+3)+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"
					+ "<td>"+child[2].innerHTML+"<input type='hidden' name='lists["+(i+3)+"].name' value='"+child[2].innerHTML+"'>"
					+ "<input type='hidden' name='lists["+(i+3)+"].appr_kind' value='"+child[3].innerHTML+"'>"
					+ "<input type='hidden' name='lists["+(i+3)+"].turn' value='"+0+"'>"
					+ "<input type='hidden' name='lists["+(i+3)+"].id' value='"+child[5].innerHTML+"'>"
					+ "</td>"
					+ "</tr>";

			$(".refertable").append(app);

		}
	}

	function nodeSort(nodes){
		var tempNode;
		for (var i = 0; i < nodes.length; i++) {
			var child1 = nodes[i].children;
			for (var j = 0; j < nodes.length; j++) {
				var child2 = nodes[j].children;
				if((i < j)&&(child1[6].innerHTML < child2[6].innerHTML)){
					tempNode = nodes[j];
					nodes[j] = nodes[i];
					nodes[i] = tempNode;
				}
			}
		}
		
		return nodes;
	}
	
	function report() {
		if($("#inputTitle").val()==""){
			alert("제목을 입력하세요.");
		}else if($(".apprtable > tr").length != 4){
			alert("결재자를 선택하세요.");
		}else{
			$(".leftBox").append("<input type='hidden' name='state' value='1'>");
			$("#insertdoc").attr("action", "./DocWrite.do");
			document.insertdoc.submit();	
		}
	}

	function savedoc() {
		if($("#inputTitle").val()==""){
			alert("제목을 입력하세요.");
		}else{
			$(".leftBox").append("<input type='hidden' name='state' value='0'>");
			$("#insertdoc").attr("action", "./DocWrite.do");
			document.insertdoc.submit();
		}
		
	}
	function cancelwrite() {
		if (confirm("문서 작성을 취소하시겠습니까?\n(작성한 내용은 저장되지 않습니다.)") == true) {
			$("#insertdoc").attr("action", "./goEapprHome.do");
			$("#insertdoc").attr("method", "get");
			document.insertdoc.submit();
		} else {
			return;
		}
	}

	function resetDoc() {
		if (confirm("모든 내용을 초기화하시겠습니까?\n(지정한 결재루트와 문서 내용 모두가 삭제됩니다.)") == true) {
			location.reload(true);
		} else {
			return;
		}
	}
</script>
<body>
	<form id="insertdoc" name="insertdoc" method="POST">
		<div class="rightBox">
			<div class="apprBox">
				<input class="apprbtn" type="button" onclick="apprSearch()" value="결재선 선택/수정"><br>
				<h3>결재자</h3>
				
				<div class="apprs">
					<table class="apprtable">
						<tr class="index">
							<td>부서직급</td><td>이름</td><td>종류</td><td>순서</td>
						</tr>
					</table>	
				</div>
				<br><hr>
				<h3>참조자</h3>
				<div class="refers">
					<table class="refertable">
						<tr class="index">
							<td>부서직급</td><td>이름</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="btnBox">
				<input type="hidden" name="type_seq" value="${typeDto.type_seq}">
				<input type="button" value="작성 취소" onclick="cancelwrite()">
				<input type="button" value="초기화" onclick="resetDoc()">
				<input type="button" value="임시저장" onclick="savedoc()">
				<input type="button" value="바로상신" onclick="report()">
			</div>
		</div>
		
		<div class="leftBox">
			<h1>${typeDto.name}</h1>
			<table id="docuHead">
				<tr>
					<th>기안자</th>
					<td><sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal.username" var="drafter"/>
							${drafter}
						</sec:authorize>
						<input type="hidden" id="author" name="author" value="${drafter}">
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input id="inputTitle" type="text" name="title"></td>
				</tr>
			</table>
			<table id="apprSign">
			</table>
			<br>
			<textarea id="p_content" name="content" rows="5" cols="50">${typeDto.content}</textarea>
			<script type="text/javascript">CKEDITOR.replace('p_content', {height : 700});</script>
		</div>
	</form>
</body>
</html>