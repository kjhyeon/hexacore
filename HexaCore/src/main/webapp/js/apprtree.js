function addEmp() {
	var id = $("#deptTree").jstree("get_selected");
	var node = ($("#deptTree").jstree("get_node",id));
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
				var tr1 = "<tr class='refernode' onclick='selectNode(this)' id='referss'><td>"+node.li_attr['deptname']+"</td><td>"+node.li_attr['e_rank_name']+"</td><td>"+node.text+"</td><td>"+chkval+"</td><td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
				$(".refertable").append(tr1);
			}else{
				var tr2 = "<tr class='apprnode' onclick='selectNode(this)' id='apprss'><td>"+node.li_attr['deptname']+"</td><td>"+node.li_attr['e_rank_name']+"</td><td>"+node.text+"</td><td>"+chkval+"</td><td><input type='button' value='삭제' onclick='delEmp(this)'></td><td hidden='false'>"+node.id+"</td><td hidden='false'>"+node.li_attr['e_rank']+"</td></tr>";
				$(".apprtable").append(tr2);
			}
		}
	}
}

function delEmp(node) {
	var tr = node.parentElement.parentElement;
	tr.parentElement.removeChild(tr);
}

function delEmps() {
	$(".selected").remove();
}

function selectNode(node) {
	if(node.hasAttribute("class")){
		node.removeAttribute("class");
	}else{
		node.setAttribute("class","selected");
	}
}

function closeEmp(){
	if($(".apprtable > tbody > tr").length != 4){
		alert("결재자는 3명을 선택해야합니다.");
	} else{
		var apprnodes = $(".apprnode");
		var refernodes = $(".refernode");
		opener.setChildValue(apprnodes);
		opener.setChildValue2(refernodes);
		window.close();
	}
}

function cancelEmp(){
	window.close();
}