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
	function replyComplete(){
		alert("작성완료");
		location.href="./bbsMain.do";
	}
</script>
<body>
<%@include file="/WEB-INF/header.jsp"%>
	<sec:authentication property="principal.username" var="sessionId"/>
	${Ldto}
	<div class="container">
		<form action="#" method="post" id="replyForm" name="replyForm">
		<input type="hidden" name="seq" value="${seq }">
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
	        <button class="btn btn-default" onclick="replyComplete()">답글작성완료</button>
	        <button class="btn btn-default" onclick="">취소</button>
	      </div>
	    </div>
	    </form>
	</div>
	
</body>
<%@include file="/WEB-INF/footer.jsp"%>
</html>