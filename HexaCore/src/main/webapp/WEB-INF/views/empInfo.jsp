<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${dto.name}님의 정보</title>
</head>
<body>
	<%@include file="./empInfoBar.jsp" %>
<div style="margin-left:25%">
	<div class="w3-container container">
	<h2>사원 정보</h2>
	<table class="table table-bordered" style="width: 75%">
		<tr><td rowspan="8">
			<c:choose>
				<c:when test="${empty dto.profile_img || dto.profile_img eq '' }">
					<img style="height: 120px; width: 120px;" alt="" src="./image/default_profile.png">
				</c:when>
				<c:otherwise>
					<img style="height: 120px; width: 120px;" alt="" src="/home/HexaCore/image/profile/${dto.profile_img }">
				</c:otherwise>
			</c:choose>
		</td><th>아이디</th><td>${dto.id }</td></tr>
		<tr><th>이름</th><td>${dto.name }</td></tr>
		<tr><th>부서</th><td>${dto.department_name }</td></tr>
		<tr><th>직위</th><td>${dto.e_rank_name }</td></tr>
		<tr><th>번호</th><td>${dto.phone }</td></tr>
		<tr><th>이메일</th><td>${dto.email }</td></tr>
		<tr><th>주소</th><td>[${dto.postcode }] ${dto.address }&nbsp;${dto.detailaddress }</td></tr>
		<tr><th>사인</th>
		<td>
		<c:choose>
				<c:when test="${empty dto.sign_img || dto.sign_img eq '' }">
					<img style="height: 80px; width: 80px;" id="sign-image" src="./image/default_sign.png" >
				</c:when>
				<c:otherwise>
					<img style="height: 80px; width: 80px;" id="sign-image" src="/home/HexaCore/image/profile/${dto.sign_img}" >
				</c:otherwise>
			</c:choose>
		</td></tr>
	</table>
	</div>
</div>
</body>
</html>