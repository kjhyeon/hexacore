<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/sidebar.css">
<style type="text/css">
	#iframe{
		position : relative; 
		overflow-x:hidden; 
		overflow:auto; 
		border: none; 
		min-height: 900px;
		min-width: 1000px;
		width: 100%;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<div class="wrap">
		<div class="sidebar">
		<div class="menu" data-url="./employeeList.do">유저 관리</div>
		<div class="menu" data-url='./updateDepartment.do'>부서 관리</div>
		<div class="menu" data-url='./goDocTypeMng.do'>문서 양식 관리</div>
		</div>
	<%@include file="./../header.jsp" %>
	<div class="container">
		<div class="content" style="min-height: 900px; min-width: 1000px; height: auto;">
			<iframe id="iframe" src="./employeeList.do"  onload="calcHeight();"></iframe>
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

		//<![CDATA[
		function calcHeight() {
			//find the height of the internal page

			var the_height = document.getElementById('iframe').contentWindow.document.body.scrollHeight;

			//change the height of the iframe
			document.getElementById('iframe').height = the_height;

			//document.getElementById('the_iframe').scrolling = "no";
			document.getElementById('iframe').style.overflow = "hidden";
		}
	</script>
</body>
</html>