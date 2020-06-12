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
	문서요약보기${docCounts}<br>
	COUNT1: 참조문서함 개수 : ${docCounts.get("COUNT1")}<br>
	COUNT2: 결재문서함-결재중문서 개수 : ${docCounts.get("COUNT2")}<br>
	COUNT3: 결재문서함-결재필요문서개수 : ${docCounts.get("COUNT3")}<br>
	COUNT4: 상신문서함-임시저장문서 : ${docCounts.get("COUNT4")}<br>
	COUNT5:	상신문서함-결재대기문서 개수 : ${docCounts.get("COUNT5")}<br>
	COUNT6: 상신문서함-결재중문서 개수 : ${docCounts.get("COUNT6")}<br>
	COUNT7: 상신문서함-승인문서개수 : ${docCounts.get("COUNT7")}<br>
	COUNT8: 상신문서함-반려문서개수 : ${docCounts.get("COUNT8")}<br>
</body>
</html>