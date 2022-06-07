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
	<form action="AjaxInsertProc.do" method="post">
		아이디 <input type="text" name="id" id="id"><br>
		<div id="result" style="color:red"></div>
		비밀번호 <input type="text" name="pw"><br>
		이름 <input type="text" name="name"><br>
		<input type="submit" value="가입"><br>
	</form>

	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		$(document).ready(function(){
			$("#id").keyup(function(){
				var iddata ={
							id: $("#id").val()
						  }	// json 형태의 넘길값 형태 만들기
				//alert(JSON.stringify(data));
				// 테스트 코드 : json data를 String 타입으로변경하는 스크립트 메서드
				
				$.ajax({
					type:"post",
					url:"isIdExsit.do",
					data:JSON.stringify(iddata),
					contentType:"application/json; charset=utf8",
					dataType:"json",
					success: function(args){
						$("#result").text(args.res);
					},
					error:function(args){
						$("#result").html(args.responseText+" 에러!!");
					}
				});
				
			});
		});
	</script>
	
	
	<script>
		/* $(document).ready(function(){
			$("#id").keyup(function(){
				var params = "id="+$("#id").val();
				$.ajax({
					type:"post",
					url:"isExsit.do",
					data:params,
					dataType:"json",
					success:function(data){
						$("#result").text(data.res);
					},
					error:function(data){
						$("#result").html(data.responseText+" 에러!!");
					}
					
				});
			});
		}); */
	</script>	
	
</body>
</html>






