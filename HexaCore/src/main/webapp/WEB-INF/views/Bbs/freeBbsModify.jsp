<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>자유게시판 글 수정페이지</title>
<script type="text/javascript">
	function BbsContent_modify_cancle(){
// 		alert("작성을 취소 하실건가요?");
	}
	
	function BbsContent_modify_complete(){
		alert("수정을 완료하였습니다.");
	}
	
	$(document).ready(function() {
		var len = document.getElementsByName("files").length;
		if(len<3){
			var button = "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
			$("#file_td").append(button);
		}
// 		if(len ==)
	});
	
	function addinput(){
		var len1 = document.getElementsByName("files").length;
		var len2 = document.getElementsByName("filename").length;
		if(len1+len2<3){
			$("#addbutton").remove();
			var input = "<input type='file' name='filename'><br>";
			if(len1+len2<2){
				input += "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
			}
			$("#file_td").append(input);
		}
	}
	
	function delFile(val){
		$(val).parent().remove();
	}
</script>
</head>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<body>
<%@include file="/WEB-INF/header.jsp" %>
	${dto}
	${list}
	<div class="container">
		<form action="./bbsDetail.do?seq=${dto.seq}" method="post" enctype="multipart/form-data">
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
     		 		<td>파일</td>
		     		<td id="file_td">
		     		 	<c:forEach items="${list}" var="files">
		     		 	<span id="file">
			    	  		${files.ori_name}
			    	  		<input readonly="readonly" type="hidden" name="files" value="${files.name }" >
			    	  		<input type="button" class="btn btn-default" name="deleteFile" onclick="delFile(this)" value="삭제"><br>
		     		 	</span>
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