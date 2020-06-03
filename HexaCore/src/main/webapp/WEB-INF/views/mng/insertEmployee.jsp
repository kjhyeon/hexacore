<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 입력</title>
<link rel="stylesheet" href="./css/empMng.css">
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="./javascript/employeeMng.js"></script>
</head>
<body>
	<%@include file="./../../header.jsp"%>
	<div id="container">
	<form action="./insertEmployee.do" method="post" id ="frm" enctype="multipart/form-data">
		<table class="table table-bordered">
		<h2>사원 등록</h2>
			<tr>
				<td rowspan="10"><img id="image" src="#" style="display :none;"></td>
				<th>아이디</th>
				<td>
					<input class="form-control" type="text" name="id" placeholder="아이디" id="id"><br> 
					<span id="idChkMsg" style="font-size: 7px;"></span>
					<input type="hidden" id="idChkFlag" value="false">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th><td><input class="form-control" type="password" name="password" placeholder="비밀번호" id="password"></td>
			</tr>
			<tr>
				<th>이름</th><td><input class="form-control" type="text" name="name" placeholder="이름" id="name"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<input type="date" class="form-control" name="birth" placeholder="생년월일" id="birth">
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<select name="gender" class="form-control">
						<option value="m">남</option>
						<option value="f">여</option>					
					</select>
				</td>
			</tr>
			<tr>
				<th>전화번호</th><td><input class="form-control" type="text" name="phone" placeholder="000-0000-0000" id="phone"></td>
			</tr>
			<tr>
				<th>이메일</th><td><input class="form-control" type="text" name="email" placeholder="○○○@gmail.com" id="email"></td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
					<input class="form-control" type="text" id="department_name" readonly="readonly" placeholder="부서">
					<input class="form-control" type="hidden" name="department_id" id="department_id" value='0'>
					<input class="btn" type="button" onclick="deptSearch()" value="부서 찾기"><br>
				</td>
			</tr>
			<tr>
				<th>거주지</th>
				<td>
					<div style="text-align: left;">
						<input class="form-control" type="text" name="postcode" id="postcode" placeholder="우편번호" readonly="readonly" style="width: 30%; display: inline;">
						<input class="btn" type="button" onclick="execDaumPostcode()" value="우편번호 찾기" style="position: relative; display: inline"><br>
					</div>
					<input class="form-control" type="text" name="address" id="address" placeholder="주소" readonly="readonly">
					<input class="form-control" type="text" name="detailaddress" id="detailAddress" placeholder="상세주소">
				</td>
			</tr>
			<tr>
				<th>프로필이미지</th><td><input  multiple="multiple" type="file" name="profile_file" onchange="a(this)"></td>
			</tr>
		</table>
			<script type="text/javascript">
						function a(input) {
							if (input.files && input.files[0]) {
								var reader = new FileReader(); // File API
								reader.onload = function(e) {
									var img = document.getElementById("image");
									img.src = e.target.result;
									img.style.width = '120px';
									img.style.height = "120px";
								}
								reader.readAsDataURL(input.files[0]);
								$("#image").show();
							}
						}
					</script>
		<input class="btn" type="button" value="등록" onclick="formChk()">
	</form>
	</div>
	<%@include file="./../../footer.jsp"%>
</body>
</html>