<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<body>
	${Ddto}
	<div id="container">
	<form>
		<table>
			<tr>
				<td>글번호</td>
				<td><input type="text"  id="seq" name="seq" value="${Ddto.seq}"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" id="title" name="title" value="${Ddto.title}"></td>
			</tr>
			<tr>
				<td>기안자</td>
				<td><input type="text" id="author" name="author" value="${Ddto.author}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea id="content" name="content" rows="5" cols="50">${Ddto.content}</textarea></td>
			</tr>
		</table>
			<input type="button" value="임시저장" onclick="saveDoc()">
			<input type="button" value="상신" onclick="apprDoc()">
	</form>
	</div>
</body>
</html>
