<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
</head>
<script type="text/javascript">
	function moreInfo(category) {
		if(category == "freeBbs"){
			document.location.href= "./goBbs.do?keyword="+$("#keyword").val()+"&type="+$("#type").val()+"&category=0";
		}else if(category == "noticeBbs"){
			document.location.href= "./goBbs.do?keyword="+$("#keyword").val()+"&type="+$("#type").val()+"&category=1";
		}else if(category == "fileBbs"){
			document.location.href= "./goBbs.do?keyword="+$("#keyword").val()+"&type="+$("#type").val()+"&category=2";
		}else if(category == 'msg'){
			window.open("./msgReceiveList.do?keyword="+$("#keyword").val()+"&type="+$("#type").val(), "사원 정보", "width=1024,height=760")
		}
	}
	
	function detail(seq,category){
		if(category == "freeBbs"){
			document.location.href= "./goBbs.do?seq="+seq+"&category=0";
		}else if(category == "noticeBbs"){
			document.location.href= "./goBbs.do?seq="+seq+"&category=1";
		}else if(category == "fileBbs"){
			document.location.href= "./goBbs.do?seq="+seq+"&category=2";
		}
	}
</script>
<body>
	<%@include file="./../header.jsp" %>
	<div id="container" class="container">
		<div class="content">
		<input type="hidden" id="keyword" value="${keyword }">
		<input type="hidden" id="type" value="${type }">
		<h2>검색 결과</h2>
		<hr>
		<div id="eDocContainer">
			<h3>상신함</h3>
			<button class="btn" style="float: right;">더 보기</button>
			<table class="table table-bordered">
				<tr><th>번호</th><th>제목</th><th>글쓴이</th><th>작성일</th></tr>
				<c:forEach items="${eDocList }" var="eDoc">
					<tr>
						<td>${eDoc.seq }</td>
						<td><a href="./docDetail.do?seq=${eDoc.seq }">${eDoc.title }</a></td>
						<td>${eDoc.author }</td><td>${eDoc.regdate }</td>
					</tr>
					<tr style="background-color: #EEEEEE;"><td colspan="4">${eDoc.content }</td></tr>
				</c:forEach>
			</table>
		</div>
		<hr>
		<div id="noticeContainer">
			<table class="table table-bordered">
			</table>
		</div>
		<hr>
		<div id="freeContainer">
			<h3>자유게시판</h3>
			<button class="btn" style="float: right;" onclick="moreInfo('freeBbs')">더 보기</button>
			<table class="table table-bordered" id="freeBbs">
				<tr><th>번호</th><th>제목</th><th>글쓴이</th><th>작성일</th></tr>
				<c:forEach items="${freeBbsList }" var="free">
					<tr>
						<td>${free.seq }</td>
						<td><a href="#" onclick="detail(${free.seq},'freeBbs')">${free.title }</a></td>
						<td>${free.name }</td><td>${free.regdate }</td>
					</tr>
					<tr style="background-color: #EEEEEE;"><td colspan="4">${free.content }</td></tr>
				</c:forEach>
			</table>
		</div>
		<hr>
		<div id="fileContainer">
			<table class="table table-bordered">
			</table>
		</div>
		
		<div id="MsgContainer">
			<h3>메세지 수신함</h3>
			<button class="btn" style="float: right;" onclick="moreInfo('msg')">더 보기</button>
			<table class="table table-bordered" id="freeBbs">
				<tr><th>번호</th><th>제목</th><th>보낸사람</th><th>작성일</th></tr>
				<c:forEach items="${msgList }" var="msg">
					<tr>
						<td>${msg.seq }</td>
						<td>${msg.title }</a></td>
						<td>${msg.sender_id }</td><td>${msg.regdate }</td>
					</tr>
					<tr style="background-color: #EEEEEE;"><td colspan="4">${msg.content }</td></tr>
				</c:forEach>
			</table>
		</div>
		<hr>
		
	</div>
		</div>
	
	
	<%@include file="./../footer.jsp" %>
</body>
</html>