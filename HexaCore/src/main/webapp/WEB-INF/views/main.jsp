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
	<sec:authorize access="hasRole('ROLE_ADMIN')" var="auth">${auth }</sec:authorize>
	<sec:authentication property="principal.username"/>
	<sec:authentication property="principal.department_name"/>
	<sec:authentication property="principal.e_rank_name"/>
</body>
</html>