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
<title>HEXACORE 게시판</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bbs.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	// 스크립트 < > 제약조건 
	$(document).ready(function() {
		var button = "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
		$("#file_td").append(button);
	});
	function addinput(){
		var len = document.getElementsByName("filename").length;
		if(len<3){
			$("#addbutton").remove();
			var input = "<input type='file' name='filename' onchange='fileChk(this)'><br>";
			if(len<2){
				input += "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
			}
			$("#file_td").append(input);
		}
	}
</script>
<body>
	<sec:authentication property="principal.username" var="sessionId"/>
	<div class="container">
		<form action="#" method="post" id="writeForm" name="writeForm" enctype="multipart/form-data">
  		<table class="table table-bordered">
    		<thead>
      			<tr>
        			<th colspan="2">
        				<h1>글작성</h1>
        			</th>
      			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td>제목</td>
    				<td>
    					<input type="text" id="title" name="title" placeholder="제목" maxlength="100">
    				</td>
    			</tr>
       			<tr>
        			<td>아이디</td>
        			<td>
        				${Ldto.username}
        			</td>
      			</tr>
      			<tr>
      				<td>파일</td>
      				<td id="file_td">
      				</td>
      			</tr>
      			<tr>
        			<td>글내용</td>
        			<td>
						<textarea id="content" name="content"></textarea>
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
  		
	    <div class="form-group">        
	      <div class="Insert_Button" style="margin-left: 970px;">
	        <input type="button" class="btn btn-default" onclick="writeComplete()" value="작성완료">
	        <a href="./freeBbsMain.do">
		        <input type="button" class="btn btn-default" value="작성취소" onclick="writeCancle()">
	        </a>
	      </div>
	    </div>
	    </form>
	</div>
</body>
</html>