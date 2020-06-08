<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>Insert title here</title>
<style type="text/css">
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
* {
	font-family: 'Jeju Gothic', sans-serif;
}

html {
	height: 100%;
	background-color: #E1E2E1;
}

body {
	margin: 0;
	height: 100%;
}

.wrap {
	min-height: 100%;
	position: relative;
	padding-bottom: 100px;
}

header {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	text-align: center;
	height: 100px;
background: #232526;  /* fallback for old browsers */
background: -webkit-linear-gradient(to bottom, #414345, #232526);  /* Chrome 10-25, Safari 5.1-6 */
background: linear-gradient(to bottom, #414345, #232526); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */


	
	color: white;
	font-size: 1.5em;
}

.sidebar {
	position: fixed;
	left: 0;
	width: 10%;
	height: 100%;
	padding-top: 100px;
	padding-bottom: 100px;
	background: #232526;


	
}

.menu, .menu01 {
	position: relative;
	height: 100px;
	line-height: 100px;
	width: 100%;
	text-align: center;
	color: white;
	font-size: 1em;
}
.menu:hover, .menu01:hover {
	background-color: #414345;
	cursor: pointer;
}

footer {
	position: absolute;
	bottom: 0;
	left: 0;
	right: 0;
	height: 100px;
	background: #232526;  /* fallback for old browsers */
	background: -webkit-linear-gradient(to top, #414345, #232526);  /* Chrome 10-25, Safari 5.1-6 */
	background: linear-gradient(to top, #414345, #232526); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */

	color: white;
}

.content {
	position: relative;
	top: 120px;
	left: 11%;
	padding-bottom: 100px;
	background-color: #F5F5F6;
	width: 87%;
	padding: 10px;
}

.subside {
	position: fixed;
	background-color: #414345;
	height: 70%;
	top: 100px;
	left: 10%;
	display: none;
	z-index: 2;
}
	
.subside > p {
	display: none;
}

.container {
	z-index: 1;
}

.topmenu {
	display: table-cell;
	width: 250px;
	height: 100px;
	vertical-align: middle;
}

.topmenu:hover {
	transform: scale(1.2);
	transition: transform .5s;
	cursor: pointer;
}

.footer {
	display: table-cell;
	height: 100px;
	vertical-align: middle;
	text-align: center;
	width: 200px;
}
</style>
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
});
</script>
<body>
	<div class="wrap">
		<div class="sidebar">
			<div class="menu01">문서작성</div>
			<div class="menu">상신문서함</div>
			<div class="menu">결재문서함</div>
			<div class="menu">참조문서함</div>
		</div>
		<div class="subside">
			<p>문서양식1</p>
		</div>
		<header>
			<div class="topmenu" style="font-size:0.5em"><img alt="logo" src="./image/hexa64.png">HEXACORE</div>
			<div class="topmenu">전자결재</div>
			<div class="topmenu">게시판</div>
			<div class="topmenu">쪽지</div>
			<div class="topmenu">관리</div>
			<div class="topmenu">검색</div>
		</header>
		<div class="container">
			<div class="content">
				<iframe style="height: 800px; width: 600px;" src="./goMyDocList.do?state=0"></iframe>
			</div>
		</div>
		<footer><div class="footer"></div></footer>
	</div>
</body>
</html>