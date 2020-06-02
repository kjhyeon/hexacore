<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="./js/eAppr_js.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<body>
	${Ddto}
	<div id="container">
		<form action="./saveDoc.do" method="post">
			<button>결재선 선택</button>
			<table><!-- 결재선 테이블 -->
			<tr>
				<td>결재자1</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[0].id" value="appr1"></td>
				<td>name:<input name="lists[0].name" value="결재자1"></td>
				<td>duty:<input name="lists[0].duty" value="결재테스트부서 대리"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:<input name="lists[0].turn" value="1"></td>
				<td>appr_kind:<input name="lists[0].appr_kind" value="합의"></td>
			</tr>
			<tr>
				<td>결재자2</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[1].id" value="appr2"></td>
				<td>name:<input name="lists[1].name" value="결재자2"></td>
				<td>duty:<input name="lists[1].duty" value="결재테스트부서 부장"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:<input name="lists[1].turn" value="2"></td>
				<td>appr_kind:<input name="lists[1].appr_kind" value="합의"></td>
			</tr>
			<tr>
				<td>결재자3</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[2].id" value="appr3"></td>
				<td>name:<input name="lists[2].name" value="결재자3"></td>
				<td>duty:<input name="lists[2].duty" value="대표"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:<input name="lists[2].turn" value="3"></td>
				<td>appr_kind:<input name="lists[2].appr_kind" value="결재"></td>
			</tr>
			<tr>
				<td>참조자</td>
				<td>seq:문서seq자동입력</td>
				<td>id:<input name="lists[4].id" value="refer1"></td>
				<td>name:<input name="lists[4].name" value="참조자1"></td>
				<td>duty:<input name="lists[4].duty" value="결재테스트부서 사원"></td>
				<td>chk:F로 자동입력</td>
				<td>turn:</td>
				<td>appr_kind:<input name="lists[4].appr_kind" value="참조"></td>
			</tr>
			</table>
			
			<table><!-- 문서 테이블 -->
				<tr>
					<td>글번호</td>
					<td><input type="text" id="seq" name="seq" value="${Ddto.seq}"></td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" id="title" name="title"
						value="${Ddto.title}"></td>
				</tr>
				<tr>
					<td>내용</td>
				</tr>
				<tr>
					<td><textarea class="form-control" id="p_content" name="content">${Ddto.content}</textarea></td>
				</tr>
			</table>
<script type="text/javascript">
	CKEDITOR.replace('p_content', {
		height : 500,
		width : 800
	});
</script>
			<input type="button" value="상신" onclick="apprDoc(${Ddto.seq})">
			<input type="submit" value="임시저장">
			<input type="button" value="취소" onclick="javascript:history.back(-1)"> 
		</form>
		
		<div id="apprDoc" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">상신</h4>
		      </div>
		      <div class="modal-body">
		      <!-- ajax를 통해서 수정하고 넘길 데이터를 표출해줌 -->
		      <form action="#" method="post" id="apprDocUp"></form>
		      </div>
		    </div>
		 </div>
	</div>
	</div>
</body>
</html>
