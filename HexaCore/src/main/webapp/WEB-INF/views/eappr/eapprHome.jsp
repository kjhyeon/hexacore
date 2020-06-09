<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자결재 홈</title>
<style type="text/css">
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
* {
	font-family: 'Jeju Gothic', sans-serif;
}
</style>
</head>
<body>
	문서요약보기
	<br> 상신문서함에 있는 문서의 총 개수 : ${count1}
	<br> 임시저장된 문서의 개수 : ${count2}
	<br> 상신한 문서 중 결재대기중인 문서의 개수 : ${count3}
	<br> 상신한 문서 중 결재중인 문서의 개수 : ${count4}
	<br> 상신한 문서 중 결재완료된 문서의 개수 : ${count5}
	<br> 상신한 문서 중 반려된 문서의 개수 : ${count6}
	<br> 참조문서함에 있는 문서의 개수 : ${count7}
	<br> 결재문서함에 있는 문서의 총 개수 : ${count8}
	<br> 내가 결재해야할 문서의 개수 : ${count9}
	<br>
</body>
</html>