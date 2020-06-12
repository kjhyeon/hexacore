<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 발신함</title>
<style type="text/css">
	thead {
		font-weight: bold;
		font-size: 13pt;
		text-align: center;
	}
	tbody > tr > td{
		text-align: center;
	}
	tbody > tr > th{
		margin-left: 50px;
	}
	#select{
	width: 100%;
	text-align: right;
	margin-bottom: 10px;
}

div.center{
	text-align: center;
}

ul.pagination{
	display: inline;
}

ul.pagination li a{
	color: black;
	float: left;
	padding: 10px 20;
	text-decoration: none;
	transition : background-color .3s;
	border: 1px solid #ddd;	
}

ul.pagination li a:active{
	
	background-color: #4a4a4a;
	color: skyblue;
	border: 1px solid pink;
	
}	
</style>
</head>
<script type="text/javascript" src="./js/message.js"></script>
<body>
<%@include file="../empInfoBar.jsp" %>
<div id="container" style="margin-left:25%">
	<div class="panel-group" id="accordion">
		<div style="font-size: 20pt; margin-left: 280px; padding: 20px;">메세지 발신함</div>
		<table class="table table-boardered">
			<thead>
				<tr>
					<td>수신자</td><td>제&nbsp;&nbsp;&nbsp;&nbsp; 목</td><td>보낸 날짜</td><td>상태</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="dto">
					<tr>
               			<td>${dto.receiver_id}</td>
						<th>
							<div class='panel-heading'>
								<a data-toggle='collapse' data-parent='#accordion'
									href='#collapse${dto.seq}'
									onclick='collapse(\"${dto.seq}\")'>${dto.title}</a>
							</div>
						</th>
						<td>${dto.regdate}</td>
						<td>
							<c:choose>
								<c:when test="${dto.state eq 0}">안읽음</c:when>
								<c:otherwise>읽음</c:otherwise>
							</c:choose>
						</td>
						<tr>
							<td colspan='5'>
								<div id='collapse${dto.seq}' class='panel-collapse collapse'>
									<div class='form-group'>
										<label>내&nbsp;&nbsp;&nbsp;&nbsp; 용</label>
										<textarea rows='5' class='form-control' readonly='readonly'>${dto.content}</textarea>
									</div>
									<c:if test="${not empty dto.file }">
										<div class="form-group">
											<label>첨부 파일 : </label>
											<a href="./download.do?name=${dto.file.name}" >${dto.file.ori_name}</a>
										</div>
									</c:if>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<input type="hidden" name="index" id="index" value="${row.index }">
		<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum }">
		<input type="hidden" name="listNum" id="listNum" value="${row.listNum }">
		
		<div class="center" style="text-align: center; margin-left: 300px; position: relative;">
			<ul class="pagination">
				<li><a href="./msgSendList.do?page=0">&laquo;</a></li>
				<li><a href="./msgSendList.do?page=${row.index-1}">&lt;</a></li>
				<c:forEach var="i" begin="${row.pageNum }" end="${row.count }" step="1">
					<li><a href="./msgSendList.do?page=${i-1}">${i }</a></li>
				</c:forEach>
				<li><a href="./msgSendList.do?page=${row.index+1}" >&gt;</a></li>
				<li><a href="./msgSendList.do?page=${row.lastPage-1}" >&raquo;</a></li>
			</ul>
		</div>
	</div>

</body>
</html>







