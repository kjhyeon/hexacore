<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<body>

<form action="./test.do" method="get">

<textarea class="form-control" id="p_content" name="ckedit"></textarea>
<script type="text/javascript">
 CKEDITOR.replace('p_content'
                , {height: 500                                                  
                 });
</script>

<input type="submit" value="제출">
</form>

</body>
</html>