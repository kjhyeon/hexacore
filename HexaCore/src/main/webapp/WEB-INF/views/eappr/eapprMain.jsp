<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HEXACORE 전자결재</title>
<link rel="stylesheet" href="./css/sidebar.css">
<script type="text/javascript" src="./js/eAppr_js.js"></script>
</head>

<body>
	<div id="wrap" class="wrap">
	
		<div class="subside">
			<c:forEach var="docType" items="${docTypeList}">
				<p onclick="previewAjax(${docType.type_seq})">${docType.name}</p>
			</c:forEach>
		</div>
		<div class="preview"></div>
		<div class="sidebar">
			<div class="menu01">문서 작성</div>
			<div>
			<div class="ddmenu">상신문서함&nbsp;&nbsp;<img src="./image/ArrowDrop.png" style="height: 10px;"></div>
				<div class="submenu" data-url="./docLists.do?state=0">임시저장문서</div>
				<div class="submenu" data-url="./docLists.do?state=1">결재대기문서</div>
				<div class="submenu" data-url="./docLists.do?state=2">결재중문서</div>
				<div class="submenu" data-url="./docLists.do?state=3">승인문서</div>
				<div class="submenu" data-url="./docLists.do?state=4">반려문서</div>
			</div>
			<div>
				<div class="ddmenu" >결재문서함&nbsp;&nbsp;<img src="./image/ArrowDrop.png" style="height: 10px;"></div>
				<div class="submenu" data-url="./docLists.do?state=6">결재한문서</div>
				<div class="submenu" id="appr" data-url="./docLists.do?state=7">결재필요문서</div>
			</div>
			<div id="referMenu" class="menu" data-url="./docLists.do?state=8">참조문서함</div>
		</div>
	<%@include file="../header.jsp" %>
	<script type="text/javascript" src="./js/sidebar_js.js"></script>
		<div class="contatiner">
			<div class="content">
			<iframe id="iframe" src="./goEapprHome.do"></iframe>
			</div>
		</div>
	</div>
</body>
</html>