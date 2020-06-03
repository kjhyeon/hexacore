<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 입력</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
$(document).ready(
		function() {
			var op = $("select[name=state]>option");
			for (var i = 0; i < op.length; i++) {
				if(op.eq(i).val() == $("#stateNo").val()){
					op.eq(i).attr("selected",true);
				}
			}
		}
	)
</script>
<script type="text/javascript" src="./javascript/employeeMng.js"></script>
</head>
<body>
	<%@include file="./../../header.jsp" %>
	<div id="container">
	<form action="./updateEmployee.do" method="post" id="frm" enctype="multipart/form-data">
		<input type="hidden" value="${dto.employee_number }" name="employee_number">
		<table class="table">
			<tr>
				<td rowspan="13"><img id="profile-image" src="./image/profile/${dto.profile_img}" ></td>
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
					<input type="date" name="birth" placeholder="생년월일" value="${dto.birth }" id="birth">
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<select name="gender">
						<c:if test="${dto.gender eq 'm' }">
							<option value="m" selected="selected">남</option>
							<option value="f">여</option>					
						</c:if>
						<c:if test="${dto.gender eq 'w' }">
							<option value="m">남</option>
							<option value="f"  selected="selected">여</option>					
						</c:if>
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
							<c:choose>
								<c:when test="${dto.e_rank eq rank.e_rank }">
									<option value="${rank.e_rank }" selected="selected">${rank.e_rank_name}</option>
								</c:when>
								<c:otherwise>
									<option value="${rank.e_rank }">${rank.e_rank_name}</option>
								</c:otherwise>
							</c:choose>
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
				<input type="hidden" id="stateNo" value="${dto.state }">
					<select name="state">
						<option value="0" id="op0">재직</option>
						<option value="1" id="op1">휴가</option>
						<option value="-1" id="op-1">퇴사</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>권한</th>
				<td>
					<select name="auth">
						<c:if test="${dto.auth eq 'A' }">
							<option value="A" selected="selected">관리자</option>
							<option value="U">일반</option>
						</c:if>
						<c:if test="${dto.auth eq 'U' }">
							<option value="A">관리자</option>
							<option value="U" selected="selected">일반</option>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<th>프로필이미지</th><td><input multiple="multiple" type="file" name="profile_file" onchange="a(this,'profile-')"></td>
			</tr>
			<tr>
				<td><img id="sign-image" src="./image/profile/${dto.sign_img}" ></td><th>결재 도장</th><td><input multiple="multiple" type="file" name="sign_file" id="sign" onchange="a(this,'sign-')"></td>
			</tr>
		</table>
		<div id="right-content">
			<script type="text/javascript">
						function a(input,str) {
							if (input.files && input.files[0]) {
								var reader = new FileReader(); // File API
								reader.onload = function(e) {
									var img = document.getElementById(str+"image");
									img.src = e.target.result;
									if(str=='sign-'){
										img.style.width = '60px';
										img.style.height = "60px";
									}else{
										img.style.height = "120px";
										img.style.width = '120px';
									}
								}
								}
								reader.readAsDataURL(input.files[0]);
								$(str+"#image").show();
							}
					</script>
		</div>
		<div class="btns" style="text-align: center;">
			<input type="button" onclick = "formChk()" value="수정">
			<input type="reset" value="리셋">
		</div>
	</form>
	</div>
	<%@include file="./../../footer.jsp" %>
</body>
</html>