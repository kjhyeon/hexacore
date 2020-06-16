
function deleteCal(title) {
	$("#deleteCal").attr("action",'./deleteCal.do?title='+title);
	$("#deleteCal").attr("method","post");
	$("#deleteCal").submit();
}