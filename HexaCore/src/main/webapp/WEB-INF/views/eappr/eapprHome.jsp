<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
<meta charset="UTF-8">
<title>전자결재 홈</title>
<style type="text/css">
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
* {
	font-family: 'Jeju Gothic', sans-serif;
}
.fc-time{
   display : none;
}
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<link href='./packages/core/main.css' rel='stylesheet' />
<link href='./packages/daygrid/main.css' rel='stylesheet' />
<link href='./packages/timegrid/main.css' rel='stylesheet' />
<link href='./packages/list/main.css' rel='stylesheet' />
<link type="text/css" rel="stylesheet" href="./css/eapprhome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.js"></script>
<script src='./packages/core/main.js'></script>
<script src='./packages/interaction/main.js'></script>
<script src='./packages/daygrid/main.js'></script>
<script src='./packages/timegrid/main.js'></script>
<script src='./packages/list/main.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script type="text/javascript" src="./js/eapprhome.js"></script>
<link type="text/css" rel="stylesheet" href="./css/eapprhome.css">
</head>
<body>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth"></sec:authorize>
<button style="display: none" type="button" id="btnModal" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>
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
						<form id="addEventCals" action="./addEventCals.do" method="POST">
						<div class="form-group">
                            <label for="title">TITLE:</label>
                            <input type="text" name="title" class="form-control" id="txtTitle">
                            <label for="start">START_DATE:</label>
                            <input type="text" name="start_date" class="form-control" id="start_date" readonly="readonly">
                            <label for="end">END_DATE:</label>
                            <input type="text" name="end_date" class="form-control" id="end_date" readonly="readonly">
                            <input type="submit" class='btn btn-success' id='OKAY' value='일정등록'>
	         				<button class='btn btn-default' data-dismiss='modal'>닫기</button>
                        </div>
						</form>
					</div>
				</div>
			</div>
		</div>
<div id="calendar" style="width: 50%;">
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction' ,'dayGrid', 'timeGrid' ],
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      defaultDate: new Date(),
      navLinks: true, // can click day/week names to navigate views
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

</script>
<form id="deleteCal">
</form>
</div>
	문서요약보기${docCounts}<br>
	COUNT1: 참조문서함 개수 : ${docCounts.get("COUNT1")}<br>
	COUNT2: 결재문서함-결재중문서 개수 : ${docCounts.get("COUNT2")}<br>
	COUNT3: 결재문서함-결재필요문서개수 : ${docCounts.get("COUNT3")}<br>
	COUNT4: 상신문서함-임시저장문서 : ${docCounts.get("COUNT4")}<br>
	COUNT5:	상신문서함-결재대기문서 개수 : ${docCounts.get("COUNT5")}<br>
	COUNT6: 상신문서함-결재중문서 개수 : ${docCounts.get("COUNT6")}<br>
	COUNT7: 상신문서함-승인문서개수 : ${docCounts.get("COUNT7")}<br>
	COUNT8: 상신문서함-반려문서개수 : ${docCounts.get("COUNT8")}<br>
${lists}

</body>
</html>