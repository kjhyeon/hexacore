<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link href='./packages/core/main.css' rel='stylesheet' />
<link href='./packages/daygrid/main.css' rel='stylesheet' />
<link href='./packages/timegrid/main.css' rel='stylesheet' />
<link href='./packages/list/main.css' rel='stylesheet' />
<script src='./packages/core/main.js'></script>
<script src='./packages/interaction/main.js'></script>
<script src='./packages/daygrid/main.js'></script>
<script src='./packages/timegrid/main.js'></script>
<script src='./packages/list/main.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
</head>
<body>
	<div class="wrap">
		<%@include file="./header.jsp"%>
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
		<div class="container">
			<div class="content">
				<div class="container_div1" id="item">
					<div id="Doc_Title_Header">
						<h3>문서상신함</h3>
					</div>
					<div id="Doc_Title_Header">
						<a href="./goEapprMain.do" id="Title_a">
							more
						</a>
					</div>
					<hr>
					<table>
						<c:forEach items="${eDocList}" var="Doc">
							<tr id="Doc_List_Tr">
								<td>
									<a href="./docDetail.do?seq=${Doc.seq}">
									<c:choose>
										<c:when test="${fn:length(Doc.title) > 12 }">
											${fn:substring(Doc.title,0,12)}ㆍㆍㆍ
										</c:when>
										<c:otherwise>
											${Doc.title }
										</c:otherwise>
									</c:choose>
									</a>
								</td>
								<td>
								<c:choose>
									<c:when test="${Doc.state eq 0}">임시저장</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${Doc.state eq 1}">결재대기</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${Doc.state eq 2}">결재중</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${Doc.state eq 3}">결재완료</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${Doc.state eq 4}">반려</c:when>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="container_div2" id="item">
					<img alt="메인이미지" src="./image/MainPageImage.png">
				</div>
				
				<div class="container_div3" id="item">
					<div id="NoticeBoard_Title_Header">
						<h3 id="Title_h">공지사항</h3> 
					</div>
					<div id="NoticeBoard_Title_Header">
						<a href="./goBbs.do?category=1" id="Title_a">
							more
						</a>
					</div>
					<hr>
					<div>
						<table class="NoticeList_Title">
							<c:forEach items="${noticeList}" var="notice">
								<tr>
									<td id="NoticeList_List">
										<a href="./goBbs.do?seq=${notice.seq}&category=1">
											[공지]${notice.title}&nbsp;&nbsp;${fn:substring(notice.regdate,0,10)}
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				
				
				<div class="container_div4" id="item">
					<div id="FileBoard_Title_Header">
						<h3 id="Title_h">자료실</h3> 
					</div>
					<div id="FileBoard_Title_Header">
						<a href="./goBbs.do?category=2" id="Title_a">
							more
						</a>
					</div>
					<hr>
					<div>
						<table class="FileList_Title">
							<c:forEach items="${fileList}" var="fileBbs">
								<tr>
									<td id="NoticeList_List">
										<a href="./goBbs.do?seq=${fileBbs.seq}&category=2">
											[자료]${fileBbs.title}&nbsp;&nbsp;${fn:substring(fileBbs.regdate,0,10)}
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				
				
				<div class="container_div5" id="calendar">
					<button style="display: none" type="button" id="btnModal"
						class="btn btn-info btn-lg" data-toggle="modal"
						data-target="#myModal">Open Modal</button>
					<div id="addEventCal" class="modal fade" role="dialog">
						<div class="modal-dialog">	
							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h2 class="modal-title">간편공지</h2>
									<h4 class="modal-title">간단하게 공지를 등록해주세요</h4>
								</div>
								<div class="modal-body">
									<form id="addEventCals" action="./addEventCals.do"
										method="POST">
										<div class="form-group">
											<label for="title">TITLE:</label> <input type="text"
												name="title" class="form-control" id="txtTitle"> <label
												for="start">START_DATE:</label> <input type="text"
												name="start_date" class="form-control" id="start_date"
												readonly="readonly"> <label for="end">END_DATE:</label>
											<input type="text" name="end_date" class="form-control"
												id="end_date" readonly="readonly"> <input
												type="submit" class='btn btn-success' id='OKAY' value='일정등록'>
											<button class='btn btn-default' data-dismiss='modal'>닫기</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<form id="deleteCal">
				</form>
				<div class="container_div6" id="item">
					<img alt="코로나" src="./image/corona19.png">
				</div>

				<div class="container_div7" id="item">
					<table id="Emp_Table">
						<tr>
							<td id="profile" rowspan="3">
							<c:choose>
								<c:when test="${empty emp.profile_img|| emp.profile_img eq '' }">
									<img src="./image/default_profile.png" alt="사진">
								</c:when>
								<c:otherwise>
									<img src="/home/HexaCore/image/profile/${emp.profile_img }" alt="사진" style="width: 120px; height: 120px;">
								</c:otherwise>
							</c:choose>
							</td>
							<th>직급 :</th>
							<td>${emp.e_rank_name}</td>
						</tr>
						<tr>
							<th>부서 :</th>
							<td>${emp.department_name}</td>
						</tr>
						<tr>
							<th>이름 :</th>
							<td>${emp.name}</td>
						</tr>
					</table>
				</div>
				
			</div>
		</div>
	</div>
	<%@include file="./footer.jsp"%>
	
	<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction' ,'dayGrid' ],
      header: {
        left: 'today',
        center: 'title',
        right: 'prev,next '
      },
      defaultDate: new Date(),
      navLinks: false, // can click day/week names to navigate views
	  <c:choose>
      	<c:when test="${auth eq true}">
      	selectable: true,
      	</c:when>
      	<c:otherwise>
    	  selectable: false,
      	</c:otherwise>
      	</c:choose>
      selectMirror: true,
      select:  function(event) {
    		  $("#addEventCal").modal("show");
    		  $("#start_date").val(moment(event.start).format('YYYY-MM-DD'));
    		  $("#end_date").val(moment(event.end).format('YYYY-MM-DD'));
    	  },
      editable: true,
      disableDragging: true,
      eventLimit: true, // allow "more" link when too many events
      eventSources: [{
  		events: function(info, successCallback, failureCallback) {
  			$.ajax({
  				url: './getCals.do',
  				type: 'POST',
  				data: {
  					start : moment(info.startStr).format('YYYY-MM-DD'),
  					end : moment(info.endStr).format('YYYY-MM-DD'),
  				},
  				success: function(data) {
  					successCallback(data.lists);
  				},
  				error: function(data) {
					alert(실패);
				}
  			});
  		}
  	}]
	, eventClick:function(info, jsEvent, view) {
		  <c:choose>
	      	<c:when test="${auth eq true}">
				 var r = confirm("일정을 삭제하시겠습니까? "+info.event.title);
       			  if (r === true) {
        			deleteCal(info.event.title);
        			 }
	      	</c:when>
	      	<c:otherwise>
	      		return false;
	      	</c:otherwise>
	      	</c:choose>
       },
    });
    calendar.render();
  });

	function deleteCal(title) {
	$("#deleteCal").attr("action",'./deleteCal.do?title='+title);
	$("#deleteCal").attr("method","post");
	$("#deleteCal").submit();
}
</script>
</body>
</html>