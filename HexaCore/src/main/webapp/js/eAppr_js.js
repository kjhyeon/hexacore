function saveDoc(){
location.href='./saveDoc.do?seq='+$(".seq").val()+'&title='+$(".title").val()+'&content='+$(".content").val()+'&author='+$(".author").val();
}

function apprDoc(val,val3){
	alert(val+"+"+val3);
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
	         html += "<div class='modal-footer'>";
	         html += "<input class='btn btn-success' type='button' value='승인' onclick='passwordChk()'>";
	         html += "<input class='btn btn-primary' type='button' value='반려' onclick='reject()'>";
	         html += "<button class='btn btn-default' data-dismiss='modal'>닫기</button>";
	         html += "</div>";
	         $("#apprDocUp").html(html);
	      },
	      error : function(){
	         alert("오류");
	      }
	   });
	}
	
function passwordChk() {
	var password = document.getElementById("password").value;
	 $.ajax({
	      url:"./checkPassword.do",
	      type:"post",
	      data:"password="+password,
	      success:function(msg){
	     	 if(msg=="true"){
	     	 confirm();
	     	 }else{
	     		$("#password").append("<p>비밀번호가 틀렸습니다.</p>");
	     	 }
	      },
	      error:function(){
	         swal("로그인","로그인에 문제가 있습니다.");
	      }
	   });
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

function modifyFormDoc(val,val2){
	$("#formDoc").attr("action",'./modifyFormDoc.do?seq='+val+'&id='+val2);
	$("#formDoc").attr("method","post");
	$("#formDoc").submit();
}

function deleteDocc(val){
	alert(val);
	$("#formDoc").attr("action",'./deleteDoc.do?seq='+val+'&id='+val2);
	$("#formDoc").attr("method","post");
	$("#formDoc").submit();
}

function upApprDoc(){
	alert("상신");
}
function update(){
	   var frm = document.getElementById("DocModify");
	   frm.action ="./modify.do";
	   var title=$("#title").val();
	   if(title=''){
	      swal("글수정 화면","");
	   }else{
	      frm.submit();
	   }
}

