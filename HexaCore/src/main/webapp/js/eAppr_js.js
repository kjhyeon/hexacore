function apprDoc(val1,val2,val3,number){
	ajaxapprDoc(val1,val2,val3,number);
	$("#apprDocUp").modal();
	}

	var ajaxapprDoc = function(val1,val2,val3,number) {
	   $.ajax({
	      url : "./apprDoc.do",
	      type:"post",
	      dataType:"json",
	      data:{"seq":val1,"appr_turn":val2,"a_turn":val3,"state":number},
	      success:function(msg){
	         var html="";
	         html += "<div class='form-group'>                   ";
	         html += "<input type='hidden' class='form-control' name='seq' value='"+msg.seq+"'> ";
	         html += "<input type='hidden' class='form-control' name='appr_turn' value='"+msg.appr_turn+"'> ";
	         html += "<input type='hidden' class='form-control' name='id' value='"+msg.id+"'> ";
	         html += "<input type='hidden' class='form-control' name='turn' value='"+msg.turn+"'> ";
	         html += "<input type='hidden' class='form-control' name='comment_seq' value='"+msg.appr_turn+"'> ";
	         html += "<input type='hidden' class='form-control' name='name' value='"+msg.name+"'> ";
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


//결재선 지정//////////////////////////////////////////////////////////////////


function apprSearch() {
	var treeWindow = window.open("./goApprTree.do", "결재루트 선택",
			"width=1000, height=750");
}
function setChildValue(nodes) {
	nodes = nodeSort(nodes);
	$(".apprtable > tbody").empty();
	var ap = "<tr class='index'>"
		   + "<td>부서직급</td><td>이름</td><td>종류</td><td>순서</td>"
		   + "</tr>"
	$(".apprtable > tbody").append(ap);
	for (var i = 0; i < nodes.length; i++) {
		var child = nodes[i].children;
		var app = "<tr>"
				+ "<td>"+child[0].innerHTML+" "+child[1].innerHTML+"<input type='hidden' name='lists["+i+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"
				+ "<td>"+child[2].innerHTML+"<input type='hidden' name='lists["+i+"].name' value='"+child[2].innerHTML+"'></td>"
				+ "<td>"+child[3].innerHTML+"<input type='hidden' name='lists["+i+"].appr_kind' value='"+child[3].innerHTML+"'></td>"
				+ "<td>"+(i+1)+"<input type='hidden' name='lists["+i+"].turn' value='"+(i+1)+"'>"
				+ "<input type='hidden' name='lists["+i+"].id' value='"+child[5].innerHTML+"'>"
				+ "<input type='hidden' name='lists["+(i)+"].seq' value='"+$(".seq").val()+"'>"
				+ "</td>"
				+ "</tr>";

		$(".apprtable > tbody").append(app);
	}
}
function setChildValue2(nodes1) {
	nodes = nodeSort(nodes1);
	$(".refertable > tbody").empty();
	var ap = "<tr class='index'>"
		   + "<td>부서직급</td><td>이름</td>"
		   + "</tr>";
	$(".refertable > tbody").append(ap);
	for (var i = 0; i < nodes.length; i++) {
		var child = nodes[i].children;
		var app = "<tr>"
				+ "<td>"+child[0].innerHTML+" "+child[1].innerHTML+"<input type='hidden' name='lists["+(i+3)+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"
				+ "<td>"+child[2].innerHTML+"<input type='hidden' name='lists["+(i+3)+"].name' value='"+child[2].innerHTML+"'>"
				+ "<input type='hidden' name='lists["+(i+3)+"].appr_kind' value='"+child[3].innerHTML+"'>"
				+ "<input type='hidden' name='lists["+(i+3)+"].turn' value='"+0+"'>"
				+ "<input type='hidden' name='lists["+(i+3)+"].id' value='"+child[5].innerHTML+"'>"
				+ "<input type='hidden' name='lists["+(i+3)+"].seq' value='"+$(".seq").val()+"'>"
				+ "</td>"
				+ "</tr>";

		$(".refertable > tbody").append(app);
	}
}

function nodeSort(nodes){
	var tempNode;
	for (var i = 0; i < nodes.length-1; i++) {
		var child1 = nodes[i].children;
		for (var j = i; j < nodes.length; j++) {
			var child2 = nodes[j].children;
			if((i!=j)&&(parseInt(child1[6].innerHTML) < parseInt(child2[6].innerHTML))){
				tempNode = nodes[j];
				nodes[j] = nodes[i];
				nodes[i] = tempNode;
			}
		}
	}
	
	return nodes;
}

//////////////////////////////////////////////////////////////////////////////////////

function report() {
	if($("#inputTitle").val()==""){
		alert("제목을 입력하세요.");
	}else if($(".apprtable > tbody >tr").length != 4){
		alert("결재자를 선택하세요.");
	}else{
		$(".leftBox").append("<input type='hidden' name='state' value='1'>");
		$("#formDoc").attr("action", "./saveUpDoc.do");
		$("#formDoc").attr("method", "post");
		$("#formDoc").submit();
	}
}


function saveUpdoc(){
	if(confirm("임시저장 하시겠습니까?\n(작성한 내용은 임시보관함에 저장됩니다.)") == true){
		if($("#inputTitle").val()==""){
			alert("제목을 입력하세요.");
		}else if($(".apprtable > tbody > tr").length != 4){
			alert("결재자를 선택하세요.");
		}else{
			$(".leftBox").append("<input type='hidden' name='state' value='0'>");
			$("#formDoc").attr("action", "./saveUpDoc.do");
			$("#formDoc").attr("method", "post");
			$("#formDoc").submit();
		}
	}else{
		return;
	}
}

function cancelmodify(){
	if(confirm("문서 수정을 취소하시겠습니까?\n(작성한 내용은 저장되지 않습니다.)") == true){
		location.reload(true);
	}else{
		return;
	}
}

function modifyFormDoc(val,flist){
	var len = document.getElementsByName("files").length;
	$.ajax({
		url:"./modifyFormDoc.do",
		type : "post",
		data : {"seq":val},
		dataType : "json",
		async : true,
		success : function(msg) {
			$(".btnBox").html("");
			$(".btnBox").append(btnInsert());
			$("#titleModi").html("");
			$("#titleModi").append(titleInsert(msg.title));
			$("#contentModi").html("");
			$("#contentModi").append(contentInsert(msg.content));
			$(".apprSignTable").html("");
			if(len<3){
				var button = "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
				$("#file_td").append(button);
			}
				$(delBtn).attr("type","button");
		},	error: function() {
			alert("실패");
		}
		
	});
}
function titleInsert(val) {
	var result="";
	result+="<th>제목</th>";
	result+="<td><input type='text' id='inputTitle' name='title' value='"+val+"'></td>";
	return result;
}

function contentInsert(val) {
	var result="";
	result+="<script type='text/javascript' src='./ckeditor/ckeditor.js'></script>";
	result+="<textarea id='p_content' name='content' rows='5' cols='50'>"+val+"</textarea>	";      
	result+="<script type='text/javascript'>CKEDITOR.replace('p_content', {height: 500});</script>	";   
	return result;
}

function btnInsert() {
	var result="";
	result+="<input type='button' onclick='apprSearch()' value='결재선 재선택'>                            		"     ;      
	result+="<input type='button' value='수정 취소' onclick='cancelmodify()'>                                  	"	;      
	result+="<input type='button' value='임시저장' onclick='saveUpdoc()'>                                    	"	;      
	result+="<input type='button' value='바로상신' onclick='report()'>                                    	"	;      
	return result;
}

function deleteFile(val) {
	$(val).parent().remove();
	var len = document.getElementsByName("files").length;
	if(len==2){
		$("#file_td").append("<input id='addbutton' type='button' value='추가' onclick='addinput()'>");
	}
}

function deleteDocc(seq,state){
	$("#formDoc").attr("action",'./deleteDoc.do?seq='+seq+"&state="+state);
	$("#formDoc").attr("method","GET");
	$("#formDoc").submit();
}

function upApprDocc(){
	$("#formDoc").attr("action",'./upApprDoc.do');
	$("#formDoc").attr("method","post");
	$("#formDoc").submit();
}


function addinput(){
	var len = document.getElementsByName("files").length;
	var input = "<span>";
	if(len<3){
		$("#addbutton").remove();
		input += "<input type='file' name='filename' onchange='fileChk(this)'><input type='button'  onclick='deleteFile(this)' value='파일삭제'>";
		input += "</span>";
		if(len<2){
			input += "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
		}
		$("#file_td").append(input);
	}
}

function fileChk(f){
	// 파일 유형 제한
	if ($(f).val != "") {
		var ext = $(f).val().split('.').pop().toLowerCase();
		if ($.inArray(ext, ['gif','jpg','jpeg','doc','html','zip','rar','7z',
			'alz','egg','001','alz','lzh','tgz','tar','tlz','tbz',
			'jar','war','apk','ppt','xlsx','txt','hwp','png','mp3',
			'mp4','avi','docx','gif','java'])== -1) {
			alert("등록할 수 없는 파일입니다.");
			$(f).val("");
			return;
		}
	}
	// 파일 용량 제한
	if (f.value != ""){
		var fileSize = f.files[0].size;
		var maxSize = 108*1024*1024 
		if(fileSize > maxSize){
			alert("첨부파일은 108MB 이내로 등록가능합니다.");
			$(f).val("");
			return
		}
	}
}


