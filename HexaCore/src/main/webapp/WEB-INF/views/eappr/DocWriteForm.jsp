<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="./DocWrite.do" method="POST">
		결재자 정보 입력 테이블
		<button>결재선 선택</button>
		<table>
			<tr>
				<td>결재자1</td><td><input name="lists[0].name" value="name1"></td>
			</tr>
			<tr>
				<td>결재자2</td><td><input name="lists[1].name" value="name2"></td>
			</tr>
			<tr>
				<td>결재자3</td><td><input name="lists[2].name" value="name3"></td>
			</tr>
			<tr>
				<td>참조자</td><td></td>
			</tr>
		</table>
		문서 정보 입력 테이블
		<table>
			<tr>
				<td>문서형식</td><td>${dto.name}</td>
			</tr>
			<tr>
				<td>제목</td><td><input type="text"></td>
			</tr>
			<tr>
				<td>기안자</td><td>세션에서 가져오기</td>
			</tr>
			<tr>
				<td>기안일</td><td>현재시간</td>
			</tr>
			<tr>
				<td>내용</td><td><textarea name="content" rows="5" cols="50">${dto.content}</textarea></td>
			</tr>
		</table>
		<input type="hidden" name="state" value="1">
		<input type="button" value="작성취소" onclick="">
		<input type="button" value="초기화" onclick="">
		<input type="button" value="양식 재선택" onclick="">
		<input type="button" value="임시저장" onclick="">
		<input type="button" value="상신" onclick="">
		<input type="submit" value="테스트">
	</form>
</body>
</html>