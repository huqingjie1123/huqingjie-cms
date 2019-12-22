<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${art.title }</title>
<!-- 引入样式 -->
<link href="/resource/css/bootstrap.css" rel="stylesheet">

</head>
<body>
	<div class="container">
	  <h1 align="center">${art.title }</h1>
	  <h3>${art.user.username} <fmt:formatDate value="${art.created }" pattern="yyyy-MM-dd HH:mm:ss"/></h3>
	  <div align="center"> ${art.content }</div> 
	
	</div>
</html>