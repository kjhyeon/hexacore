<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function writeComplete(){
		alert("작성완료");
		location.href="/bbsMain.do";
	}
	function writeCancle(){
		alert("작성취소");
	}
</script>
<body>
<%@include file="/WEB-INF/header.jsp"%>
	<sec:authentication property="principal.username" var="sessionId"/>
	${Ldto}
		
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
    					<input type="text" id="title" name="title" value="제목">
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
      				<td>
      					<input multiple="multiple" type="file" name="filename">
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
   	<hr>
  		
	    <div class="form-group">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button class="btn btn-default" onclick="writeComplete()">작성완료</button>
	        <a href="./bbsMain.do">
		        <input type="button" class="btn btn-default" value="작성취소" onclick="writeCancle()">
	        </a>
	      </div>
	    </div>
	    </form>
	</div>
	
</body>
<%@include file="/WEB-INF/footer.jsp"%>
</html>