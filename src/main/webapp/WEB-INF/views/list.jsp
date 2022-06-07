<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	총 개수 : ${cnt} 개<br>


	<form action="AjaxSearchList.do">
		이름검색:<input type="text" name="name">
		<input type="submit" value="검색">
	</form>
	<br>
	idx/id/pw/name<br><br>
	<c:forEach var="dto" items="${list}">
		${dto.idx}/${dto.id}/${dto.pw}/${dto.name}<br>
	</c:forEach>
	
	<c:forEach var="i" begin="1" end="${pageCount}" step="1">
		<a href="AjaxList.do?pageNum=${i}">[${i}]</a>
	</c:forEach>
</body>
</html>








