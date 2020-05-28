<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./dist/themes/default/style.css">
<!-- <link rel="stylesheet" href="./css/themes/default-dark/style.min.css"> -->
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous"></script>
<script type="text/javascript" src="./dist/jstree.js"></script>

<script type="text/javascript">
window.onload = function(){
	$('#tree').jstree({	// id가 tree인 영역에 트리 세팅
	'core' : {
					check_callback : true,
					'data' : {	// 초기 데이터 세팅 ajax로 갖고옴
						"url" : function(node) { //ajax 주소, 지연된 처리방식
							return "./testTree.do";
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
						"icon" : "./img/icon_9.gif" //폴더 아이콘 지정
					},
					"people" : { // 부서내 사원
						"icon" : "./img/reply.png" //사원 아이콘 지정
					}
				},
				"plugins" : [ "contextmenu", "dnd", "search", "state", "types","checkbox",
					"wholerow", "json_data" ], //사용할 플러그인들 설정
				"themes" : {	// icon true로 지정한 아이콘 사용
					"theme" : "classic",
					"dots" : true,
					"icons" : true
				}
			}).bind("move_node.jstree", function(e, data) {	// 드래그 앤 드롭이 끝났을때 콜백함수
		console.log("Drop node " + data.node.id + " to " + data.parent);
	});

	$("#tree").on("click", ".jstree-anchor", function(e) {	//노드 클릭 시 콜백함수
		var node = $("#tree").jstree(true).get_node($(this));	//클릭한 노드 정보 갖고오기
		var type = node.type;	// 노드의 타입
		var id = node.id; // 클릭한 노드의 id
		if (type == 'folder') { // 선택한 노드가 폴더일 시 열려있으면 닫고 닫혀있으면 열음
			if (!node.state.opened) {
				$("#tree").jstree("open_node", id);
			} else {
				$("#tree").jstree("close_node", id);
			}
		} else { // 선택한 노드가 사람일시 정보를 가져옴
			alert(id);
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
			$("#tree").jstree(true).search($("#menu_name").val());
		} else {
			$("#tree").jstree("clear_search");
		}
	}

	function open_all() { // 트리 전체 열기
		$("#tree").jstree("open_all");
	}

	function close_all() { // 트리 전체 닫기
		$("#tree").jstree("close_all");

	}
</script>

</head>
<body>
<input type="text" id="menu_name" class="jstree-default jstree-search" name="menu_name" value="" onchange="search()" >
<input type="button" id="search_menu" name="search_menu" value="검색하기" onclick="search()">

<div id="tree" class="jstree-default"></div>

<input type="button" id="open_all" name="open_all" value="전체열기" onclick="open_all()">
<input type="button" id="close_all" name="close_all" value="전체닫기" onclick="close_all()">

</body>
</html>