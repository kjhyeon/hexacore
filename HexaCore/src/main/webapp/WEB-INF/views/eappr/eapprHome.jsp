<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
<meta charset="UTF-8">
<title>HEXACORE 전자결재 홈</title>
<style type="text/css">
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
* {
	font-family: 'Jeju Gothic', sans-serif;
}
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/eapprhome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/eapprhome.js"></script>
</head>
<body>
<div id="leftbox">
	<div id="left2" onclick="location.href='./docLists.do?state=7'">결재필요 문서<br><br><b>${docCounts.get("COUNT3")}</b> 건</div>
	<div id="right2" onclick="location.href='./docLists.do?state=0'">임시저장 문서<br><br><b>${docCounts.get("COUNT4")}</b> 건</div>
</div>
<div id="rightbox">
	<div id="left2" onclick="location.href='./docLists.do?state=1'">결재대기 문서<br><br><b>${docCounts.get("COUNT5")}</b> 건</div>
	<div id="right2" onclick="location.href='./docLists.do?state=2'">결재중 문서<br><br><b>${docCounts.get("COUNT6")}</b> 건</div>
</div>
<div id="quickview">
<h1>결재가 필요해요~</h1>
<c:if test="${lists ne null}">
	<c:forEach items="${lists}" var="Ddto" varStatus="num">
	<div id="boxs" onclick="location.href='./docDetail.do?seq=${Ddto.seq}'">
		<table>
			<tbody>
				<tr style="text-align: center;">
					<td style="width:50%;">${Ddto.title}</td><td  style="width:25%;">${Ddto.author}</td><td style="width:25%;">${Ddto.regdate}</td>
				</tr>
			</tbody>
		</table>
	</div>
	</c:forEach>
</c:if>
</div>

</body>
</html>