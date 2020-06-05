<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판</title>
</head>
<script type="text/javascript">
	function writeForm(){
		alert("글쓰기 작성 작동");
		location.href="./freeBbsInsert.do";
	}
	
	function multiDelete(){
		alert("글 다중삭제 작동");
		document.forms[0].submit();
	}

	function checkAll(bool){
		 alert("전체 선택");
		 var chkVals = document.getElementsByName("chkVal");
		 for (var i = 0; i < chkVals.length; i++) {
		      chkVals[i].checked = bool;
		 }
	}

	function chkbox(){
		 var n = 0;
		 var chkVals = document.getElementsByName("chkVal");
		 for (var i = 0; i < chkVals.length; i++) {
			if (chkVals[i].checked) {
				n++;
			}
		 }
		 if (n > 0) {
			document.getElementById("multidel").action = "./multiDel.do";
		 } else {
			alert("삭제할게 없네요?");
			return false;
		 }
	}
</script>
<body>
	<%@include file="/WEB-INF/header.jsp"%>
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
	${lists}
	<div class="container">
	<form action="./multiDel.do" method="POST" id="List" name="List" onsubmit="return chkbox()">
	  <table class="table table-bordered">
      		<tr>
      			<c:if test="${auth eq true }">
	        		<th>
    	    			<input type="checkbox" name="chkVal" onclick="checkAll(this.checked)">
        			</th>
      			</c:if>
        		<th>작성번호</th>
        		<th>아이디</th>
        		<th>제목</th>
        		<th>조회수</th>
        		<th>글상태</th>
        		<th>작성일</th>
      		</tr>
      <c:forEach var="dto" items="${lists}" varStatus="vr">
      		<tr>
      			<c:if test="${auth eq true }">
	      			<td>
    	  				<input type="checkbox" name="chkVal" value="${dto.seq}">
      				</td>
      			</c:if>
      			<td>${vr.count}</td>
      			<td>${dto.id}</td>
      			<td><a href="bbsDetail.do?seq=${dto.seq}">${dto.title}</a></td>
      			<td>${dto.views}</td>
      			<td>${dto.state}</td>
      			<td>${dto.regdate}</td>
      		</tr>
      </c:forEach>
  		</table>
		<input type="button" class="btn btn-default" id="insertForm" value="새 글 작성" onclick="writeForm()">
      	<c:if test="${auth eq true }">
      		<input type="submit" class="btn btn-default" id="multidel" value="글 다중 삭제" onclick="multiDelete()">
   		</c:if>
      </form>
	</div>
	
</body>
	<%@include file="/WEB-INF/footer.jsp" %>
</html>