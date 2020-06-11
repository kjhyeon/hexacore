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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>자유게시판 글 수정페이지</title>
<script type="text/javascript">
	
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
			var input = "<input type='file' name='filename' onchange='fileChk(this)'><br>";
			if(len1+len2<2){
				input += "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
			}
			$("#file_td").append(input);
		}
	}
	
	function delFile(val){
		$(val).parent().remove();
	}
	
	function fileChk(f){
		// 파일 유형 제한
			if ($(f).val != "") {  // input에 name이 filename 불러오기
				var ext = $(f).val().split('.').pop().toLowerCase();
				if ($.inArray(ext, ['gif','jpg','jpeg','doc','html','zip','rar','7z',
									'alz','egg','001','alz','lzh','tgz','tar','tlz','tbz',
									'jar','war','apk','ppt','xlsx','txt','hwp','png','mp3',
									'mp4','avi','docx','gif','java'])== -1) {
					alert("등록할 수 없는 파일입니다.");
					$(f).val(""); // input 파일명을 다시 지워주는 코드
					return;
				}
			}
		// 파일 용량 제한
			if (f.value != ""){
		 		var fileSize = f.files[0].size;
		 		var maxSize = 108*1024*1024 // 108MB 제한
		 		if(fileSize > maxSize){
		 			alert("첨부파일 사이즈는 108MB 이내로 등록가능합니다.");
		 			$(f).val("");
		 			return
		 		}
		 	}
		}
	
	function writeComplete(){
		var temp = $("#title").val();
		if(temp.indexOf("<")>=0){
			temp = temp.replace(/</g ,"&lt;");
		}
		if(temp.indexOf(">")>=0){
			temp = temp.replace(/>/g ,"&gt;");
		}
		$("#title").val(temp);
		document.forms[1].submit();
	}
	
	
</script>
</head>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<body>
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
			    	  		<input readonly="readonly" type="hidden" name="files" value="${files.name}" >
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
	        <a href="./bbsMain.do">
	        	<button type="button" class="btn btn-default" onclick="BbsContent_modify_cancle()">작성취소</button>
	        </a>
	      </div>
	    </div>
	    </form>
	</div>
   	
 </body>
   	
</html>