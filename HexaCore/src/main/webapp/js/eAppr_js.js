function saveDoc(){
location.href='./saveDoc.do?seq='+$(".seq").val()+'&title='+$(".title").val()+'&content='+$(".content").val()+'&author='+$(".author").val();

}

function apprDoc(val){
		ajaxApprDoc(val);
	   $("#apprDocUp").modal();
	}

	var ajaxApprDoc = function(val) {
	   $.ajax({
	      url : "./apprDoc.do",
	      type:"post",
	      dataType:"json",
	      data:{"seq":val},
	      success:function(msg){
	         var html="";
	         html += "<div class='form-group'>                   ";
	         html += "<input type='hidden' class='form-control' name='seq' value='"+msg.seq+"'> ";
	         html += "<input type='hidden' class='form-control' name='type_seq' value='"+msg.type_seq+"'> ";
	         html += "<label for='id'>아이디</label>                   ";
	         html += "<p class='form-control'><strong>"+msg.id+"</strong></p>                    ";
	         html += "</div>                                     ";
	         html += "<div class='form-group'>                   ";
	         html += "<label for='title'>제목</label>                     ";
	         html += "<input type='text' class='form-control' id='title' name='title' value='"+msg.title+"' required='required'> ";
	         html += "</div>                                     ";
	         html += "<div class='form-group'>                   ";
	         html += "<label for='content'>내용</label>                     ";
	         html += "<textarea class='form-control' id='content' name='content' rows='6'>"+msg.content+"</textarea>";
	         html += "</div>                                     ";
	         
	         html += "<div class='modal-footer'>";
	         html += "<input class='btn btn-success' type='button' value='수정완료' onclick='update()'>";
	         html += "<input class='btn btn-info' type='reset' value='초기화'>";
	         html += "<button class='btn btn-default' data-dismiss='modal'>닫기</button>";
	         html += "</div>";
	         $("#apprDocUp").html(html);
	      },
	      error : function(){
	         alert("잘못된 요청");
	      }
	   });
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
