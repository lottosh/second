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
		���̵� <input type="text" name="id" id="id"><br>
		<div id="result" style="color:red"></div>
		��й�ȣ <input type="text" name="pw"><br>
		�̸� <input type="text" name="name"><br>
		<input type="submit" value="����"><br>
	</form>

	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		$(document).ready(function(){
			$("#id").keyup(function(){
				var iddata ={
							id: $("#id").val()
						  }	// json ������ �ѱ氪 ���� �����
				//alert(JSON.stringify(data));
				// �׽�Ʈ �ڵ� : json data�� String Ÿ�����κ����ϴ� ��ũ��Ʈ �޼���
				
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
						$("#result").html(args.responseText+" ����!!");
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
						$("#result").html(data.responseText+" ����!!");
					}
					
				});
			});
		}); */
	</script>	
	
</body>
</html>






