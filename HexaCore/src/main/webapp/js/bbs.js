// 공통된 파일 JavaScript 
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

function writeComplete(){
	var temp = $("#title").val();
	if(temp.length <1){
  	  alert("제목을 입력해주세요");
  	  $("#title").focus();
  	  return false;
	}
	if(temp.indexOf("<")>=0){
		temp = temp.replace(/</g ,"&lt;");
	}
	if(temp.indexOf(">")>=0){
		temp = temp.replace(/>/g ,"&gt;");
	}
	$("#title").val(temp);
	document.forms[0].submit();
}
