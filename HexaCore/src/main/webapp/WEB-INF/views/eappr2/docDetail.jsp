<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<body>
	<%@include file="../header.jsp"%>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
	<div id="ajaxModify">
		<table id="approval">
			<c:if test="${approvalLine ne null }">
				<tr>
					<td>이름</td>
					<td>직위</td>
					<td>결재 종류</td>
					<td>결재 상태</td>
				</tr>
				<c:forEach var="Adto" items="${approvalLine}" varStatus="aNum">
					<tr>
						<td><input type="hidden" name='lists["+i+"].id'	value='${Adto.id}' readonly="readonly"></td>
						<td><input type="hidden" name='lists["+i+"].turn' value='"+(i+1)+"' readonly="readonly"></td>
						<td><input name='lists["+i+"].name' value='${Adto.name}' readonly="readonly"></td>
						<td><input name='lists["+i+"].duty' value='${Adto.duty}' readonly="readonly"></td>
						<td>${Adto.appr_kind}</td>
						<c:if test="${Adto.chk ne null }">
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
				</c:forEach>
			</c:if>
		</table>
		<table id="table" class="table">
			<tr>
				<th>No.</th>
				<td>${Ddto.seq}</td>
			</tr>
			<tr>
				<th>Author</th>
				<td>${Ddto.author}</td>
			</tr>
			<tr>
				<th>Title</th>
				<td>${Ddto.title}</td>
			</tr>
			<tr>
				<th>Report Date</th>
				<td>${Ddto.regdate}</td>
			</tr>
			<tr>
				<th>Content</th>
				<td>${Ddto.content}</td>
			</tr>
		</table>

		<c:if test="${empty comment}">
		</c:if>
		<table id="commentTable" class="commentTable">
			<tr>
				<th>Comment</th>
			</tr>
			<c:forEach var="Cdto" items="${comment}">
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
		<div>
			<form name="formDoc" id="formDoc">
				<c:if test="${name eq Ddto.author && (Ddto.state eq '0' || Ddto.state eq '1') && (Ddto.appr_turn ne '2')}">
					<input type="button" id="modifyDoc" value="수정"	onclick="modifyFormDoc('${Ddto.seq}')">
					<input type="button" id="deleteDoc" value="삭제"	onclick="deleteDocc(${Ddto.seq})">
					<c:if test="${Ddto.state eq '0'}">
						<input type="button" id="upApprDoc" value="상신"	onclick="upApprDoc()">
					</c:if>
				</c:if>
				<c:if test="${name ne Ddto.author && turn != '0' && turn eq Ddto.appr_turn}">
					<input type="button" value="결재" data-toggle="modal"		data-target="#apprDoc" data-backdrop='static'
						data-keyboard='false' onclick="apprDoc('${Ddto.seq}','${Ddto.appr_turn}','${Ddto.a_turn}','${number}')">
				</c:if>
			</form>
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
						<!-- ajax를 통해서 수정하고 넘길 데이터를 표출해줌 -->
						<form id="apprDocUp"></form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../footer.jsp"%>
</body>
</html>