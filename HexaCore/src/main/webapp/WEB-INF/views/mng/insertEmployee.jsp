<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 입력</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/empMng.css">
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="./javascript/employeeMng.js"></script>
<script type="text/javascript" src="./javascript/imgChk.js"></script>
</head>
<body>
	<div id="container">
	<form action="./insertEmployee.do" method="post" id ="frm" enctype="multipart/form-data">
		<h2>사원 등록</h2>
		<table class="table table-bordered">
			<tr>
				<td rowspan="10">
					<img style="height: 120px; width: 120px;" alt="" src="./image/default_profile.png" id="profile-image">
				</td>
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
				<th>프로필이미지</th>
				<td>
					<input  type="file" name="profile_file" onchange="a(this,'profile-')" accept="image/*">
				</td>
			</tr>
		</table>
		<input class="btn" type="button" value="등록" onclick="formChk()">
	</form>
	</div>
</body>
</html>