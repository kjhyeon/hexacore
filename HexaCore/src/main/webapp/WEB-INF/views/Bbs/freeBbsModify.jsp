<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글 수정페이지</title>
<script type="text/javascript">
	function BbsContent_modify_cancle(){
// 		alert("작성을 취소 하실건가요?");
	}
	
	function BbsContent_modify_complete(){
		alert("수정을 완료하였습니다.");
		
	}
</script>
</head>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<body>
<%@include file="/WEB-INF/header.jsp" %>
	${dto}
	
	<div class="container">
		<form action="./bbsDetail.do?seq=${dto.seq}" method="post">
  		<table class="table table-bordered">
    		<thead>
    			<tr>
        			<th colspan="2">
        				<input type="text" name="title" value="${dto.title}">
        			</th>
      			</tr>
    		</thead>
    		<tbody>
       			<tr>
        			<td>작성일</td>
        			<td>${dto.regdate}</td>
      			</tr>	
       			<tr>
        			<td>아이디</td>
        			<td>${dto.id}</td>
      			</tr>
       			<tr>
        			<td>성명</td>
        			<td>${dto.name}</td>
      			</tr>
      			<tr>
        			<td>조회수</td>
        			<td>${dtoo.views}</td>
     		 	</tr>
     		 	<tr>
     		 		<td>파일</td>
     		 		<td>
     		 			<c:forEach items="${list}" var="files">
	     		 			<input multiple="multiple" type="file" name="filename"> <hr>
	     		 			${files.ori_name} <input type="button" class="btn btn-default" name="deleteFile" onclick="delFile()" value="삭제">
     		 			</c:forEach>
     		 		</td>
     		 	</tr>
      			<tr>
        			<td>글내용</td>
        			<td>
						<textarea id="content" name="content">${dto.content}</textarea>
						<script type="text/javascript">
								CKEDITOR.replace('content'
        				        , {height: 500                                                  
             				    });
             				    
//  								CKEDITOR.instances.content.setData($("#con").val());
 								
						</script>
					</td>
      			</tr>
    		</tbody>
  		</table>
   	<hr>
  		
	    <div class="form-group">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-default" onclick="BbsContent_modify_complete()">수정완료</button>
	        <button type="reset" class="btn btn-default" onclick="BbsContent_modify_cancle()">초기화</button>
	      </div>
	    </div>
	    </form>
	</div>
   	
 </body>
   	
<%@include file="/WEB-INF/footer.jsp" %>
</html>