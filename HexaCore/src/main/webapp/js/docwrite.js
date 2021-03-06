function apprSearch() {	
	var treeWindow = window.open("./goApprTree.do", "결재루트 선택",	
	"width=1000, height=750");	
}	
function setChildValue(nodes2) {	
	nodes = nodeSort(nodes2);	
	$(".apprtable > tbody").empty();	
	var ap = "<tr class='index'>"	
		+ "<td>부서직급</td><td>이름</td><td>종류</td><td>순서</td>"	
		+ "</tr>";	
	$(".apprtable > tbody").append(ap);	
	for (var i = 0; i < nodes.length; i++) {	
		var child = nodes[i].children;	
//		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3종류 4X 5아이디 6e_rank	
//		alert(child[j].innerHTML);	
//		}	
		var app = "<tr>"	
			+ "<td>"+child[0].innerHTML+" "+child[1].innerHTML+"<input type='hidden' name='lists["+i+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"	
			+ "<td>"+child[2].innerHTML+"<input type='hidden' name='lists["+i+"].name' value='"+child[2].innerHTML+"'></td>"	
			+ "<td>"+child[3].innerHTML+"<input type='hidden' name='lists["+i+"].appr_kind' value='"+child[3].innerHTML+"'></td>"	
			+ "<td>"+(i+1)+"<input type='hidden' name='lists["+i+"].turn' value='"+(i+1)+"'>"	
			+ "<input type='hidden' name='lists["+i+"].id' value='"+child[5].innerHTML+"'>"	
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
//		for (var j = 0; j < child.length; j++) { //0부서 1직위 2이름 3종류 4X 5아이디 6e_rank	
//		alert(child[j].innerHTML);	
//		}	
		var app = "<tr>"	
			+ "<td>"+child[0].innerHTML+" "+child[1].innerHTML+"<input type='hidden' name='lists["+(i+3)+"].duty' value='"+child[0].innerHTML+" "+child[1].innerHTML+"'></td>"	
			+ "<td>"+child[2].innerHTML+"<input type='hidden' name='lists["+(i+3)+"].name' value='"+child[2].innerHTML+"'>"	
			+ "<input type='hidden' name='lists["+(i+3)+"].appr_kind' value='"+child[3].innerHTML+"'>"	
			+ "<input type='hidden' name='lists["+(i+3)+"].turn' value='"+0+"'>"	
			+ "<input type='hidden' name='lists["+(i+3)+"].id' value='"+child[5].innerHTML+"'>"	
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

function report() {	
	if($("#inputTitle").val()==""){	
		alert("제목을 입력하세요.");	
	}else if($(".apprtable > tbody > tr").length != 4){	
		alert("결재자를 선택하세요.");	
	}else{	
		$(".leftBox").append("<input type='hidden' name='state' value='1'>");	
		$("#insertdoc").attr("action", "./DocWrite.do");	
		document.insertdoc.submit();		
	}	
}	
function savedoc() {	
	if($("#inputTitle").val()==""){	
		alert("제목을 입력하세요.");	
	}else if($(".apprtable > tbody > tr").length != 4){	
		alert("결재자를 선택하세요.");	
	}else{	
		$(".leftBox").append("<input type='hidden' name='state' value='0'>");	
		$("#insertdoc").attr("action", "./DocWrite.do");	
		document.insertdoc.submit();	
	}	

}	
function cancelwrite() {	
	if (confirm("문서 작성을 취소하시겠습니까?\n(작성한 내용은 저장되지 않습니다.)") == true) {	
		$("#insertdoc").attr("action", "./goEapprHome.do");	
		$("#insertdoc").attr("method", "get");	
		document.insertdoc.submit();	
	} else {	
		return;	
	}	
}	
function resetDoc() {	
	if (confirm("모든 내용을 초기화하시겠습니까?\n(지정한 결재루트와 문서 내용 모두가 삭제됩니다.)") == true) {	
		location.reload(true);	
	} else {	
		return;	
	}	
}	

$(document).ready(function() {
	var button = "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
	$("#file_td").append(button);
});
function addinput(){
	var len = document.getElementsByName("filename").length;
	var input = "<span>"
	if(len<3){
		$("#addbutton").remove();
			input += "<input type='file' name='filename' onchange='fileChk(this)'><input type='button' class='btn btn-default' name='deleteFile' onclick='delFile(this)' value='삭제'><br>";
			input += "</span>"
		if(len<2){
			input += "<input id='addbutton' type='button' value='추가' onclick='addinput()'>";
		}
		
		$("#file_td").append(input);
	}
	
}

function fileChk(f){
	// 파일 유형 제한
	if ($(f).val != "") {  // input에 name이 filename 불러오기
		var ext = $(f).val().split('.').pop().toLowerCase();
		if ($.inArray(ext, ['gif','jpg','jpeg','doc','html','zip','rar','7z',
			'alz','egg','001','alz','lzh','tgz','tar','tlz','tbz',
			'jar','war','apk','ppt','xlsx','txt','hwp','png','mp3',
			'mp4','avi','docx','gif','java'])== -1) {
			alert("등록할 수 없는 파일입니다.");
			$(f).val(""); // input 파일명을 다시 지워주는 코드
			return;
		}
	}
	// 파일 용량 제한
	if (f.value != ""){
		var fileSize = f.files[0].size;
		var maxSize = 108*1024*1024 // 108MB 제한
		if(fileSize > maxSize){
			alert("첨부파일 사이즈는 108MB 이내로 등록가능합니다.");
			$(f).val("");
			return
		}
	}
}

function delFile(val){
	$(val).parent().remove();
	var len = document.getElementsByName("filename").length;
	if(len==2){
		$("#file_td").append("<input id='addbutton' type='button' value='추가' onclick='addinput()'>");
	}
}