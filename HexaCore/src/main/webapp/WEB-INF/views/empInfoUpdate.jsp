<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>님의 정보</title>
</head>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="./javascript/imgChk.js"></script>
<script type="text/javascript">
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

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
	<div class="w3-container container">
	<h2>정보 수정</h2>
	<form action="./empInfoUpdate.do" method="post" enctype="multipart/form-data" id="frm">
	<input type="hidden" name ="employee_number" value="${dto.employee_number }">
	<input type="hidden" name ="name" value="${dto.name }">
	<input type="hidden" name ="state" value="${dto.state }">
	<table class="table table-bordered" style="width:75%">
		<tr><td rowspan="10">
			<c:choose>
				<c:when test="${empty dto.profile_img || dto.profile_img eq '' }">
					<img style="height: 120px; width: 120px;" alt="" src="./image/default_profile.png" id="profile-image">
				</c:when>
				<c:otherwise>
					<img style="height: 120px; width: 120px;" alt="" src="/home/HexaCore/image/profile/${dto.profile_img }" id="profile-image">
				</c:otherwise>
			</c:choose>
		</td><th>아이디</th><td>${dto.id }</td></tr>
		<tr><th>비밀번호</th><td><input class="form-control" type="password" name="password" id="password" maxlength="20"></td></tr>
		<tr><th>비밀번호 확인</th><td><input class="form-control" type="password" id="password2" maxlength="20"></td></tr>
		<tr><th>이름</th><td>${dto.name }</td></tr>
		<tr><th>부서</th><td>${dto.department_name }</td></tr>
		<tr><th>직위</th><td>${dto.e_rank_name }</td></tr>
		<tr><th>번호</th><td><input class="form-control" type="text" name="phone" id="phone" value=${dto.phone }></td></tr>
		<tr><th>이메일</th><td><input class="form-control" type="text" name="email" id="email" value="${dto.email }"></td></tr>
		<tr><th>주소</th>
			<td>
				<div style="text-align: left;">
						<input class="form-control" type="text" name="postcode" id="postcode" placeholder="우편번호" readonly="readonly" style="width: 30%; display: inline;" value="${dto.postcode }">
						<input class="btn" type="button" onclick="execDaumPostcode()" value="우편번호 찾기" style="position: relative; display: inline"><br>
					</div>
					<input class="form-control" type="text" name="address" id="address" placeholder="주소" readonly="readonly" value="${dto.address }" }>
					<input class="form-control" type="text" name="detailaddress" id="detailAddress" placeholder="상세주소" value="${dto.detailaddress }">
			</td>
		</tr>
		<tr><th>프로필 이미지</th><td><input type="file" name="profile_file" id="profile_file" onchange="a(this,'profile-')" accept="image/*"></td></tr>
		<tr><td>
			<c:choose>
				<c:when test="${empty dto.sign_img || dto.sign_img eq '' }">
					<img style="height: 80px; width: 80px;" id="sign-image" src="./image/default_sign.png" id="sign-image" >
				</c:when>
				<c:otherwise>
					<img style="height: 80px; width: 80px;" id="sign-image" src="/home/HexaCore/image/profile/${dto.sign_img}" id="sign-image">
				</c:otherwise>
			</c:choose>
		</td><th>사인</th><td><input type="file" name="sign_file" id="sign_file"  onchange="a(this,'sign-')" accept="image/*"></td></tr>
	</table>
		<div id="btns"><input type="button" value="수정" onclick="formChk()" id="sign-image" class="btn"><input type="reset" value="리셋" class="btn"></div>
	</form>
	
	</div>
</div>
</body>
</html>