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
	
	function goPage(page){
		var location = "./bbsMain.do?page="+page; 
		if($("#ori_keyword").val()!=null&&$("#ori_keyword").val().trim()!=''){
			location += "&keyword="+$("#ori_keyword").val()+"&type="+$("#ori_type").val();			
		}
		document.location.href=location;
	}
	
	function goSearch(){
		var location = "./bbsMain.do?"; 
		location += "keyword="+$("#keyword").val()+"&type="+$("#type").val();			
		document.location.href=location;
	}
	
</script>
<body>
	<%@include file="/WEB-INF/header.jsp"%>
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
	<div class="container">
	<input type="hidden" id="ori_keyword" value="${keyword }">
	<input type="hidden" id="ori_type" value="${type }">
	<form action="./multiDel.do" method="POST" id="List" name="List" onsubmit="return chkbox()">
	<input type="hidden" name="auth_check" value="${auth}">
	  <table class="table table-bordered">
      		<tr>
      			<c:if test="${auth eq true}">
	        		<th>
    	    			<input type="checkbox" name="chkVal" onclick="checkAll(this.checked)">
        			</th>
      			</c:if>
        		<th>작성번호</th>
        		<th>아이디</th>
        		<th>제목</th>
        		<th>조회수</th>
        		<c:if test="${auth eq true}">
        			<th>글상태</th>
        		</c:if>
        		<th>작성일</th>
      		</tr>
      <c:forEach var="dto" items="${lists}" varStatus="vr">
      		<tr>
      			<c:if test="${auth eq true }">
	      			<td>
    	  				<input type="checkbox" name="chkVal" value="${dto.seq}">
      				</td>
      			</c:if>
      			<td>${dto.seq}</td>
      			<td>${dto.id}</td>
      			<td><a href="bbsDetail.do?seq=${dto.seq}">${dto.title}</a></td>
      			<td>${dto.views}</td>
      			<c:if test="${auth eq true}">
 	    	 		<td>${dto.state}</td>
      			</c:if>
      			<td>${dto.regdate}</td>
      		</tr>
      </c:forEach>
  		</table>
		<input type="button" class="btn btn-default" id="insertForm" value="새 글 작성" onclick="writeForm()">
      	<c:if test="${auth eq true }">
      		<input type="submit" class="btn btn-default" id="multidel" value="글 다중 삭제" onclick="multiDelete()">
   		</c:if>
      </form>
      <div class="form-group input-group">
			<span class="input-group-btn"> 
				<select name="type" class="form-control" id="type">
					<option value="title/con">제목+내용</option>
					<option value="title">제목</option>
					<option value="content">내용</option>
					<option value="author">글쓴이</option>
			</select>
			</span> 
				<input type="text" class="form-control" placeholder="Search.." name="keyword"
				style="position: relative; float: right;" id="keyword" value="${keyword }"> 
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" onclick="goSearch()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</span>
		</div>
      <input type="hidden" name="index" id="index" value="${row.index }">
		<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum }">
		<input type="hidden" name="listNum" id="listNum" value="${row.listNum }">
     	 <div class="center" style="text-align: center; position: relative;">
			<ul class="pagination">
				<li><a href="#" onclick="return goPage(${0});">&laquo;</a></li>
				<li><a href="#" onclick="return goPage(${row.index-1});">&lt;</a></li>
				<c:forEach var="i" begin="${row.pageNum }" end="${row.count }" step="1">
					<li><a href="#" onclick="goPage(${i-1})">${i }</a></li>
				</c:forEach>
				<li><a href="#" onclick="return goPage(${row.index+1});" >&gt;</a></li>
				<li><a href="#" onclick="return goPage(${row.lastPage-1});" >&raquo;</a></li>
			</ul>
		</div>
	</div>
	<script type="text/javascript">

	$(document).ready(
			function() {
				var op = $("select[id=type]>option");
				for (var i = 0; i < op.length; i++) {
					if(op.eq(i).val() == '${type}')
					{
						op.eq(i).attr("selected",true);
					}
				}
			}
		)
	</script>
</body>
</html>