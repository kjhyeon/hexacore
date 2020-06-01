<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 입력</title>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<script type="text/javascript" src="./javascript/employeeMng.js"></script>
	<div id="container">
	<form action="./updateEmployee.do" method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<span>${dto.id }</span> 
				</td>
			</tr>
			<tr>
				<th>비밀번호</th><td><input type="password" name="password" placeholder="비밀번호" id="password2"></td>
			</tr>
			<tr>
				<th>이름</th><td><input type="text" name="name" placeholder="이름" value="${dto.name }" id="name"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<input type="date" name="birth" placeholder="생년월일" value="${dto.birth } id="birth">
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<select name="gender">
						<option value="m">남</option>
						<option value="f">여</option>					
					</select>
				</td>
			</tr>
			<tr>
				<th>전화번호</th><td><input type="text" name="phone" value="${dto.phone}" id="phone"></td>
			</tr>
			<tr>
				<th>이메일</th><td><input type="text" name="email" value="${dto.email }" id="email"></td>
			</tr>
			<tr>
				<th>직위</th>
				<td>
					<select name="e_rank">
						<c:forEach items="${ranks}" var="rank">
							<option value="${rank.e_rank }">${rank.e_rank_name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
					<input type="text" id="department_name" readonly="readonly" value="${dto.department_name }">
					<input type="hidden" name="department_id" id="department_id" value="${dto.department_id }">
					<input type="button" onclick="deptSearch()" value="부서 찾기"><br>
				</td>
			</tr>
			<tr>
				<th>거주지</th>
				<td>
					<input type="text" name="postcode" id="postcode" placeholder="우편번호" readonly="readonly" value="${dto.postcode }">
					<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
					<input type="text" name="address" id="address" placeholder="주소" readonly="readonly" value="${dto.address }"><br>
					<input type="text" name="detailaddress" id="detailAddress" placeholder="상세주소" value ="${dto.detailaddress }">
				</td>
			</tr>
			<tr>
				<th>상태</th>
				<td>
					<select name="state">
						<option value="0">재직</option>
						<option value="0">휴가</option>
						<option value="-1">퇴사</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>권한</th>
				<td>
					<select name="auth">
						<option value="A">관리자</option>
						<option value="U">일반</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>프로필이미지</th><td><input type="file" name="profile_img" id="profile"></td>
			</tr>
			<tr>
				<th>결재 도장</th><td><input type="file" name="sign_img" id="sign"></td>
			</tr>
		</table>
		<input type="button" onclick = "formChk()" value="수정">
		<input type="reset" value="리셋">
	</form>
	</div>
	<%@include file="./../../footer.jsp" %>
</body>
</html>