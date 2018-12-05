<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Home</title>
<style>
	#homeWrap {width: 500px; margin: auto;]}
	#homeWrap table {width: 100%; border: 1px dotted #9900ff}
	#homeWrap table thead {background-color: #b9b9ff;}
</style>
</head>
<body>
	<div id="homeWrap">
		<h1>Hello World!</h1>
		<p>The time on the server is ${serverTime}.</p>
		<p><a href="emp/get/20">관리자 모드</a></p>
		<p><a href="bbs1List">회원용 게시판</a></p>
	</div>
</body>
</html>