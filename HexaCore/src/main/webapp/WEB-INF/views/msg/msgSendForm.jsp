<%@page import="com.hexa.core.dto.MessageDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메세지 보내기 화면</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
	.col-sm-10{
		margin-top: 5px;
		margin-bottom: 5px;
	}
	.col-sm-2{
		margin-top: 12px;
		margin-bottom: 12px;
	}
	label{
		text-align: center;
	}
</style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function cancel(){
		location.href="./empInfo.do"
	}
	function search() {
		var treeWindow = window.open("./allTreeMsg.do", "사원목록", "width=300,height=400");
		
	}
	function fileChk(f){
	   // 파일 유형 제한
	      if ($(f).val != "") { 
	         var ext = $(f).val().split('.').pop().toLowerCase();
	         if ($.inArray(ext, ['gif', 'jpg', 'jpeg','doc','html','zip','rar','7z',
	                        'alz','egg','001','alz','lzh','tgz','tar','tlz','tbz',
	                        'jar','war','apk','ppt','xlsx','txt','hwp','png','mp3',
	                        'mp4','avi','docx','gif','java']) == -1) {
	            alert("등록할 수 없는 파일입니다.");
	            $(f).val(""); // input 파일명을 다시 지워주는 코드
	            return;
	         }
	      }
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
	function sendMessage(){
	      var temp = $("#title").val();
	      if(temp.length <1){
	    	  alert("제목을 입력해주세요");
	    	  $("#title").focus();
	    	  return false;
	      }
	      if($("#content").val().length <1){
	    	  alert("내용을 입력해주세요");
	    	  $("#content").focus();
	    	  return false;
	      }
	      if($("#receiver_id").val() == ''){
	    	  alert("수신자를 선택해주세요");
	    	  $("#receiver_id").focus();
	    	  return false;
	      }
	      
	      if(temp.indexOf("<")>=0){
	         temp = temp.replace(/</g ,"&lt;");
	      }
	      if(temp.indexOf(">")>=0){
	         temp = temp.replace(/>/g ,"&gt;");
	      }
	      
	      $("#title").val(temp);
	      
	      temp = $("#content").val();
	      if(temp.indexOf("<")>=0){
		         temp = temp.replace(/</g ,"&lt;");
		      }
		  if(temp.indexOf(">")>=0){
		         temp = temp.replace(/>/g ,"&gt;");
		      }
		 $("#content").val(temp);
		      
	     document.forms[0].submit();
	   }
</script>
<body>
<%@include file="../empInfoBar.jsp" %>
<div id="container" style="margin-left:25%">
	<form action="./insertMessage.do" method="post" style="width: 800px;" enctype="multipart/form-data">
		<div class="form-group">
       		<label class="control-label col-sm-2">보내는 사람</label>
          		<div class="col-sm-10">
                    <sec:authentication property="principal.username" var="user_id" />
                    <input type="text" class="form-control" style="width: 300px;" 
                    id="sender_id" value="${user_id}" readonly="readonly" name="sender_id">
          		</div>
     	</div>
     	<div class="form-group">
       		<label class="control-label col-sm-2">받는 사람</label>
        		<div class="col-sm-10" style="display: inline;">
               		<input type="text" class="form-control" style="width: 300px; float:left;" id="receiver_id" name="receiver_id" placeholder="받을 사람 입력" readonly="readonly">
          			<input type="button" style="float:left;" value="받을 사람 검색" onclick="search()" class="btn btn-info">
          		</div>
     	</div>
            
     	<div class="form-group">
       		<label class="control-label col-sm-2">제&nbsp;&nbsp;&nbsp;&nbsp; 목</label>
        		<div class="col-sm-10">
                	<input type="text" class="form-control" id="title" name="title" placeholder="Enter title">
          	 	</div>
     	</div>
     	<div class="form-group">
       		<label class="control-label col-sm-2">첨부 파일</label>
        		<div class="col-sm-10" style="border : none;" >
                	<input type="file" class="form-control" id="file" name="filename" style="border: none; box-shadow: 0" onchange="fileChk(this)">
          	 	</div>
     	</div>
     	<div class="form-group">
        	<label class="control-label col-sm-2">내&nbsp;&nbsp;&nbsp;&nbsp; 용</label>
          		<div class="col-sm-10">
                	<textarea class="form-control" rows="5" id="content" name="content" placeholder="Enter Content"></textarea>
          		</div>
     	</div>
     	<div class="form-group">
       		<div class="col-sm-offset-2 col-sm-10">
         		<input class="btn btn-success" type="button" value="보내기" onclick="sendMessage()">
         		<input class="btn btn-default" type="button" value="취소" onclick="cancel()">
       		</div>
     	</div>
	</form>     	
</div>
</body>
</html>