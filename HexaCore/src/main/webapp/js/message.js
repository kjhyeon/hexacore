function del(val){
	location.href="./rDel.do?seq="+val;
}

function checkAll(bool){
   var chkVals = document.getElementsByName("chkVal");
   for (var i = 0; i < chkVals.length; i++) {
      chkVals[i].checked = bool;
   }
}

function chkbox(){
   var n = 0;
   var chkVals = document.getElementsByName("chkVal");
   for (var i = 0; i < chkVals.length; i++) {
      if(chkVals[i].checked){
         n++;
      }
   }
   
   if(n>0){
      document.getElementById("frm").action="./rMultiDel.do";
   }else{
      alert("선택된 메세지가 없습니다.");
      return false;
   }
}

var collapse = function(seq){
	if($("#msgState"+seq).text()=='안읽음'){
		$.ajax({
			url : "./updateState.do",
			method : "POST",
			data : {"seq":seq},
			success : function(){
//				alert($("#msgState"+seq).text());
				$("#msgState"+seq).text("읽음");
			},
			error : function(){
				alert("메세지 업데이트 실패");
				return false;
			}
		});
	}
	
//	$(".panel-collapse").not("#collapse"+seq).each(function(){
//		$(this).attr('class','panel-collapse collapse');
//	});
}