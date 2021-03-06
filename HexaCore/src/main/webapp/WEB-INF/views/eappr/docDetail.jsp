<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<meta charset="UTF-8">
<title>HEXACORE 문서상세조회</title>
<link rel="stylesheet" href="./css/doc.css">
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
<script type="text/javascript" src="./js/eAppr_js.js"></script>

<body>
	<div id="ajaxModify">
		<form id="formDoc" enctype="multipart/form-data">
			<div class="rightBox">
				<div class="apprBox">
					<h3>결재자</h3>
					<div class="apprs">
						<table class="apprtable">
						<tbody>
							<tr class="index">
								<td>부서직급</td>
								<td>이름</td>
								<td>종류</td>
								<td>상태</td>
							</tr>
							<c:forEach var="Adto" items="${apprList}" varStatus="i">
								<c:if test="${i.index < 3}">
									<tr>
										<td>${Adto.duty}<input type="hidden" name='lists[${i.index}].duty' value='${Adto.duty}'></td>
										<td>${Adto.name}<input type="hidden" name='lists[${i.index}].name' value='${Adto.name}'></td>
										<td>
											${Adto.appr_kind}
											<input type="hidden" name='lists[${i.index}].seq' value='${Adto.seq}'>
											<input type="hidden" name='lists[${i.index}].appr_kind' value='${Adto.appr_kind}'>
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
							</tbody>
						</table>
					</div>
					<br><hr>
					<h3>참조자</h3>
					<div class="refers">
						<table class="refertable">
						</tbody>
							<tr class="index">
								<td>부서직급</td>
								<td>이름</td>
							</tr>
							<c:forEach var="Adto" items="${apprList}" varStatus="i">
								<c:if test="${i.index > 2}">
									<tr>
										<td>${Adto.duty}<input type="hidden" name='lists[${i.index}].duty' value='${Adto.duty}'></td>
										<td>
											${Adto.name}
											
											<input type="hidden" name='lists[${i.index}].seq' value='${Adto.seq}'>
											<input type="hidden" name='lists[${i.index}].name' value='${Adto.name}'>
											<input type="hidden" name='lists[${i.index}].appr_kind' value='${Adto.appr_kind}'>
											<input type="hidden" name='lists[${i.index}].id' value='${Adto.id}'>
											<input type="hidden" name='lists[${i.index}].turn' value='0'>
										</td>
									</tr>
								</c:if>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="btnBox">
				<c:if
					test="${name eq Ddto.author && (Ddto.state eq '0' || Ddto.state eq '1') && (Ddto.appr_turn ne '2')}">
					<c:if test="${Ddto.state eq '0'}">
						<input type="button" id="upApprDoc" value="상신"	onclick="upApprDocc()">
					<input type="button" id="modifyDoc" value="수정"		onclick="modifyFormDoc('${Ddto.seq}','${flist}')">
					<input type="button" id="deleteDoc" value="삭제"		onclick="deleteDocc('${Ddto.seq}','${Ddto.state}')">
					</c:if>
					<c:if test="${Ddto.state eq '1'}">
						<input type="button" id="upApprDoc" value="상신취소"	onclick="upApprDocc('${Ddto.seq}','${Ddto.state}')">
					</c:if>
				</c:if>
				<c:if
					test="${name ne Ddto.author && turn != '0' && turn eq Ddto.appr_turn}">
					<input type="button" value="결재" data-toggle="modal"		data-target="#apprDoc" data-backdrop='static' data-keyboard='false'	
																		onclick="apprDoc('${Ddto.seq}','${Ddto.appr_turn}','${Ddto.a_turn}','${Ddto.state}')">
				</c:if>
				</div>
				<table class="filetable">
					<tr class="index">
						<td>첨부파일</td>
					</tr>
					<tr>
						<td id="file_td">
						<c:forEach items="${flist}" var="files" varStatus="n">
							<span id="file">
							<input readonly="readonly" type="hidden" name="files" value="${files.name}" >
     		 				<a href="./fdownload.do?name=${files.name}" >${files.ori_name} | ${files.f_size }byte</a>
     		 				<input id="delBtn" type='hidden' value='파일삭제' onclick='deleteFile(this)' ><br>
     		 				</span>
     		 			</c:forEach>
						</td>
					</tr>
				</table>
			</div>
			<div class="leftBox">
			<table id="docuHead">
				<tr>
					<th>문서번호</th>
					<td>${Ddto.seq}<input type="hidden" class="seq" name ="seq" value="${Ddto.seq}"><input type="hidden" name ="state" value="${Ddto.state}"></td>
				</tr>
				<tr>
					<th>기안자</th>
					<td>${Ddto.author}<input type="hidden" name ="author" value="${Ddto.author}"></td>
				</tr>
				<tr id="titleModi">
					<th>제목</th>
					<td>${Ddto.title}<input type="hidden" name ="title" value="${Ddto.title}"></td>
				</tr>
			</table>
			<hr>
			<h1>${typeDto.name}<input type="hidden" name="type_seq" value="${Ddto.type_seq}"></h1>
		<div id="contentModi" style="text-align: center; width: 100%;">
				<div class="apprSignTable" style="margin-bottom: 10px;">
					<table id="approLine" style="border: 3px solid black; width: 100%; height: 100px;">
						<c:if test="${apprList ne null}">
							<tr style="text-align: right; width: 200px; height: 30px;">
						<c:forEach  var="AdtoL" items="${apprList}" >
						<c:if test="${AdtoL.appr_kind ne '참조'}">
							<th style="border: 1px solid black; width: 25px; text-align: center;">${AdtoL.duty}</th>
						</c:if>
						</c:forEach>
							</tr>
							<tr>
						<c:forEach  var="AdtoL" items="${apprList}" >
						<c:if test="${AdtoL.appr_kind ne '참조'}">
								<c:choose>
										<c:when test="${AdtoL.appr_sign ne null}">
											<td style="border: 1px solid black; width:25px; "><img src="${AdtoL.appr_sign}"></td>
										</c:when>
										<c:when test="${AdtoL.appr_sign eq null}">
											<td  style="border: 1px solid black; width:25px; ">미결재</td>
										</c:when>
								</c:choose>
						</c:if>
						</c:forEach>
							</tr>
							</c:if>
					</table>
		</div>
			<div class="contentDiv">
				${Ddto.content}
			</div>
			</div>
		</div>
		<div class="commentTable" style="float: left; width: 70%;">
			<hr>
			<table id="commentTable" class="table" style="text-align: center;">
				<tr style="background: #E1E2E1;">
					<th colspan="4" style="border-top: none; text-align: center;">Comment</th>
				</tr>
				<tr style="text-align: center;">
						<th style="text-align: center;">번호</th>
						<th style="text-align: center;">작성자</th>
						<th style="text-align: center;">댓글</th>
						<th style="text-align: center;">작성일</th>
					</tr>
				<c:forEach var="Cdto" items="${comment}" varStatus="status">
					<tr>
						<td>${Cdto.comment_seq}</td>
						<td>${Cdto.name}</td>
						<td>${Cdto.content}</td>
						<td>${Cdto.regdate}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		</form>
		<div id="apprDoc" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">결재</h4>
					</div>
					<div class="modal-body">
						<form id="apprDocUp"></form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>