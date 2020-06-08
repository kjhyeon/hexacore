<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자결재 Home</title>
<link rel="stylesheet" href="./css/sidebar.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	// 사이드 바의 문서 작성 클릭할 때마다 문서 양식 출력/숨기기
	$(".menu01").click(function(){
		if($(".subside").css("display") == "none"){
			$(".subside").show();
			$(".subside").animate({
				width: '300px'
			},200,function(){
				$(".subside>p").show();
			});
			$(".menu01").css("background-color","#414345");
		}else{
			$(".subside>p").hide();
			
			$(".subside").animate({
				width: '0px'
			},200,function(){
				$(".subside").css("display", "none");
			});
			$(".menu01").css("background-color","#232526");
		}
	});
	
	// 문서함 클릭시 하위 문서함 출력/숨기기
	$(".menu").click(function(){
		if($(this).find(".submenu").css("display") == "none"){
			$(this).find(".submenu").slideDown('fast').show();
		}else{
			$(this).find(".submenu").slideUp('fast').hide();
		}
	});
		
	
});
</script>
<body>
	<div class="wrap">
		<div class="sidebar">
			<div class="menu01" onclick="location.href='./goDocTypeList.do'">문서 작성</div>
			<div class="menu">상신문서함
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=0'">임시저장문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=1'">결재대기문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=2'">결재중문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=3'">결재완료문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=4'">반려문서</div>
			</div>
			<div class="menu">결재문서함
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=0'">결재할문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=1'">결재중문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=2'">결재완료문서</div>
				<div class="submenu" onclick="location.href='./goMyDocList.do?state=3'">반려문서</div>
			</div>
		</div>
	<%@include file="../header.jsp" %>
		<div class="contatiner">
			<div class="content">
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
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			<p>asdf</p>
			</div>
		</div>
	</div>
	<%@include file="../footer.jsp" %>
</body>
</html>