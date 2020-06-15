document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'dayGrid', 'timeGrid' ],
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      defaultDate: new Date(),
      navLinks: true, // can click day/week names to navigate views
      selectable: true,
      selectMirror: true,
      select:  function(event) {
    	  alert(event.start+":"+event.end);
    	  $("#addEventCal").modal("show");
    	  $("#start_date").val(moment(event.start).format('YYYY-MM-DD'));
    	  $("#end_date").val(moment(event.end).format('YYYY-MM-DD'));
    	  },
//         var title = prompt('Event Title:');
//         if (title) {
//           calendar.addEvent({
//             title: title,
//             start: arg.start,
//             end: arg.end,
//             allDay: arg.allDay
//           })
//         }
//         calendar.unselect()
      editable: true,
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
		 var r = confirm("일정을 삭제하시겠습니까? "+info.event.title);
         if (r === true) {
        	deleteCal(info.event.title);
         }
       }, 
    });

    calendar.render();
  });

function deleteCal(title) {
	$("#deleteCal").attr("action",'./deleteCal.do?title='+title);
	$("#deleteCal").attr("method","post");
	$("#deleteCal").submit();
}