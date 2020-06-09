function checkImg(obj, ext){
	var check = false; 
	var extName = $(obj).val().substring($(obj).val().lastIndexOf(".")+1).toUpperCase(); 
	var str = ext.split(","); 
	for (var i=0;i<str.length;i++) { 
		if ( extName == $.trim(str[i]) ) { 
			check = true;
			break; 
			} 
		else 
			check = false; 
		} 
	if(!check){ 
		alert(ext+" 이미지 파일만 업로드 가능합니다."); 
		}
	return check; 
	}

function checkLength(obj){
	var check = false; 
	if(obj.files.length<=1){
		check = true;
	}
	if(!check){ 
		alert("한개의 파일만 업로드 가능합니다."); 
		}
	return check; 
	}
//첨부파일 용량 확인
function checkImgSize(obj, size) { 
	var check = false; 
	if(window.ActiveXObject) {
		var fso = new ActiveXObject("Scripting.FileSystemObject"); //
		var filepath = document.getElementById(obj).value; 
		var filepath = obj[0].value; 
		var thefile = fso.getFile(filepath); 
		sizeinbytes = thefile.size; 
	} else {//IE 외 //
		sizeinbytes = obj.files[0].size; 
	} 
	var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
	var i = 0;
	var checkSize = size;
	while(checkSize>900) {
		checkSize/=1024; i++; 
	} 
	checkSize = (Math.round(checkSize*100)/100)+' '+fSExt[i]; 
	var fSize = sizeinbytes;
	if(fSize > size) { 
		alert("첨부파일은 "+ checkSize + " 이하로 등록가능합니다."); 
		check = false; 
	} 
	else { 
		check = true; 
	} 
	return check;
} // 이미지 선택

function a(input,str) {
	if (input.files && input.files[0]) {
		if(this.value != "") { 
			var extPlan = "JPG, PNG"; 
			var checkSize = 1024*1024*10; // 5MB 
			if(!checkImg(input, extPlan) | !checkImgSize(input, checkSize) | !checkLength(input)) { 
//				input.select();
//				document.selection.clear();
				$(input).val('');
				return false;
			}
			
		}
		var reader = new FileReader(); // File API
		reader.onload = function(e) {
			var img = document.getElementById(str+"image");
			img.src = e.target.result;
			if(str=='sign-'){
				img.style.width = '60px';
				img.style.height = "60px";
			}else{
				img.style.height = "120px";
				img.style.width = '120px';
			}
		}
		}
		reader.readAsDataURL(input.files[0]);
		$(str+"#image").show();
	}
