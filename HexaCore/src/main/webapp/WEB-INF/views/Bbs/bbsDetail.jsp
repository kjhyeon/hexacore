<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 상세보기</title>
<style type="text/css">
	textarea{
		height: 150px;
		width: 750px; 
		word-break:break-all;
	}
	.commnet_List{
/* 		border: 2px solid black; */
		height: 150px;
		width: 750px;
		margin: auto;
	}
	#comment_ListTable{
		
	}
	input[type=image]{
		position: absolute;
		left: 1400px;
	}
</style>
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
	
	function BbsReplyWrite(){
		alert("답글");
		location.href="./freeBbsReplyInsert.do?seq="+${seq.seq};
	}
	
	function writeComment(){
		document.forms[2].submit();
	}
	
	function deleteComment(seq) {
		alert("답글 삭제");
		location.href="./commentDelete.do?seq="+seq+"&parent_seq="+$("#seq").val();
	}
	
</script>
<body>
	<%@include file="/WEB-INF/header.jsp"%>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"/>
<sec:authentication property="principal.username" var="sessionId"/>
	
	${seq}
	
	<div class="container">
		<form action="./del.do" method="post">
		<input type="hidden" name="auth_check" value="${auth}">
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
     		 		<td>파일</td>
     		 		<td>
     		 			<c:forEach items="${list}" var="files" varStatus="n">
     		 				<a href="./download.do?name=${files.name}" >${files.ori_name}</a> | ${files.f_size }byte
     		 			</c:forEach>
     		 		</td>
     		 	</tr>
      			<tr>
        			<td>글내용</td>
        			<td>${seq.content}</td>
      			</tr>
    		</tbody>
  		</table>
	    <div class="form-group">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <input type="hidden" name="seq" value="${seq.seq}" id="seq">
	        
	        <c:if test="${(sessionId eq seq.id) || auth eq true}">
	        	<input type="button" class="btn btn-default" onclick="BbsContent_modify()" value="수정">
	        	<input type="submit" id="del" class="btn btn-default" value="삭제" onclick="del()">
	        </c:if>
	        	<input type="button" class="btn btn-default" onclick="BbsReplyWrite()" value="답글">
	      </div>
	    </div>
  		
	   </form>
	</div>
	<hr>
	
	<div class="comment_container" style="text-align: center;">	
	<form action="./commentWrite.do" method="post">
		<div class="reply_Comment">
			<textarea rows="10" cols="10" name=content placeholder="댓글을 입력하라고"></textarea>
		</div>
		<input type="hidden" name="parent_seq" value="${seq.seq}">
	        
	
		<div class="form-group">        
      		<div class="col-sm-offset-2 col-sm-10">
        		<input type="button" id=write_Comment value="작성" onclick="writeComment()">
      		</div>
    	</div>
	</form>
	</div>
		
	<hr>
	<hr>
	
	<div class="commnet_List" >
		<c:forEach items="${lists}" var="commentList">
			<table id="comment_ListTable">
				<thead>
					<tr>
						<th style="border-right: 1px solid gray;">
							<b>${commentList.id}</b>
							
						</th>
						<td style="padding-left: 10px;">
							${commentList.content}
						</td>
						<td>
							<input type="image" src="./image/x.png" alt="XBox" onclick="deleteComment('${commentList.seq}')">
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="border-right: 1px solid gray; padding-right: 20px;">
							${fn:substring(commentList.regdate,0,10)}
						</td>
					</tr>
				</tbody>
			</table>
			<hr>
		</c:forEach>
	</div>
	
</body>
</html>