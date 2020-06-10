<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.apprLine {
	text-align: center;
	padding: 10px;
}

.table {
	width: 90%;
}

#approval>tr>td {
	width: 10px;
}
</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<meta charset="UTF-8">
<title>전자</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<body>
	<div id="ajaxModify">
	
			<form name="formDoc" id="formDoc">
					<input type="button" id="modifyDoc" value="수정"		onclick="modifyFormDoc('${Ddto.seq}')">
				<c:if
					test="${name eq Ddto.author && (Ddto.state eq '0' || Ddto.state eq '1') && (Ddto.appr_turn ne '2')}">
					<input type="button" id="deleteDoc" value="삭제"		onclick="deleteDocc(${Ddto.seq})">
						<c:if test="${Ddto.state eq '0'}">
						<input type="button" id="upApprDoc" value="상신"	onclick="upApprDoc()">
					</c:if>
				</c:if>
				<c:if
					test="${name ne Ddto.author && turn != '0' && turn eq Ddto.appr_turn}">
					<input type="button" value="결재" data-toggle="modal"		data-target="#apprDoc" data-backdrop='static' data-keyboard='false'	
																		onclick="apprDoc('${Ddto.seq}','${Ddto.appr_turn}','${Ddto.a_turn}','${number}')">
				</c:if>
			</form>
	
		<table id="table" class="table">
			<tr>
				<th>No.</th>
				<th>Author</th>
				<th>Title</th>
				<th>Report Date</th>
			</tr>
			<tr>
				<td>${Ddto.seq}</td>
				<td>${Ddto.author}</td>
				<td>${Ddto.title}</td>
				<td>${Ddto.regdate}</td>
			</tr>
		</table>
		<div id="appr">
		<table id="approval" class="table">
			<c:if test="${approvalLine ne null }">
				<tr>
					<td>이름</td>
					<td>직위</td>
					<td>결재 종류</td>
					<td>결재 상태</td>
					<td>서명</td>
				</tr>
				<c:forEach var="Adto" items="${approvalLine}" varStatus="aNum">
					<tr>
						<td><input name='lists["+i+"].name' value='${Adto.name}' readonly="readonly"></td>
						<td><input name='lists["+i+"].duty' value='${Adto.duty}' readonly="readonly"></td>
						<td>${Adto.appr_kind}</td>
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
							<td>
								<c:if test="${Adto.appr_sign ne null}">
									<img src="./image/도장1.png<%-- ${Adto.appr_sign} --%>"	style="width: 30px; height: 30px;">
								</c:if>
							</td>
					</tr>
					<td><input type="hidden" name='lists["+i+"].id'	value='${Adto.id}' readonly="readonly"></td>
					<td><input type="hidden" name='lists["+i+"].turn' value='"+(i+1)+"' readonly="readonly"></td>
				</c:forEach>
			</c:if>
		</table>
		</div>
		<div class="allcontent">
		<div class="apprSignTable" style="width: 30%; position: absolute; right:450px; margin-top: 100px;">
			<table id="approLine" style="border: 1px solid black; width: 100%">
			
				<c:if test="${approvalLine ne null}">
					<tr style="text-align: right; width: 200px; height: 30px;">
						<c:forEach  var="AdtoL" items="${approvalLine}" >
							<th style="border: 1px solid black; width: 25px;">${AdtoL.duty}</th>
						</c:forEach>
					</tr>
					<tr>
						<c:forEach  var="AdtoL" items="${approvalLine}" >
						<c:choose>
								<c:when test="${AdtoL.appr_sign ne null}">
									<td style="border: 1px solid black; width:25px; "><img src="./image/도장1.png" style="width: 30px; height: 30px;"></td>
								</c:when>
								<c:when test="${AdtoL.appr_sign eq null}">
									<td  style="border: 1px solid black; width:25px; ">""</td>
								</c:when>
						</c:choose>
						</c:forEach>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="contentTable">
		<p>Content</p>
			<table  style="text-align: center; width: 800px;">
				<tr>
					<td style="text-align: center; right: 450px; position: absolute; margin-top: 130px;">${Ddto.content}</td>
				</tr>
			</table>
		</div>
		</div>
		
		<div class="commentTable" style="position: relative;">
			<table id="commentTable" class="table">
				<tr>
					<th>Comment</th>
				</tr>
				<c:forEach var="Cdto" items="${comment}" varStatus="status">
					<tr>
						<th>No.</th>
						<th>Name</th>
						<th>Comment</th>
						<th>Confirm-Date</th>
					</tr>
					<tr>
						<td>${Cdto.comment_seq}</td>
						<td>${Cdto.name}</td>
						<td>${Cdto.content}</td>
						<td>${Cdto.regdate}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
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