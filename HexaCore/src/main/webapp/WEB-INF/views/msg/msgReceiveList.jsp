<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메세지 수신함</title>
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


	#Main_Search_Text{
		width: 450px;
		float: right;
	}


#type_Select{
		width: 120px;
		margin-right: 5px;
		border-radius: 4px;
	}
	
	#keyword_Search{
		width: 300px;
		position: relative; 
		float: left;
		border-radius: 4px;
		margin-right: 5px;
	}
	#type_Finder{
		border-radius: 4px;
	}

</style>
<script type="text/javascript">
	function gotoSend() {
		location.href = "./msgSendForm.do";
	}
	function goPage(page){
		var location = "./msgReceiveList.do?page="+page; 
		if($("#ori_keyword").val()!=null&&$("#ori_keyword").val().trim()!=''){
			location += "&keyword="+$("#ori_keyword").val()+"&type="+$("#ori_type").val();			
		}
		document.location.href=location;
	}
	
	function goSearch(){
		var location = "./msgReceiveList.do?"; 
		location += "keyword="+$("#keyword_Search").val()+"&type="+$("#type_Select").val();			
		document.location.href=location;
	}
</script>
</head>
<body>
	<%@include file="../empInfoBar.jsp"%>
	<div id="container" style="margin-left: 25%">
		<form action="./rMultiDel.do" method="post" id="frm" name="frm"
			onsubmit="return chkbox()">
			<div class="panel-group" id="accordion">
			<div style="font-size: 20pt; margin-left: 280px; padding: 20px;">메세지 수신함</div>
				<div class="form-group input-group" id="Main_Search_Text">
					<span class="input-group-btn"> <select name="type"
						class="form-control" id="type_Select">
							<option value="title/con">제목+내용</option>
							<option value="title">제목</option>
							<option value="content">내용</option>
							<option value="author">글쓴이</option>
					</select>
					</span> <span> <input type="text" class="form-control"
						placeholder="Search.." name="keyword" id="keyword_Search"
						value="${keyword }">
					</span> <span class="input-group-btn">
						<button class="btn btn-default" type="button" onclick="goSearch()"
							id="type_Finder">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
				<table class="table table-boardered">
					<thead>
						<tr>
							<td><input type="checkbox" onclick="checkAll(this.checked)"></td>
							<td>발신자</td><td>제&nbsp;&nbsp;&nbsp;&nbsp; 목</td><td>받은 날짜</td><td>상태</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lists}" var="dto">
							<tr>
								<td><input type="checkbox" name="chkVal" value="${dto.seq}"></td>
								<td>${dto.sender_id}</td>
								<th>
									<div class='panel-heading'>
										<a data-toggle='collapse' data-parent='#accordion' href='#collapse${dto.seq}'	
											onclick='javascript:collapse("${dto.seq}");'>${dto.title}</a>
									</div>
								</th>
								<td>${dto.regdate}</td>
								<td><c:choose>
										<c:when test="${dto.state eq 0}"><span id="msgState${dto.seq}">안읽음</span></c:when>
										<c:when test="${dto.state eq 1}"><span id="msgState${dto.seq}">읽음</span></c:when>
									</c:choose>
								</td>
							<tr>
								<td colspan='5'>
									<div id='collapse${dto.seq}'
										class='panel-collapse collapse'>
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
										<div class='form-group'>
											<input class='btn btn-primary btn-sm btn-center' type="button" value='메세지 삭제' onclick='del("${dto.seq}")'>
										</div>
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
					<li><a href="#" onclick="return goPage(${0});">&laquo;</a></li>
					<li><a href="#" onclick="return goPage(${row.index-1});">&lt;</a></li>
					<c:forEach var="i" begin="${row.pageNum }" end="${row.count }"
						step="1">
						<li><a href="#" onclick="goPage(${i-1})">${i }</a></li>
					</c:forEach>
					<li><a href="#" onclick="return goPage(${row.index+1});">&gt;</a></li>
					<li><a href="#" onclick="return goPage(${row.lastPage-1});">&raquo;</a></li>
				</ul>
			</div>
			<input class="btn btn-info" style="margin-left: 50px;" type="button" value="메세지 보내기" onclick="gotoSend()">
			<input class="btn btn-danger" style="margin-left: 20px;" type="submit" value="다중삭제">
		</form>
		<input type="hidden" id="ori_keyword" value="${keyword }">
		<input type="hidden" id="ori_type" value="${type }">
	</div>
<script type="text/javascript" src="./js/message.js"></script>
</body>
</html>