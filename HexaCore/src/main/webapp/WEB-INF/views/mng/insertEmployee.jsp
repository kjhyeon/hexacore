<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 입력</title>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
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
    
    var treeWindow;
	function deptSearch() {
		var treeWindow = window.open("./deptTree.do", "부서목록", "width=300,height=400");
		
	}
	

	$(document).ready(
		function(){
			$("#id").keyup(function(){
				$("#idChkMsg").html("");
				var inputlength = $(this).val().length;
				var id = $(this).val();
				if(id.indexOf(" ")!=-1){
					$("#idChkMsg").css("color","red");
					$("#idChkMsg").html("아이디엔 공백이 포함될 수 없습니다");
					$("#idChkFlag").val('false');
				}else if(inputlength<4){
					$("#idChkMsg").css("color","red");
					$("#idChkMsg").html("3자리 이상의 아이디를 입력해주세요");
					$("#idChkFlag").val('false');
				}else if(inputlength>3){ //공백이 없고 3자리 이상이라면 ajax를 통한 유효성 검사
					$.ajax({
						type : "post",
						url : "./idChk.do",
						async : true,
						data : {"id":$("#id").val()},
						success : function(msg){
						if(msg == 'true'){
							$("#idChkMsg").css("color","black");
							document.getElementById("idChkMsg").innerHTML = "사용 가능한 아이디입니다.";
							document.getElementById("idChkFlag").value='true';
						}else{
							document.getElementById("idChkMsg").innerHTML = "사용 불가능한 아이디입니다.";
							document.getElementById("idChkFlag").value='false';
						}
						},
						error: function(){
							document.getElementById("idChkMsg").innerHTML = "아이디 중복체크 실패";
						}
					});
				}
			});
		});
	
	
// 	function idChk(){
// 		if($("#id").val()==''||$("#id").val().length<5){
// 			alert("최소 4자 이상의 아이디를 입력해주세요");
// 		}else{
// 			$.ajax({
// 				url : "./idChk.do",
// 				data : {"id":$("#id").val()},
// 				method : "POST",
// 				success : function(msg) {
// 					if(msg == 'true'){
// 						document.getElementById("idChkMsg").innerHTML = "사용 가능한 아이디입니다.";
// 						document.getElementById("idChkFlag").value='true';
// 					}else{
// 						document.getElementById("idChkMsg").innerHTML = "사용 불가능한 아이디입니다.";
// 					}
// 				},
// 				error : function() {
// 					document.getElementById("idChkMsg").innerHTML = "아이디 중복체크 실패";
// 					return false;
// 				}
// 			});
// 		}
// 	}
	
	function formChk() {
		var phoneChk =  /^\d{2,3}-\d{3,4}-\d{4}$/;
		var emailChk = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		if($("#id").val()==''||$("#id").val().length<4){
			alert("최소 4자 이상의 아이디를 입력해주세요");
			$("#id").focus();
		}else if($("#idChkFlag").val()=='false'){
			alert("아이디 중복 체크를 해주세요");
			$("#id").focus();
		}else if($("#password").val().length<4){
			alert("최소 4자 이상의 비밀번호를 입력해주세요");
			$("#password").focus();
		}else if($("#name").val()==''){
			alert("이름을 입력해주세요");
			$("#name").focus();
		}else if($("#birth").val()==''){
			alert("생년월일을 입력해주세요");
			$("#birth").focus();
		}else if($("#phone").val()==''||!phoneChk.test($("#phone").val())){
			alert("알맞은 전화번호를 입력해주세요");
			$("#phone").focus();
		}else if($("#email").val()==''||!emailChk.test($("#email").val())){
			alert("알맞은 이메일을 입력해주세요");
			$("#email").focus();
		}else if($("#department_id").val()=='0'){
			alert("부서를 선택해주세요");
			$("#department_name").focus();
		}else if($("#postcode").val()==''||$("#address").val()==''||$("#detailaddress").val()==''){
			alert("주소를 입력해주세요");
			$("#address").focus();
		}else{
			document.getElementById("frm").submit();
		}
		
		return false;
	}
</script>
</head>
<body>
	<%@include file="./../../header.jsp"%>
	<div id="container">
	<form action="./insertEmployee.do" method="post" id ="frm">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="id" placeholder="아이디" id="id"><br> 
<!-- 					<input type="button" onclick="idChk()" value="중복체크"> -->
					<span id="idChkMsg" style="font-size: 7px;"></span>
					<input type="hidden" id="idChkFlag" value="false">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th><td><input type="password" name="password" placeholder="비밀번호" id="password"></td>
			</tr>
			<tr>
				<th>이름</th><td><input type="text" name="name" placeholder="이름" id="name"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<input type="date" name="birth" placeholder="생년월일" id="birth">
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
				<th>전화번호</th><td><input type="text" name="phone" placeholder="000-0000-0000" id="phone"></td>
			</tr>
			<tr>
				<th>이메일</th><td><input type="text" name="email" placeholder="○○○@gmail.com" id="email"></td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
					<input type="text" id="department_name" readonly="readonly" placeholder="부서">
					<input type="hidden" name="department_id" id="department_id" value='0'>
					<input type="button" onclick="deptSearch()" value="부서 찾기"><br>
				</td>
			</tr>
			<tr>
				<th>거주지</th>
				<td>
					<input type="text" name="postcode" id="postcode" placeholder="우편번호" readonly="readonly">
					<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
					<input type="text" name="address" id="address" placeholder="주소" readonly="readonly"><br>
					<input type="text" name="detailaddress" id="detailAddress" placeholder="상세주소">
				</td>
			</tr>
			<tr>
				<th>프로필이미지</th><td><input type="file" name="profile_img"></td>
			</tr>
		</table>
		<input type="button" value="등록" onclick="formChk()">
	</form>
	</div>
	<%@include file="./../../footer.jsp"%>
</body>
</html>