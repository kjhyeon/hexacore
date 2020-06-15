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
<title>공지게시판</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bbs.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
<script type="text/javascript">
	function writeForm(){
		location.href="./fileBbsInsert.do";
	}
	
	function multiDelete(){
		document.forms[0].submit();
	}
	
	function checkAll(bool){
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
			document.getElementById("multidel").action = "./fileMultiDel.do";
		 } else {
			alert("삭제할게 없네요?");
			return false;
		 }
	}
	
	function goPage(page){
		var location = "./fileBbsMain.do?page="+page; 
		if($("#ori_keyword").val()!=null&&$("#ori_keyword").val().trim()!=''){
			location += "&keyword="+$("#ori_keyword").val()+"&type="+$("#ori_type").val();			
		}
		document.location.href=location;
	}
	
	function goSearch(){
		var location = "./fileMain.do?"; 
		location += "keyword="+$("#keyword_Search").val()+"&type="+$("#type_Select").val();			
		document.location.href=location;
	}
</script>
<body>

<div id="Main_Top_Div">
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
	 <div class="form-group input-group" id="Main_Search_Text">
			<span class="input-group-btn"> 
				<select name="type" class="form-control" id="type_Select">
					<option value="title/con">제목+내용</option>
					<option value="title">제목</option>
					<option value="content">내용</option>
					<option value="author">글쓴이</option>
				</select>
			</span>
			<span> 
				<input type="text" class="form-control" placeholder="Search.." name="keyword" id="keyword_Search" value="${keyword }"> 
			</span>
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" onclick="goSearch()" id="type_Finder">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</span>
	</div>
	
	<div class="Mainbbs_container">
	<input type="hidden" id="ori_keyword" value="${keyword}">
	<input type="hidden" id="ori_type" value="${type }">
	<form action="./fileMultiDel.do" method="POST" id="List" name="List" onsubmit="return chkbox()">
	<input type="hidden" name="auth_check" value="${auth}">
	  <table class="table table-bordered" id="Main_Table">
      		<tr class="Main_Thead">
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
      		<tr class="Main_Tbody">
      			<c:if test="${auth eq true }">
	      			<td id="tdChk">
    	  				<input type="checkbox" name="chkVal" value="${dto.seq}">
      				</td>
      			</c:if>
      			<td id="td1">${dto.seq}</td>
      			<td id="td2">${dto.id}</td>
      			<td><a href="./fileBbsDetail.do?seq=${dto.seq}" style="text-decoration: none; color: black; font-weight: bold;">
      				<c:forEach begin="0" end="${dto.bbs_depth}">
      					&nbsp;&nbsp;&nbsp;
      				</c:forEach>
      				<c:if test="${dto.reply_seq > 0 }">
      					<img alt="에~~~로우" src="./image/reply_arrow.png">
      				</c:if>
      				${dto.title }&nbsp;&nbsp;
 	     			<c:if test="${dto.c_count != 0}">
    		  			<b id="Main_Title_Count" >✎[${dto.c_count}]</b>&nbsp;&nbsp;
      				</c:if>
      				<c:if test="${dto.f_count != 0}">
      					<img alt="FileImg" src="./image/file.png" style="width: 13px; height: 13px;">
      				</c:if>
      			</a></td>
      			<td id="td4">${dto.views}</td>
      			<c:if test="${auth eq true}">
 	    	 		<td id="td5">${dto.state}</td>
      			</c:if>
      			<td id="td6">${dto.regdate}</td>
      		</tr>
      </c:forEach>
  		</table>
      	<c:if test="${auth eq true }">
			<input type="button" class="btn btn-default" id="insertForm" value="새 글 작성" onclick="writeForm()">
      		<input type="submit" class="btn btn-danger" id="multidel" value="글 다중 삭제" onclick="multiDelete()">
   		</c:if>
      </form>
      
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
</div>

</body>
</html>