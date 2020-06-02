<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>님의 정보</title>
</head>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">

function formChk(){
	var phoneChk =  /^\d{2,3}-\d{3,4}-\d{4}$/;
	var emailChk = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	if($("#password").val()!=''&&($("#password").val().length<4)){
		alert("최소 4자 이상의 비밀번호로 수정해주세요");
		$("#password").focus();
	}else if($("#password").val()!=''&&$("#password").val()!=$("#password2").val()){
		alert("비밀번호를 확인해주세요");
		$("#password2").focus();
	}else if($("#phone").val()==''||!phoneChk.test($("#phone").val())){
		alert("알맞은 전화번호를 입력해주세요");
		$("#phone").focus();
	}else if($("#email").val()==''||!emailChk.test($("#email").val())){
		alert("알맞은 이메일을 입력해주세요");
		$("#email").focus();
	}else if($("#postcode").val()==''||$("#address").val()==''||$("#detailaddress").val()==''){
		alert("주소를 입력해주세요");
		$("#address").focus();
	}else{
		document.getElementById("frm").submit();
	}
}

</script>
<body>
<%@include file="./empInfoBar.jsp" %>
<div style="margin-left:25%">
	<div class="w3-container">
	<form action="./empInfoUpdate.do" method="post" enctype="multipart/form-data" id="frm">
	<input type="hidden" name ="employee_number" value="${dto.employee_number }">
	<input type="hidden" name ="name" value="${dto.name }">
	<input type="hidden" name ="state" value="${dto.state }">
	<table>
		<tr><td rowspan="10"><img alt="" src=""></td><th>아이디</th><td>${dto.id }</td></tr>
		<tr><th>비밀번호</th><td><input type="password" name="password" id="password" maxlength="20"></td></tr>
		<tr><th>비밀번호 확인</th><td><input type="password" id="password2" maxlength="20"></td></tr>
		<tr><th>이름</th><td>${dto.name }</td></tr>
		<tr><th>부서</th><td>${dto.department_name }</td></tr>
		<tr><th>직위</th><td>${dto.e_rank_name }</td></tr>
		<tr><th>번호</th><td><input type="text" name="phone" id="phone" value=${dto.phone }></td></tr>
		<tr><th>이메일</th><td><input type="text" name="email" id="email" value="${dto.email }"></td></tr>
		<tr><th>주소</th>
			<td>
				<input type="text" name="postcode" id="postcode" placeholder="우편번호" readonly="readonly" value="${dto.postcode }">
				<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
				<input type="text" name="address" id="address" placeholder="주소" readonly="readonly" value="${dto.address }"><br>
				<input type="text" name="detailaddress" id="detailAddress" placeholder="상세주소" value ="${dto.detailaddress }">
			</td>
		</tr>
		<tr><th>프로필 이미지</th><td><input multiple="multiple" type="file" name="profile_file" id="profile_file"></td></tr>
		<tr><th>사인</th><td><input multiple="multiple" type="file" name="sign_file" id="sign_file"></td></tr>
		<tr><td><input type="button" value="수정" onclick="formChk()"></td><td><input type="reset" value="리셋"></td></tr>
	</table>
	</form>
	
	</div>
</div>
</body>
</html>