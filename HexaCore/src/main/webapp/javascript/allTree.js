window.onload = function(){
	$('#deptTree').jstree({	// id가 tree인 영역에 트리 세팅
		'core' : {
			check_callback : true,
			'data' : {	// 초기 데이터 세팅 ajax로 갖고옴
				"url" : function(node) { //ajax 주소, 지연된 처리방식
					return "./allTree.do";
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
			"people" : { // 부서내 사원
				"icon" : "./image/people.png" //사원 아이콘 지정
			}
		},
		"plugins" : [ "contextmenu", "search", "state", "types","checkbox",
			"wholerow", "json_data" ], //사용할 플러그인들 설정
			"themes" : {	// icon true로 지정한 아이콘 사용
				"theme" : "classic",
				"dots" : true,
				"icons" : true
			}
	}).bind("move_node.jstree", function(e, data) {	// 드래그 앤 드롭이 끝났을때 콜백함수
		console.log("Drop node " + data.node.id + " to " + data.parent);
	});

	$("#deptTree").on("click", ".jstree-anchor", function(e) {	//노드 클릭 시 콜백함수
		var node = $("#deptTree").jstree(true).get_node($(this));	//클릭한 노드 정보 갖고오기
		var type = node.type;	// 노드의 타입
		var id = node.id; // 클릭한 노드의 id
		if(node.id !=0){ //루트는 선택 못함
			opener.document.getElementById("department_name").value = node.text;
			opener.document.getElementById("department_id").value = node.id;
			//id와 부서명만 갖고오고 종료
			window.close();
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
