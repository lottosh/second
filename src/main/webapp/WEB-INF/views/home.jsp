<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<a href="AjaxInsert.do">가입</a> 
<input type="button" value="목록" onclick="location.href='AjaxList.do'">
<br><br>

<c:if test="${userInfo eq null}">
	<form action="Login.do" method="POST">
		아이디<input type="text" name="id"><br>
		비밀번호<input type="text" name="pw"><br>
		<input type="submit" value="로그인"><br>
	</form>	
</c:if>

<c:if test="${userInfo ne null}">
	${userInfo.name}님 반갑습니다.
	<a href="Logout.do">로그아웃</a>
</c:if>



</body>
</html>
