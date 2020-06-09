function previewAjax(seq){
	$.ajax({
		url: "./goDocTypePreview.do",
		type: "get",
		dataType: "json",
		data:{"type_seq":seq},
		success: function(msg){
			$(".preview").hide();
			var html = "";
			html += "<div>"+msg.content+"</div>";
			html += "<button onclick='goForm("+msg.type_seq+")'>선택</button>";
			html += "<button onclick='cancelSel()'>취소</button>";
			
			$(".preview").html(html);
			$(".preview").show();
		},
		error: function(){
			alert("error");
		}
	});
}

function goForm(seq){
	location.href="./goDocWriteForm.do?type_seq="+seq;
}

function cancelSel(){
	$(".preview").hide();
}