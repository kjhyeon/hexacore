<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HEXACORE 결재선</title>
<link rel="stylesheet" href="./css/apprTree.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/apprtree.js"></script>
</head>
<body>
	<div class="leftBox">
	<%@include file="../mng/allTree.jsp" %><br>
	</div>
	<div class="centerBox">
		<h1>결재 유형 선택</h1>
		<input type="radio" id="kind" name="kind" value="참조" checked="checked">참조<br>
		<input type="radio" id="kind" name="kind" value="합의">합의<br>
		<input type="radio" id="kind" name="kind" value="결재">결재<br>
		<input type="button" value="추가" onclick="addEmp()"><br>
		<input type="button" value="삭제" onclick="delEmps()">
		<div class="bottombtn">
			<input type="button" value="확인" onclick="closeEmp()">
			<input type="button" value="취소" onclick="cancelEmp()">
		</div>
	</div>
	<div class="rightBox">
		<div class="apprBox">
			<h3>결재자</h3>
			<div class="apprs">
				<table class="apprtable">
					<tr style="background: #E1E2E1; height: 30px">
						<th>부서</th><th>직급</th><th>이름</th><th>종류</th><th>삭제</th>
					</tr>
				</table>
			</div>
		</div>
			<br><hr>
		<div class="referBox">
			<h3>참조자</h3>
			<div class="refers">
				<table class="refertable">
					<tr style="background: #E1E2E1; height: 30px">
						<th>부서</th><th>직급</th><th>이름</th><th>종류</th><th>삭제</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>