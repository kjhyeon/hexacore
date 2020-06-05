function apprDoc(val,val3){
	ajaxapprDoc(val,val3);
	$("#apprDocUp").modal();
	}

	var ajaxapprDoc = function(val,val3) {
	   $.ajax({
	      url : "./apprDoc.do",
	      type:"post",
	      dataType:"json",
	      data:{"seq":val,"appr_turn":val3},
	      success:function(msg){
	         var html="";
	         html += "<div class='form-group'>                   ";
	         html += "<input type='hidden' class='form-control' name='seq' value='"+msg.seq+"'> ";
	         html += "<input type='hidden' class='form-control' name='appr_turn' value='"+msg.appr_turn+"'> ";
	         html += "<input type='hidden' class='form-control' name='id' value='"+msg.id+"'> ";
	         html += "<input type='hidden' class='form-control' name='turn' value='"+msg.turn+"'> ";
	         html += "<label for='name'>성함</label>                   ";
	         html += "<p class='form-control'><strong>"+msg.name+"</strong></p>                    ";
	         html += "</div> ";
	         html += "<div class='form-group'>";
	         html += "<label for='content'>코멘트</label>";
	         html += "<input type='text' class='form-control' id='content' name='content' required='required'> ";
	         html += "</div>   ";
	         html += "<div class='form-group'>";
	         html += "<label for='pw'>비밀번호 입력</label>";
	         html += "<input type='password' class='form-control' id='password' name='password' required='required'> ";
	         html += "</div>   ";
	         html += "<div id='result'>   ";
	         html += "</div>   ";
	         html += "<div class='modal-footer'>";
	         html += "<input class='btn btn-success' id='confirmD' type='button' value='승인' onclick='passwordChk(\"confirm\")'>";
	         html += "<input class='btn btn-primary' id='refjectD' type='button' value='반려' onclick='passwordChk(\"reject\")'>";
	         html += "<button class='btn btn-default' data-dismiss='modal'>닫기</button>";
	         html += "</div>";
	         
	         $("#apprDocUp").html(html);
	      },
	      error : function(){
	         alert("오류");
	      }
	   });
	}
	
function passwordChk(asdf) {
	var password = document.getElementById("password").value;
	 $.ajax({
	      url:"./checkPassword.do",
	      type:"post",
	      data:"password="+password,
	      success:function(msg){
	     	 if(msg=="true"){
	     		 if(asdf=='confirm'){
	     			 confirm();
	     		 }else{
	     			 reject();
	     		 }
	     	 }else{
	     		$("#result").css("color","red");
	     		$("#result").html("<p>비밀번호를 확인해주세요.</p>");
	     	 }
	      },
	      error:function(){
	         swal("비밀번호 체크 오류","비밀번호체크에 문제가 있습니다.");
	      }
	   });
}
function saveDoc(){
	location.href='./saveDoc.do?seq='+$(".seq").val()+'&title='+$(".title").val()+'&content='+$(".content").val()+'&author='+$(".author").val();
	}

function confirm(){
	
	$("#apprDocUp").attr("action",'./confirmDoc.do?chk=T');
	$("#apprDocUp").attr("method","post");
	$("#apprDocUp").submit();
}

function reject(){
	$("#apprDocUp").attr("action",'./confirmDoc.do?chk=R');
	$("#apprDocUp").attr("method","post");
	$("#apprDocUp").submit();
}

function modifyFormDoc(val){
	$("#formDoc").attr("action",'./modifyFormDoc.do?seq='+val);
	$("#formDoc").attr("method","post");
	$("#formDoc").submit();
}

function deleteDocc(val){
	$("#formDoc").attr("action",'./deleteDoc.do?seq='+val);
	$("#formDoc").attr("method","post");
	$("#formDoc").submit();
}

function upApprDoc(){
	$("#formDoc").attr("action",'./upApprDoc.do?state=1');
	$("#formDoc").attr("method","post");
	$("#formDoc").submit();
}

