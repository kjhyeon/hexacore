window.onload = function(){
	$('#deptTree').jstree({	// id가 tree인 영역에 트리 세팅
		'core' : {
			check_callback : true,
			'data' : {	// 초기 데이터 세팅 ajax로 갖고옴
				"url" : function(node) { //ajax 주소, 지연된 처리방식
					return "./deptTree.do";
				},
				"data" : function(node) {//ajax로 보낼 데이터, 루트 id인 #이 날아감
					//         	  alert(node.id);
					return {
						"id" : node.id
					}
				},
				"method" : "post",	//ajax 방식
				"dataType" : "json" //json형식으로 데이터를 받아옴
			}
		},
		"types" : {	// 노드들의 type 설정
			"folder" : {	// 부서는 폴더형식
				"icon" : "./image/department2.png" //폴더 아이콘 지정
			},
		},
		"plugins" : [ "search", "state", "types","dnd",
			"wholerow", "json_data" ], //사용할 플러그인들 설정
		"themes" : {	// icon true로 지정한 아이콘 사용
				"theme" : "classic",
				"dots" : true,
				"icons" : true
			}
	}).bind("move_node.jstree", function(e, data) {	// 드래그 앤 드롭이 끝났을때 콜백함수
		$.ajax({
			url : "./moveDept.do",
			method : "post",
			datatype : "json",
			data : {"department_id":data.node.id,"upper_id":data.parent},
			success : function(msg){
				console.log(msg+" : Drop node " + data.node.id + " to " + data.parent);
			},
			error : function(){
				alert("부서이동에러");
			}
		});
	}).bind("create_node.jstree", function(e, data){// 노드를 생성했을 때 콜백함수
//		$("#deptTree").jstree(true).get_node(data.parent).state.opened=true;
		$.ajax({
			url : "./createDept.do",
			method : "post",
			datatype : "json",
			data : {"upper_id":data.node.parent},
			success : function(msg){
				$("#deptTree").jstree("set_id",data.node, msg);
				$("#deptTree").jstree("set_icon",data.node, "./image/department2.png");
			},
			error : function(){
				alert("부서생성에러");
			}
		});
	}).bind("delete_node.jstree", function(e, data){// 노드를 삭제했을 때 콜백함수
		$.ajax({
			url : "./deleteDept.do",
			method : "post",
			datatype : "json",
			data : {"department_id":data.node.id},
			success : function(msg){
			},
			error : function(){
				alert("부서삭제에러");
			}
		});
	});

	$("#deptTree").on("click", ".jstree-anchor", function(e) {	//노드 클릭 시 콜백함수
		var node = $("#deptTree").jstree(true).get_node($(this));	//클릭한 노드 정보 갖고오기
		var type = node.type;	// 노드의 타입
		var id = node.id; // 클릭한 노드의 id
		if(node.id !=0){ //루트는 선택 못함
			parent.document.getElementById("department_name").value = node.text;
			
			if($("#"+node.id).attr("faxnum")!=undefined&&$("#"+node.id).attr("faxnum")!='null' ){
				parent.document.getElementById("department_fax").value = $("#"+node.id).attr("faxnum");
			}else{
				parent.document.getElementById("department_fax").value = '';
			}
			if($("#"+node.id).attr("d_phone")!=undefined&&$("#"+node.id).attr("d_phone")!='null' ){
				parent.document.getElementById("department_phone").value = $("#"+node.id).attr("d_phone");
			}else{
				parent.document.getElementById("department_phone").value = '';
			}
		}
	});

	$(document).bind("dnd_start.vakata", function(e, data) {
		console.log("Start dnd");
	}).bind("dnd_move.vakata", function(e, data) {
		console.log("Move dnd");
	}).bind("dnd_stop.vakata", function(e, data) {
		console.log("stop dnd");
	});
	
}
function search() { //트리 내 name을 통한 노드 검색
	if ($("#menu_name").val()) {
		$("#deptTree").jstree(true).search($("#menu_name").val());
	} else {
		$("#deptTree").jstree("clear_search");
	}
}

function open_all() { // 트리 전체 열기
	$("#deptTree").jstree("open_all");
}

function close_all() { // 트리 전체 닫기
	$("#deptTree").jstree("close_all");

}

function createDept() { // 트리 전체 열기
	$("#deptTree").jstree("create_node", $("#deptTree").jstree("get_selected"), '새 부서', 50, false, false);
}

function updateDept() {
	var id = $("#deptTree").jstree("get_selected");
	var node = ($("#deptTree").jstree("get_node",id));
	if(id!=0){
		$.ajax({
			url : "./updateDept.do",
			method : "post",
			data : {'department_id':id[0],'upper_id':node.parent,'name':parent.document.all['department_name'].value,'d_phone':parent.document.all['d_phone'].value,'faxnum':parent.document.all['faxnum'].value
			},
			success : function() {
				$("#deptTree").jstree("get_node",id).li_attr['d_phone'] = parent.document.all['d_phone'].value;
				$("#deptTree").jstree("get_node",id).li_attr['faxnum'] = parent.document.all['faxnum'].value;
				$("#deptTree").jstree("get_node",id).text = parent.document.all['department_name'].value;
				$('#deptTree').jstree(true).refresh();
			},
			error : function(){
				alert("부서수정에러");
			}
		});
	}
}


//var reg_uid = /^.*(?=^.{8,50}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;       //6~16자의 영문 대/소문자, 숫자, 특수문자 조합  // 정규표현식
                 //비밀번호는 최소 길이 8자리 이상 : 영대문자(A~Z, 26개), 영소문자(a~z, 26개), 숫자(0~9, 10개) 및 특수문자(32개) 중 3종류 이상으로 구성
//var reg_uid = /^.*(?=^.{8,50}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()=-+]).*$/;
//var reg_uid = /^.*(?=^.{6,12}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[~,!,@,#,$,*,(,),=,+,_,.,|,[,],{,}]).*$/;

function updateChk(){
	var phoneChk =  /^\d{2,3}-\d{3,4}-\d{4}$/;
	phone=parent.document.all['d_phone'].value;
	fax=parent.document.all['faxnum'].value;
	name = parent.document.all['department_name'].value;
	if(name==''||name==undefined||name==null){
		alert("부서 이름을 입력해주세요");
		window.parent.document.getElementById("department_name").focus();
	}else if(phone!=''&&!phoneChk.test(phone)){
		alert("올바른 번호가 아닙니다");
		window.parent.document.getElementById("department_phone").focus();
	}else if(fax!=''&&!phoneChk.test(fax)){
		alert("올바른 번호가 아닙니다");
		window.parent.document.getElementById("department_fax").focus();
	}else{
		updateDept();
	}
}

function deleteDept() {
	var id = $("#deptTree").jstree("get_selected");
	var node = ($("#deptTree").jstree("get_node",id));
	if(id!=0){
		var children = $("#deptTree").jstree("get_children_dom",node);
		var confirm1 = confirm("선택한 부서를 삭제하시겠습니까?");
		if(confirm1){
			if(children.length>0){
				var confirm2 = confirm("하위 부서까지 삭제됩니다 괜찮으시겠습니까?");
				if(!confirm2){
					return null;
				}
			}
			$("#deptTree").jstree("delete_node", $("#deptTree").jstree("get_selected"));
		}
	}
}

