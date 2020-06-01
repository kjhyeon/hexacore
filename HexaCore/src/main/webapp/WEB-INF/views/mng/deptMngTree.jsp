<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./dist/themes/default/style.css">
<title>부서 목록</title>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<script type="text/javascript" src="./dist/jstree.js"></script>
<script type="text/javascript" src="./javascript/deptMngTree.js"></script>

</head>
<body>
<input type="text" id="menu_name" class="jstree-default jstree-search" name="menu_name" value="" onchange="search()" >
<input type="button" id="search_menu" name="search_menu" value="검색하기" onclick="search()">

<div id="deptTree" class="jstree-default"></div>

<input type="button" id="open_all" name="open_all" value="전체열기" onclick="open_all()">
<input type="button" id="close_all" name="close_all" value="전체닫기" onclick="close_all()">

</body>
</html>