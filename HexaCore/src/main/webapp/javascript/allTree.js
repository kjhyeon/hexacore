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
		"plugins" : [ "search", "state", "types",
			"wholerow", "json_data" ], //사용할 플러그인들 설정
			"themes" : {	// icon true로 지정한 아이콘 사용
				"theme" : "classic",
				"dots" : true,
				"icons" : true
			}
	});

	$("#deptTree").on("dblclick", ".jstree-anchor", function(e) {	//노드 클릭 시 콜백함수
		var node = $("#deptTree").jstree(true).get_node($(this));	//클릭한 노드 정보 갖고오기
		var type = node.type;	// 노드의 타입
		var flag = false;
		if(node.type == 'people'){
			for (var i = 0; i < $("tr").length; i++) {
				var chr = $("tr").eq(i).children();
				if(chr.eq(0).text()==node.li_attr['deptname']&&chr.eq(1).text()==node.li_attr['e_rank_name']&&chr.eq(2).text()==node.text){
					flag = true;
				}
			}
			if(!flag){
				var chkval = $(":input:radio[name=kind]:checked").val();
				if(chkval == '참조'){
					var tr1 = "<tr onclick='selectNode(this) id='appr''><td>"+node.li_attr['deptname']+"</td><td>"+node.li_attr['e_rank_name']+"</td><td>"+node.text+"</td><td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
					$(".refertable").append(tr1);
				}else{
					var tr2 = "<tr onclick='selectNode(this) id='appr''><td>"+node.li_attr['deptname']+"</td><td>"+node.li_attr['e_rank_name']+"</td><td>"+node.text+"</td><td>"+chkval+"</td><td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
					$(".apprtable").append(tr2);
				}
			}
		}
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
