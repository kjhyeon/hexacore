<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자문서 작성</title>
<link rel="stylesheet" href="./css/doc.css">
</head>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function apprSearch() {
		var treeWindow = window.open("./goApprTree.do", "결재루트 선택",
				"width=1000, height=750");
	}
	function setChildValue(nodes) {
		$("#approval").empty();
		for (var i = 0; i < nodes.length; i++) {
			var child = nodes[i].children;
			//	 		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3종류 4X 5아이디 6e_rank
			//	 			alert(child[j].innerHTML);
			//	 		}
			var app = "<tr>"
					+ "<td>결재자1 직위:<input name='lists["+i+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"
					+ "<td>결재자1 이름:<input name='lists["+i+"].name' value='"+child[2].innerHTML+"'></td>"
					+ "<td>결재자1 아이디:<input name='lists["+i+"].id' value='"+child[4].innerHTML+"'></td>"
					+ "<td>결재자1 결재순서:<input name='lists[" + i
					+ "].turn' value='" + (i + 1) + "'></td>"
					+ "<td><select name='lists["+i+"].appr_kind'>"
					+ "<option value='참조'>참조</option>"
					+ "<option value='합의'>합의</option>"
					+ "<option value='결재'>결재</option>" + "</select></td>"
					+ "</tr>";

			$(".apprs").append(app);

		}

	}

	
	
	function report() {
		$("#docu")
				.append(
						"<tr><td><input type='hidden' name='state' value='1'></td></tr>");
		$("#insertdoc").attr("action", "./DocWrite.do");
		document.insertdoc.submit();
	}

	function savedoc() {
		$("#docu")
				.append(
						"<tr><td><input type='hidden' name='state' value='0'></td></tr>");
		$("#insertdoc").attr("action", "./DocWrite.do");
		document.insertdoc.submit();
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
				<table class="apprIndex">
					<tr>
						<td>부서직급</td><td>이름</td><td>종류</td><td>상태</td>
					</tr>
				</table>
				<div class="apprs"></div>
				<br><hr>
				<h3>참조자</h3>
				<table class="referIndex">
					<tr>
						<td>부서직급</td><td>이름</td>
					</tr>
				</table>
				<div class="refers"></div>
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