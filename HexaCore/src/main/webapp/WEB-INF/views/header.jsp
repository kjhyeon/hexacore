<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
  <link rel="stylesheet" href="./css/common.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="./js/sweetalert.js"></script>
  <script type="text/javascript">
  window.setInterval('timeset()',100000);
  function timeset () {
      if (window.Notification) {
          Notification.requestPermission();
      }
  	aa();
  }
  window.onbeforeunload = function() {
  	clearInterval();
  };
  function aa(){
  	var beforeCnt = $("#cnt").val();
  	cntCheck(beforeCnt);
  }
  //아작스실행
  function cntCheck(val) {
  	$.ajax({
  		url:"./needCnt.do",
  		type : "post",
  		data : {"val":parseInt(val)},
  		async : true,
  		success : function(msg) {
  			if(msg!=val){
  				calculate();
  				$("#cntChkk").html("전자결재("+msg+")");
  				$("#cnt").val(msg);
  			}else{
  				$("#cntChkk").html("전자결재("+msg+")");
  				$("#cnt").val(msg);
  			}
  			if(msg=="0"){
  				$("#cntChkk").html("전자결재");
  			}
  		},error: function() {
  			alert("실패");
  		}
  });
  }	
  function calculate() {
     setTimeout(function () {
        notify();
        }, 3000);
  }
  function notify() {
       if (Notification.permission !== 'granted') {
            alert('notification is disabled');
       }
       else {
       var notification = new Notification('결재문서하실 문서가 있습니다.', {
                     icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
                     body: '결재하실 문서 확인 요청드립니다.',
                 });

                 notification.onclick = function () {
                    $("#appr").click();
                 };
             }
         }

  </script>
	<title>Home</title>
</head>
<script type="text/javascript">
function empPop() {
	var treeWindow = window.open("./empInfo.do", "사원 정보", "width=1024,height=760");
}

function totalSearch(){
	document.forms[0].submit();
}
</script>
<body>
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
	<header>
		<div class="topmenu" style="width:12%" onclick="location.href='./result.do'"><img alt="logo" src="./image/hexa64.png"></div>
		<div class="topmenu" style="width:200px" onclick="location.href='./goEapprMain.do'" id="cntChkk">전자결재</div>
		<div class="topmenu" style="width:200px" onclick="location.href='./goBbs.do'">게시판</div>
		<c:choose >
			<c:when test="${auth eq true }">
				<div class="topmenu" style="width:200px" onclick="location.href='./mngMain.do'">관리</div>
			</c:when>
			<c:otherwise>
				<div class="topmenu" style="width:200px"></div>
			</c:otherwise>
		</c:choose>
		<div class="searchbar">
			<form class="navbar-form" role="search" action="./totalSearch.do" method="get">
				<div class="input-group-btn">
					<select name="type" style="width:120px;  margin:1px" class="form-control">
						<option value="title/con">제목+내용</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="author">글쓴이</option>
					</select>
					<input type="text" class="form-control" style="width:300px;  margin:1px" name="keyword">
					<input id="cnt" type="hidden" value="0">
						<button class="form-control btn btn-default" type="button" style="border-radius: 5px; margin:1px;" onclick="totalSearch()">
							<span class="glyphicon glyphicon-search"></span>
						</button>
				</div>
			</form>
		</div>
		<div class="mypage" onclick="empPop()">My Page</div>
		<div class="logout" onclick="location.href='./logout'">Logout</div>
	</header>
</body>
</html>