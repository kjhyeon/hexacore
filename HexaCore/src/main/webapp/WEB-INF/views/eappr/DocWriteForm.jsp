<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
				"width=600, height=800");
	}
	function setChildValue(nodes) {
		$("#approval").empty();
		for (var i = 0; i < nodes.length; i++) {
			var child = nodes[i].children;
			//	 		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3X 4아이디 5e_rank
			//	 			alert(child[j].innerHTML);
			//	 		}
			var app = "<tr>"
					+ "<td>결재자1</td>"
					+ "<td>결재자1 아이디:<input name='lists["+i+"].id' value='"+child[4].innerHTML+"'></td>"
					+ "<td>결재자1 이름:<input name='lists["+i+"].name' value='"+child[2].innerHTML+"'></td>"
					+ "<td>결재자1 직위:<input name='lists["+i+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"
					+ "<td>결재자1 결재순서:<input name='lists[" + i
					+ "].turn' value='" + (i + 1) + "'></td>"
					+ "<td><select name='lists["+i+"].appr_kind'>"
					+ "<option value='참조'>참조</option>"
					+ "<option value='합의'>합의</option>"
					+ "<option value='결재'>결재</option>" + "</select></td>"
					+ "</tr>";

			$("#approval").append(app);

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
		<div class="apprBox">
			<input class="apprbtn" type="button" onclick="apprSearch()"
				value="결재선 선택"><br> 결재자
			<div class="apprs"></div>
			<br>
			<hr>
			<br> 참조자
			<div class="refers"></div>
			<input type="hidden" name="type_seq" value="${typeDto.type_seq}">
			<input type="button" value="작성 취소" onclick="cancelwrite()"> <input
				type="button" value="초기화" onclick="resetDoc()"> <input
				type="button" value="임시저장" onclick="savedoc()"> <input
				type="button" value="바로상신" onclick="report()">
		</div>
		<div class="docBox">
			<h1>${typeDto.name}</h1>

			<table id="docu">
				<tr>
					<td>기안자</td>
					<td><sec:authorize access="isAuthenticated()">
							<sec:authentication property="principal.username" var="drafter" />
							<input type="text" name="author" value="${drafter}">
						</sec:authorize></td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="title"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea id="p_content" name="content" rows="5" cols="50">${typeDto.content}</textarea>
						<script type="text/javascript">
							CKEDITOR.replace('p_content', {
								height : 500
							});
						</script>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>