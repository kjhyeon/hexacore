<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function writeDocType(){
		location.href="./goDocTypeWriteForm.do";
	}	
</script>
<body>
	<%@include file="./../../header.jsp" %>
	<form>
		<table>
			<tr>
				<td>문서명</td>
			</tr>
			<c:forEach var="type" items="${list}">
				<tr>
					<td><a href="./goDocTypeDetail.do?seq=${type.type_seq}">${type.name}</a></td>
				</tr>
			</c:forEach>
	</table>
	<input type="button" value="문서 양식 추가" onclick="writeDocType()">
	<input type="hidden" name="index" id="index" value="${row.index }">
	<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum }">
	<input type="hidden" name="listNum" id="listNum" value="${row.listNum }">
	</form>
	<div class="center" style="text-align: center; position: relative;">
			<ul class="pagination">
				<li><a href="./goDocTypeMng.do?page=0" onclick="pageFirst()">&laquo;</a></li>
				<li><a href="./goDocTypeMng.do?page=${row.index-1}" onclick="pagePre()">&lt;</a></li>
				<c:forEach var="i" begin="${row.pageNum }" end="${row.count }" step="1">
					<li><a href="./goDocTypeMng.do?page=${i-1}">${i }</a></li>
				</c:forEach>
				<li><a href="./goDocTypeMng.do?page=${row.index+1}" >&gt;</a></li>
				<li><a href="./goDocTypeMng.do?page=${row.lastPage-1}" >&raquo;</a></li>
			</ul>
		</div>
	<%@include file="./../../footer.jsp" %>
</body>
</html>