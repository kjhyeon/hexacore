<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="./DocTypeUpdate.do" method="POST">
		<table>
			<tr>
				<td>문서양식 이름:</td><td><input type="text" name="name" id="name" maxlength="50"></td>
			</tr>
			<tr>
				<td>카테고리:</td>
				<td>
					<select name="category" id="category">
						<option>옵션1</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>content:</td>
				<td>
					<textarea name="content" rows="50" cols="100"></textarea>
					<script type="text/javascript">
						CKEDITOR.replace(
								'p_content', { 
									height : 500
							});
					</script>
				</td>
			</tr>
		</table>
		<input type="submit" value="수정">
	</form>
</body>
</html>