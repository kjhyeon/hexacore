<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 상세보기</title>
</head>
<script type="text/javascript">
	function BbsContent_modify(){
		alert("수정하시겠습니까?");
		location.href="./freeBbsModify.do?seq="+${seq.seq};
	}
	
	function del(){
		alert("삭제");
		location.href="./del.do?seq="+${seq.seq};
	}
</script>
<body>
	<%@include file="/WEB-INF/header.jsp"%>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"/>
<sec:authentication property="principal.username" var="sessionId"/>
	
	${seq}
	
	<div class="container">
		<form action="./del.do" method="post">
  		<table class="table table-bordered">
    		<thead>
      			<tr>
        			<th colspan="2"><h1>${seq.title}</h1></th>
      			</tr>
    		</thead>
    		<tbody>
       			<tr>
        			<td>작성일</td>
        			<td>${seq.regdate}</td>
      			</tr>	
       			<tr>
        			<td>아이디</td>
        			<td>${seq.id}</td>
      			</tr>
       			<tr>
        			<td>성명</td>
        			<td>${seq.name}</td>
      			</tr>
      			<tr>
        			<td>조회수</td>
        			<td>${seq.views}</td>
     		 	</tr>
      			<tr>
        			<td>글내용</td>
        			<td>${seq.content}</td>
      			</tr>
    		</tbody>
  		</table>
	    <div class="form-group">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <input type="hidden" name="seq" value="${seq.seq}">
	        
	        <c:if test="${(sessionId eq seq.id) || auth eq true}">
	        	<input type="button" class="btn btn-default" onclick="BbsContent_modify()" value="수정">
	        	<input type="submit" id="del" class="btn btn-default" value="삭제" onclick="del()">
	        </c:if>
	      </div>
	    </div>
	   </form>
	</div>
   	<hr>
   		 	
	
<div class="comment_container">	
	<div class="reply_Comment">
		<textarea rows="10" cols="10" 
			style="padding-left:5;padding-right:50;padding-bottom:50;padding-top:50;
				   word-break:break-all;width:50%">
			댓글을 입력해 주세요
		</textarea>
	</div>
	<hr>
	
	<div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">작성</button>
        <button type="submit" class="btn btn-default">수정</button>
        <button type="submit" class="btn btn-default">삭제</button>
      </div>
    </div>
</div>	
	
</body>
	<%@include file="/WEB-INF/footer.jsp"%>
</html>