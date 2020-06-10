


function apprDoc(val1,val2,val3,number){
	ajaxapprDoc(val1,val2,val3,number);
	$("#apprDocUp").modal();
	}

	var ajaxapprDoc = function(val1,val2,val3,number) {
	   $.ajax({
	      url : "./apprDoc.do",
	      type:"post",
	      dataType:"json",
	      data:{"seq":val1,"appr_turn":val2,"a_turn":val3,"number":number},
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
	         html += "<input class='btn btn-success' id='confirmD' type='button' value='승인' onclick='passwordChk(\"confirm\","+msg.a_turn+","+msg.number+")'>";
	         html += "<input class='btn btn-primary' id='refjectD' type='button' value='반려' onclick='passwordChk(\"reject\","+msg.a_turn+","+msg.number+")'>";
	         html += "<button class='btn btn-default' data-dismiss='modal'>닫기</button>";
	         html += "</div>";
	         
	         $("#apprDocUp").html(html);
	      },
	      error : function(){
	         alert("오류");
	      }
	   });
	}
	
function passwordChk(conf, a_turn, number) {
	var password = document.getElementById("password").value;
	 $.ajax({
	      url:"./checkPassword.do",
	      type:"post",
	      data:{"password":password},
	      success:function(msg){
	     	 if(msg=="true"){
	     		 if(conf=='confirm'){
	     			 confirmDoc(a_turn, number);
	     		 }else{
	     			 rejectDoc(a_turn, number);
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
function saveUpdoc(){
	$("#docu").append("<tr><td><input type='hidden' name='state' value='0'></td></tr>");
	$("#modifyDoc").attr("action", "./saveDoc.do");
	document.modifyDoc.submit();
}

function confirmDoc(a_turn,number){
	
	$("#apprDocUp").attr("action",'./confirmDoc.do?chk=T&state='+number);
	$("#apprDocUp").attr("method","post");
	$("#apprDocUp").submit();
}

function rejectDoc(a_turn,number){
	$("#apprDocUp").attr("action",'./confirmDoc.do?chk=R&state='+number);
	$("#apprDocUp").attr("method","post");
	$("#apprDocUp").submit();
}


//
//////////////////////////////////


function apprSearch(){
	var treeWindow = window.open("./goApprTree.do", "결재루트 선택", "width=600, height=800");
}
function setChildValue(nodes){
	$("#approval").empty();
	for (var i = 0; i < nodes.length; i++) {
		var child = nodes[i].children;
// 		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3X 4아이디 5e_rank
// 			alert(child[j].innerHTML);
// 		}
		var app =
			"<tr>"+
			"<td>결재자"+i+" 아이디:<input name='lists["+i+"].id' value='"+child[4].innerHTML+"' readonly='readonly' ></td>"+
			"<td>결재자"+i+" 이름:<input name='lists["+i+"].name' value='"+child[2].innerHTML+"' readonly='readonly' ></td>"+
			"<td>결재자"+i+" 직위:<input name='lists["+i+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"' readonly='readonly' ></td>"+
			"<td>결재자"+i+" 결재순서:<input name='lists["+i+"].turn' value='"+(i+1)+"' readonly='readonly' ></td>"+
			"<td><select name='lists["+i+"].appr_kind'>"+
			"<option value='참조'>참조</option>"+
			"<option value='합의'>합의</option>"+
			"<option value='결재'>결재</option>"+
			"</select></td>"+
			"</tr>";
		
		$("#approval").append(app);
		
	}
	
}

function report(){
	$("#docu").append("<tr><td><input type='hidden' name='state' value='1'></td></tr>");
	$("#modifyDoc").attr("action", "./DocWrite.do");
	$("#modifyDoc").attr("method", "post");
	document.modifyDoc.submit();	
}

function saveUpdoc(){
	if(confirm("임시저장 하시겠습니까?\n(작성한 내용은 임시보관함에 저장됩니다.)") == true){
$("#docu").append("<tr><td><input type='hidden' name='state' value='0'></td></tr>");
$("#modifyDoc").attr("action", "./saveDoc.do");
$("#modifyDoc").attr("method", "post");
document.modifyDoc.submit();
	}else{
		return;
	}
}

function cancelmodify(){
	if(confirm("문서 수정을 취소하시겠습니까?\n(작성한 내용은 저장되지 않습니다.)") == true){
		$("#modifyDoc").attr("action", "./goEapprHome.do");
		$("#modifyDoc").attr("method", "get");
		document.modifyDoc.submit();
	}else{
		return;
	}
}

function reselecttype(){
	var isc = window.confirm("문서 양식을 재선택하시겠습니까?");
	if( isc == true){
		$("#modifyDoc").attr("action", "./goDocTypeList.do");
		$("#modifyDoc").attr("method", "get");
		modifyDoc.submit();
	}else{
		return;
	}
}

function resetDoc(){
	if(confirm("모든 내용을 초기화하시겠습니까?\n(지정한 결재루트와 문서 내용 모두가 삭제됩니다.)") == true){
		location.reload(true);	
	}else{
		return;
	}
}

function modifyFormDoc(val){
	$.ajax({
		url:"./modifyFormDoc.do",
		type : "post",
		data : {"seq":val},
		dataType : "json",
		
		success : function(msg) {
			var html="";
			html+= "<script type='text/javascript' src='./ckeditor/ckeditor.js'></script>";
			html+= "<form id='modifyDoc' name='modifyDoc'>                                    	        " ;      
			html+= "	<tr>                                                                                 	      	";      
			html+= "<input type='button' onclick='apprSearch()' value='결재선 재선택'>                            		"     ;      
			html+= "<input type='button' value='수정 취소' onclick='cancelmodify()'>                                  	"	;      
			html+= "<input type='button' value='초기화' onclick='resetDoc()'>                                        	"	;      
			html+= "<input type='button' value='임시저장' onclick='saveUpdoc()'>                                    	"	;      
			html+= "<input type='button' value='바로상신' onclick='report()'>                                    		 "   ;      
			html+= "	</tr>                                                                                    	  	";      
			html+= "<table id='docu' class='table'  style='text-align:center;'>                                                                            	    ";      
			html+= "	<tr>                                                                                       		";      
			html+= "		<td>No.</td>                                                                           		";      
			html+= "		<td>Author</td>                                                                        	 	";      
			html+= "		<td>Title</td>                                                                         		";      
			html+= "		<td>First Report Date</td>                                                              	";      
			html+= "	</tr>                                                                                   	   	";      
			html+= "	<tr>                                                                                    	   	";      
			html+= "		<td>"+msg.seq+"</td>                                                               	    	";      
			html+= "		<td>"+msg.author+"</td>                                                               	 	";      
			html+= "		<td><input type='text' name='title' value='"+msg.title+"'></td>                        	 	";      
			html+= "		<td>"+msg.regdate+"</td>                                                               	 	";      
			html+= "	</tr>                                                                                      		";      
			html+= "<div id='appr'>";
			html+= "</div>";
			html+= "</table>                                                                                         	";      
			html+= "<div class='apprSignTable'>";
			html+= "</div>";
			html+= "		<p>Content</p>                                                                       	  	";      
			html+= "<table'>                                                                                         	";      
			html+= "		<tr>                                                                                   	  	";      
			html+= "		<td>                                                                                   	  	";      
			html+= "			<textarea id='p_content' name='content' rows='5' cols='50'>"+msg.content+"</textarea>	";      
			html+= "			<script type='text/javascript'>CKEDITOR.replace('p_content', {height: 500});</script>	";      
			html+= "		</td>                                                                                    	";      
			html+= "		</tr>                                                                                   	  	";      
			html+= "		<td><input type='hidden' name='regdate' value='"+msg.regdate+" readonly = 'readonly''></td>                 	   	";      
			html+= "		<td><input type='hidden' name='author' value='"+msg.author+"' readonly = 'readonly'></td>                                                               	 	";      
			html+= "		<td><input type='hidden' name='seq' value='"+msg.seq+"' readonly = 'readonly'></td>                                                               	    	";      
			html+= "</form>                                                                                           "  ;
			$("#ajaxModify").html("");
			$("#ajaxModify").html(html);
			$(".apprSignTable").append(appLineInsert(msg.content2));
			
			
			
		},	error: function() {
			alert("실패");
		}
		
	});
}
function appLineInsert(val) {
	var result="";
	result+="<table id='approval' class='table'>";
	if(val !=null){
		result+="<tr>                  ";
		result+="	<td>이름</td>      ";
		result+="	<td>직위</td>      ";
		result+="	<td>결재 종류</td> ";
		result+="	<td>결재 상태</td> ";
		result+="</tr>                 ";
		for (var i = 0; i < val.length; i++) {
			result+="<tr>";
			result+="<td><input name='lists["+i+"].name' value='"+val[i].name+"' readonly='readonly'></td>                         ";
			result+="<td><input name='lists["+i+"].duty' value='"+val[i].duty+"' readonly='readonly'></td>                         ";
			result+="<td>"+val[i].appr_kind+"</td>                                                                                 ";
			if(val[i].chk != null){
				if(val[i].chk =='T'){
					result+="<td>승인</td>                                                                                              ";
				}
				if(val[i].chk =='R'){
					result+="<td>반려</td>                                                                                              ";
				}
				if(val[i].chk =='F'){
					result+="<td>미결재</td>                                                                                              ";
				}
			}
			result+="<td>                                                                                                       ";
			if(val[i].appr_sign!=null){
				result+="<img src='./image/도장1.png'	 style='width: 30px; height: 30px;'>             ";
			}
			result+="</td>                                                                                                      ";
			result+="</tr>                                                                                                      ";
			result+="<td><input type='hidden' name='lists["+i+"].id'	value='"+val[i].id+"' readonly='readonly'></td>            ";
			result+="<td><input type='hidden' name='lists["+i+"].turn' value='"+(i+1)+"' readonly='readonly'></td>              ";
			
		}
	}
result+="</table>";
	return result;
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

