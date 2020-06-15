<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	function writeDocType(){
		location.href="./goDocTypeWriteForm.do";
	}	
</script>
<body>
	<form>
		<table>
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
</body>
</html>