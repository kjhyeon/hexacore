<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/sidebar.css">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<body>
	<div class="wrap">
		<div class="sidebar">
		<div class="menu" data-url='#'>공지게시판</div>
		<div class="menu" data-url="./freeBbsMain.do">자유게시판</div>
		<div class="menu" data-url='#'>자료실</div>
		</div>
	<%@include file="./../header.jsp" %>
	<div class="container">
		<div class="content" style="min-height: 900px; min-width: 1000px;">
		<iframe id="iframe" src="./freeBbsMain.do" style="height: 100%; width: 100%; border: none;  position: relative; min-height: 800px; min-width: 1000px;"></iframe>
		</div>
	</div>
	</div>
	<script type="text/javascript">
$(document).ready(function(e) {
	/* a요소를 클릭 했을 시 */
	    $('.menu').click(function(){
	/* iframe 요소의 src 속성값을 a 요소의 data-url 속성값으로 변경 */ 
	        $('#iframe').attr('src',$(this).attr('data-url'));
	        })
	});

</script>
</body>
</html>