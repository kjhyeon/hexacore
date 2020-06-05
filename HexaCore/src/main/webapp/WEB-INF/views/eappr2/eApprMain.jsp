<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="./css/bootstrap.min.css">
  <script src="./js/jquery.min.js"></script>
  <script src="./js/bootstrap.min.js"></script>
  <style>    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
  </style>
</head>
<body>
<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal.username" var="id"/>
    <div class="well">
        <p><a href="#">상신문서함</a></p>
        <p><a href="#">결재 문서함</a></p>
        <p><a href="./docLists.do?number=결재필요">결재필요문서</a></p>
        <p><a href="./docLists.do?number=결재중">결재중문서</a></p>
        <p><a href="#">결재반려문서</a></p>
        <p><a href="#">참조 문서함</a></p>
        <p><a href="#">완료 문서함</a></p>
        <p><a href="#">반려 문서함</a></p>
        <p><a href="#">저장 문서함</a></p>
      </div>
</sec:authorize>
</body>
</html>
