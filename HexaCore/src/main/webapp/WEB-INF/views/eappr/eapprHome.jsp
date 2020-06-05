<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자결재 Home</title>
<style type="text/css">
	body{
		margin: 0px;
	}
	.sidemenu {
		position: fixed;
		background-color: skyblue;
		width: 300px;
		height: 100%;
	}
	.menubox {
		width: 100%;
		height: 100px;
		background-color: blue;
		text-align: center;
		vertical-align: middle;
		cursor: pointer;
	}
	.submenu {
		position: fixed;
		background-color: skyblue;
		height: 100%;
		left: 300px;
		display: none;
	}
	.submenu > p {
		display: none;
	}
	#countdoc {
		position: absolute;
		left: 300px;
	}
</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".menubox").click(function(){
			$(".submenu").show();
			$(".submenu").animate({
				width: '300px'
			},300);
			$(".submenu>p").show();
		});
	});
</script>
<body>
<%@include file="./../../header.jsp" %>
	<div class="sidemenu">
		<a href="./goDocTypeList.do">문서 작성</a><br>
		<a href="">상신문서함</a><br>
		<a href="">결재문서함</a><br>
		<a href="">참조문서함</a><br>
		<a href="">완료문서함</a><br>
		<a href="">반려문서함</a><br>
		<a href="">저장문서함</a><br>
		<div class="menubox"><p>서브 메뉴 열기</p></div>
	</div>
	<div class="submenu">
		<p>asdf</p>
	</div>
	<div id="countdoc">
		문서요약보기<br>
		상신문서함에 있는 문서의 총 개수 : ${count1}<br>
		임시저장된 문서의 개수 : ${count2}<br>
		상신한 문서 중 결재대기중인 문서의 개수 : ${count3}<br>
		상신한 문서 중 결재중인 문서의 개수 : ${count4}<br>
		상신한 문서 중 결재완료된 문서의 개수 : ${count5}<br>
		상신한 문서 중 반려된 문서의 개수 : ${count6}<br>
		참조문서함에 있는 문서의 개수 : ${count7}<br>
		결재문서함에 있는 문서의 총 개수 : ${count8}<br>
		내가 결재해야할 문서의 개수 : ${count9}<br>
	</div>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@include file="./../../footer.jsp" %>
</body>
</html>