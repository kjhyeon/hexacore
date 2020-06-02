function saveDoc(){
	var seq = document.getElementById("seq").value;
	var title = document.getElementById("title").value;
	var content = document.getElementById("content").value;
	var author = document.getElementById("author").value;
	alert(seq+":"+title+":"+content);
	location.href='./saveDoc.do?seq='+seq+'&title='+title+'&content='+content+'&author='+author;
}

function modifyForm(seq) {
	location.href="./modifyForm.do?seq="+seq;
}