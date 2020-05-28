<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 입력</title>

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
		var treeWindow = window.open("./testTree.do", "부서목록", "width=300,height=400");
		
	}
</script>
</head>
<body>

	<div id="container">
	<form action="./insertEmployee.do" method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="id" placeholder="아이디"> <button onclick="idChk()">중복체크</button><br>
					<span id="idChkMsg" style="font-size: 7px;">아이디메세지</span>	
				</td>
			</tr>
			<tr>
				<th>비밀번호</th><td><input type="password" name="password" placeholder="비밀번호"></td>
			</tr>
			<tr>
				<th>이름</th><td><input type="text" name="name" placeholder="이름"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>
					<input type="date" name="birth" placeholder="생년월일">
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
				<th>전화번호</th><td><input type="text" name="phone" placeholder="000-0000-0000"></td>
			</tr>
			<tr>
				<th>이메일</th><td><input type="text" name="email" placeholder="○○○@gmail.com"></td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
					<input type="text" id="department_name" readonly="readonly" placeholder="부서">
					<input type="hidden" name="department_id" id="department_id">
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
		<input type="submit" value="등록">
	</form>
	</div>
</body>
</html>