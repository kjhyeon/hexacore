<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>	
<!DOCTYPE html>	
<html>	
<head>	
<meta charset="UTF-8">	
<title>전자문서 작성</title>	
<link rel="stylesheet" href="./css/doc.css">	
</head>	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>	
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>	
<script type="text/javascript" src="./js/docwrite.js"></script>	
<body>	
	<form id="insertdoc" name="insertdoc" method="POST" enctype="multipart/form-data">	
		<div class="rightBox">	
			<div class="apprBox">	
				<input class="apprbtn" type="button" onclick="apprSearch()" value="결재선 선택/수정"><br>	
				<h3>결재자</h3>	

				<div class="apprs">	
					<table class="apprtable">	
						<tbody>	
							<tr class="index">	
								<td>부서직급</td><td>이름</td><td>종류</td><td>순서</td>	
							</tr>	
						</tbody>	
					</table>		
				</div>	
				<br><hr>	
				<h3>참조자</h3>	
				<div class="refers">	
					<table class="refertable">	
						</tbody>	
							<tr class="index">	
								<td>부서직급</td><td>이름</td>	
							</tr>	
						</tbody>	
					</table>	
				</div>	
			</div>
			<table class="filetable">
				<tr class="index">
					<td>첨부파일</td>
				</tr>
				<tr>
					<td id="file_td"></td>
				</tr>
			</table>
			<div class="btnBox">	
				<input type="hidden" name="type_seq" value="${typeDto.type_seq}">	
				<input type="button" value="작성 취소" onclick="cancelwrite()">	
				<input type="button" value="초기화" onclick="resetDoc()">	
				<input type="button" value="임시저장" onclick="savedoc()">	
				<input type="button" value="바로상신" onclick="report()">	
			</div>	
		</div>	

		<div class="leftBox">	
			<table id="docuHead">	
				<tr>	
					<th>기안자</th>	
					<td><sec:authorize access="isAuthenticated()">	
							<sec:authentication property="principal.username" var="drafter"/>	
							${drafter}	
						</sec:authorize>	
						<input type="hidden" id="author" name="author" value="${drafter}">	
					</td>	
				</tr>	
				<tr>	
					<th>제목</th>	
					<td><input id="inputTitle" type="text" name="title"></td>	
				</tr>	
			</table>
			<br>
			<hr>	
			<h1>${typeDto.name}</h1>
			<textarea id="p_content" name="content" rows="5" cols="50">${typeDto.content}</textarea>	
			<script type="text/javascript">CKEDITOR.replace('p_content', {height : 700});</script>	
		</div>	
	</form>	
</body>	
</html> 