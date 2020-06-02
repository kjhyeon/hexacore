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
		<table>
			<tr>
				<td>결재자1</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[0].id" value="appr1"></td>
				<td>name:<input name="lists[0].name" value="결재자1"></td>
				<td>duty:<input name="lists[0].duty" value="결재테스트부서 대리"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:<input name="lists[0].turn" value="1"></td>
				<td>appr_kind:<input name="lists[0].appr_kind" value="합의"></td>
			</tr>
			<tr>
				<td>결재자2</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[1].id" value="appr2"></td>
				<td>name:<input name="lists[1].name" value="결재자2"></td>
				<td>duty:<input name="lists[1].duty" value="결재테스트부서 부장"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:<input name="lists[1].turn" value="2"></td>
				<td>appr_kind:<input name="lists[1].appr_kind" value="합의"></td>
			</tr>
			<tr>
				<td>결재자3</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[2].id" value="appr3"></td>
				<td>name:<input name="lists[2].name" value="결재자3"></td>
				<td>duty:<input name="lists[2].duty" value="대표"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:<input name="lists[2].turn" value="3"></td>
				<td>appr_kind:<input name="lists[2].appr_kind" value="결재"></td>
			</tr>
			<tr>
				<td>참조자</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[4].id" value="refer1"></td>
				<td>name:<input name="lists[4].name" value="참조자1"></td>
				<td>duty:<input name="lists[4].duty" value="결재테스트부서 사원"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:</td>
				<td>appr_kind:<input name="lists[4].appr_kind" value="참조"></td>
			</tr>
		</table>
		문서 정보 입력 테이블
		<table>
			<tr>
				<td>문서양식 이름:</td><td>${typeDto.name}</td>
			</tr>
			<tr>
				<td>seq:</td><td>DB에 입력되면서 nextval로 생성될꺼임</td>
			</tr>
			<tr>
				<td>author:</td><td><input type="text" name="author" value="writer1"></td>
			</tr>
			<tr>
				<td>title:</td><td><input type="text" name="title"></td>
			</tr>
			<tr>
				<td>state:</td><td>임시저장 누르면 0/상신누르면 1로 보낼거임</td>
			</tr>
			<tr>
				<td>regdate:</td><td>SYSDATE로 넣어줄거임</td>
			</tr>
			<tr>
				<td>appr_turn:</td><td>1로 알아서 들어감</td>
			</tr>
			<tr>
				<td>type_seq:</td><td><input type="text" name="type_seq" value="${typeDto.type_seq}"></td>
			</tr>
			<tr>
				<td>content:</td><td><textarea name="content" rows="5" cols="50">${typeDto.content}</textarea></td>
			</tr>
		</table>
		<input type="submit" value="임시저장">
	</form>
</body>
</html>