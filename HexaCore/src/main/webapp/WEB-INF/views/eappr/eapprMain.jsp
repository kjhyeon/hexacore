<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자결재 메인</title>
<link rel="stylesheet" href="./css/sidebar.css">
<script type="text/javascript" src="./js/eAppr_js.js"></script>
</head>

<body>
	<div id="wrap" class="wrap">
	
		<div class="subside">
			<c:forEach var="docType" items="${list}">
				<p onclick="previewAjax(${docType.type_seq})">${docType.name}</p>
			</c:forEach>
		</div>
		<div class="preview"></div>
		<div class="sidebar">
			<div class="menu01">문서 작성</div>
			<div>
			<div class="ddmenu">상신문서함</div>
				<div class="submenu" data-url="./docLists.do?state=0">임시저장문서</div>
				<div class="submenu" data-url="./docLists.do?state=1">결재대기문서</div>
				<div class="submenu" data-url="./docLists.do?state=2">결재중문서</div>
				<div class="submenu" data-url="./docLists.do?state=3">승인문서</div>
				<div class="submenu" data-url="./docLists.do?state=4">반려문서</div>
			</div>
			<div>
				<div class="ddmenu" >결재문서함(${cnt})</div>
				<div><input id="cnt" type="hidden" value="${cnt}"></div>
				<div class="submenu" id="appr" data-url="./docLists.do?state=7">결재필요문서</div>
				<div class="submenu" data-url="./docLists.do?state=6">결재중문서</div>
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
	<%@include file="../footer.jsp" %>
</body>
</html>