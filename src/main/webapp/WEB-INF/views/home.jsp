<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<a href="AjaxInsert.do">����</a> 
<input type="button" value="���" onclick="location.href='AjaxList.do'">
<br><br>

<c:if test="${userInfo eq null}">
	<form action="Login.do" method="POST">
		���̵�<input type="text" name="id"><br>
		��й�ȣ<input type="text" name="pw"><br>
		<input type="submit" value="�α���"><br>
	</form>	
</c:if>

<c:if test="${userInfo ne null}">
	${userInfo.name}�� �ݰ����ϴ�.
	<a href="Logout.do">�α׾ƿ�</a>
</c:if>



</body>
</html>
