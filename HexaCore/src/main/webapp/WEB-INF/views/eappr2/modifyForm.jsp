<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자문서 작성</title>
<link rel="stylesheet" href="./css/doc.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<body>
	<form id="insertdoc" name="insertdoc" method="POST">
		<div class="rightBox">
			<div class="apprBox">
				<input class="apprbtn" type="button" onclick="apprSearch()" value="결재선 선택/수정"><br>
				<h3>결재자</h3>
				<div class="apprs">
					<table class="apprtable">
						<tr class="index">
							<td>부서직급</td>
							<td>이름</td>
							<td>종류</td>
							<td>상태</td>
						</tr>
						<c:forEach var="Adto" items="${approvalLine}" varStatus="i">
								<c:if test="${i.index < 3}">
									<tr>
										<td>${Adto.duty}<input type="hidden" name='lists[${i.index}].duty' value='${Adto.duty}'></td>
										<td>${Adto.name}<input type="hidden" name='lists[${i.index}].name' value='${Adto.name}'></td>
										<td>
											${Adto.appr_kind}
											<input type="hidden" name='lists[${i.index}].id' value='${Adto.id}'>
											<input type="hidden" name='lists[${i.index}].turn' value='${i.index+1}'>
										</td>
										<c:if test="${Adto.chk ne null}">
											<c:choose>
												<c:when test="${Adto.chk eq 'T'}">
													<td>승인</td>
												</c:when>
												<c:when test="${Adto.chk eq 'R'}">
													<td>반려</td>
												</c:when>
												<c:when test="${Adto.chk  eq 'F'}">
													<td>미결재</td>
												</c:when>
											</c:choose>
										</c:if>
									</tr>
								</c:if>
							</c:forEach>
					</table>	
				</div>
				<br><hr>
				<h3>참조자</h3>
				<div class="refers">
					<table class="refertable">
						<tr class="index">
							<td>부서직급</td>
							<td>이름</td>
						</tr>
						<c:forEach var="Adto" items="${approvalLine}" varStatus="i">
								<c:if test="${i.index > 2}">
									<tr>
										<td>${Adto.duty}<input type="hidden" name='lists[${i.index}].duty' value='${Adto.duty}'></td>
										<td>
											${Adto.name}
											<input type="hidden" name='lists[${i.index}].name' value='${Adto.name}'>
											<input type="hidden" name='lists[${i.index}].appr_kind' value='${Adto.appr_kind}'>
											<input type="hidden" name='lists[${i.index}].id' value='${Adto.id}'>
											<input type="hidden" name='lists[${i.index}].turn' value='0'>
										</td>
									</tr>
								</c:if>
							</c:forEach>
					</table>
				</div>
			</div>
			<div class="btnBox">
				<input type="hidden" name="type_seq" value="${typeDto.type_seq}">
				<input type="button" value="작성 취소" onclick="cancelwrite()">
				<input type="button" value="초기화" onclick="resetDoc()">
				<input type="button" value="임시저장" onclick="saveUpdoc()">
				<input type="button" value="바로상신" onclick="report()">
			</div>
		</div>
		
		<div class="leftBox">
			<h1>${typeDto.name}</h1>
			<table id="docuHead">
				<tr>
					<th>기안자</th>
					<td>${Ddto.author}</td>
					<td><input type="hidden" id="author" name="author" value="${drafter}"></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input id="inputTitle" type="text" name="title" value="${Ddto.title}"></td>
				</tr>
			</table>
			<table id="apprSign">
			</table>
			<br>
			<textarea id="p_content" name="content" rows="5" cols="50">${Ddto.content}</textarea>
			<script type="text/javascript">CKEDITOR.replace('p_content', {height : 700});</script>
		</div>
	</form>
</body>
</html>