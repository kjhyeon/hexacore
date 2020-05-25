<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./javascript/jquery-3.4.1.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function formaa(val) {
// 		alert(val);
		$.ajax({
			url : "./formTest.do",
			type:"post",
// 			dataType:"json",
			data:{"seq":val},
			success:function(msg){
// 				alert(msg);
// 				document.getElementById("p_content").innerHTML=msg;
				CKEDITOR.instances.p_content.setData(msg);
			},
			error : function(){
				alert("잘못된 요청인데스");
			}
		});
	}
</script>
</head>
<body>
	<div id="select">
		<span>
			<select class="btn btn-primary" id="list" name="list" onchange="formaa(this.value)">
				<option value="1">양식1</option>
				<option value="2">양식2</option>
			</select>
		</span>
	</div>
	
	<div id ="contationer">
		<textarea rows="100" cols="100" id="p_content"></textarea>
		<script type="text/javascript">
 CKEDITOR.replace('p_content'
                , {height: 500                                                  
                 });
</script>
	</div>
</body>
</html>