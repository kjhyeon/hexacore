<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="./../header.jsp" %>
	<!-- 권한 -->
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth">${auth}</sec:authorize>
	<!-- username : id  -->
	<sec:authentication property="principal.username"/>
	<!-- name : 사원이름 -->
	<sec:authentication property="principal.name"/>
	<!-- department_name : 부서이름 -->
	<sec:authentication property="principal.department_name"/>
	<!-- e_rank_name : 사원직위 -->
	<sec:authentication property="principal.e_rank_name"/>
	<a href="./goEapprHome.do">전자결재 홈으로 가기</a>
	<a href="./goDocTypeMng.do">문서 양식 관리로 가기</a>
<%@include file="./../footer.jsp" %>
</body>
</html>