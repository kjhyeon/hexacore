$(function(){
	// 사이드 바의 문서 작성 클릭할 때마다 문서 양식 출력/숨기기
	$(".menu01").click(function(){
		if($(".subside").text().length == 0){
			
		}
		if($(".subside").css("display") == "none"){
			
			$(".subside").show();
			$(".subside").animate({
				width: '10%'
			},200,function(){
				$(".subside>p").show();
			});
			$(".menu01").css("background-color","#414345");
		}else{
			$(".preview").hide();
			$(".subside>p").hide();
			$(".subside").animate({
				width: '0px'
			},200,function(){
				$(".subside").css("display", "none");
			});
			$(".menu01").css("background-color","#232526");
		}
	});
	
	// 문서함 클릭시 하위 문서함 출력/숨기기
	$(".ddmenu").click(function(){
		if($(this).parent().find(".submenu").css("display") == "none"){
			$(this).parent().parent().find(".submenu").hide();
			$(this).parent().find(".submenu").slideDown('fast').show();
		}else{
			$(this).parent().parent().find(".submenu").hide();
		}
	});
	
	// 메뉴 클릭시 iframe의 src속성 값을 변경
	$('.submenu').click(function(){
		$('#iframe').attr('src',$(this).attr('data-url'));
	})
	
$('#referMenu').click(function(){
		$('#iframe').attr('src',$(this).attr('data-url'));
	})
	
});

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
	$('#iframe').attr('src','./goDocWriteForm.do?type_seq='+seq);
	$(".preview").hide();
	$(".subside>p").hide();
	$(".subside").animate({
		width: '0px'
	},200,function(){
		$(".subside").css("display", "none");
	});
	$(".menu01").css("background-color","#232526");
}

function cancelSel(){
	$(".preview").hide();
}