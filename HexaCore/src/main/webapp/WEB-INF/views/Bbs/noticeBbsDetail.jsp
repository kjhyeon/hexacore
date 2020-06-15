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
<title>자유게시판 상세보기</title>
<style type="text/css">
	
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bbs.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
</head>
<script type="text/javascript">
	function NoticeContent_modify(){
		location.href="./noticeBbsModify.do?seq="+${seq.seq};
	}
	
	function Notice_del(){
		location.href="./Noticedel.do?seq="+${seq.seq};
	}
	
	function NoticeReplyWrite(){
		location.href="./noticeBbsReplyInsert.do?seq="+${seq.seq};
	}
	
	function writeComment(){
		document.forms[1].submit();
	}
	
	function deleteComment(seq) {
		location.href="./noticeCommentDelete.do?seq="+seq+"&parent_seq="+$("#seq").val();
	}
	
	function goPage(page){
		var location = "./noticeBbsDetail.do?seq="+${seq.seq}+"&page="+page; 
		document.location.href=location;
	}
	
</script>
<body>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"/>
<sec:authentication property="principal.username" var="sessionId"/>
	
	<div class="Detail_container">
		<form action="./Noticedel.do" method="post">
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
     		 				<a href="./Noticedownload.do?name=${files.name}" >${files.ori_name}</a> | ${files.f_size }byte
     		 			</c:forEach>
     		 		</td>
     		 	</tr>
      			<tr>
        			<td>글내용</td>
        			<td>${seq.content}</td>
      			</tr>
    		</tbody>
  		</table>
	    <div class="Detail_form_group">        
	      <div class="Detail_form_Button">
	        <input type="hidden" name="seq" value="${seq.seq}" id="seq">
	        
	        <c:if test="${(sessionId eq seq.id) || auth eq true}">
	        	<input id="Notice_Detail_Delete" type="submit" class="btn btn-default" value="삭제" onclick="Notice_del()">
	        	<input id="Notice_Detail_Modify" type="button" class="btn btn-default" onclick="NoticeContent_modify()" value="수정">
	        	<input id="Notice_Detail_Reply" type="button" class="btn btn-default" onclick="NoticeReplyWrite()" value="답글">
	        </c:if>
	      </div>
	    </div>
	   </form>
	</div>
	<br>
	<hr>
	<div class="comment_container">	
	<form action="./noticeCommentWrite.do" method="post">
		<div class="reply_Comment">
			<textarea id="Detail_textarea" rows="10" cols="10" name=content placeholder="댓글을 입력해주세요..."></textarea>
		</div>
		<input type="hidden" name="parent_seq" value="${seq.seq}">
	        
	
		<div class="Detail_Comment_Write">        
      		<div class="Detail_Comment_Button">
        		<input type="button" class="btn btn-default" id=write_Comment value="작성" onclick="writeComment()">
      		</div>
    	</div>
	</form>
	</div>
	<br>
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
							<c:if test="${(sessionId eq commentList.id) || auth eq true}">
								<input type="image" src="./image/x.png" alt="XBox" onclick="deleteComment('${commentList.seq}')">
							</c:if>
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
		<c:if test="${lists.size() ne 0}">
		<input type="hidden" name="index" id="index" value="${row.index }">
		<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum }">
		<input type="hidden" name="listNum" id="listNum" value="${row.listNum }">
     	 <div class="center" style="text-align: center; position: relative;">
			<ul class="pagination">
				<li><a href="#" onclick="return goPage(${0});">&laquo;</a></li>
				<li><a href="#" onclick="return goPage(${row.index-1});">&lt;</a></li>
				<c:forEach var="i" begin="${row.pageNum }" end="${row.count}" step="1">
					<li><a href="#" onclick="goPage(${i-1})">${i}</a></li>
				</c:forEach>
				<li><a href="#" onclick="return goPage(${row.index+1});" >&gt;</a></li>
				<li><a href="#" onclick="return goPage(${row.lastPage-1});" >&raquo;</a></li>
			</ul>
		</div>
		</c:if>
	</div>
</body>
</html>