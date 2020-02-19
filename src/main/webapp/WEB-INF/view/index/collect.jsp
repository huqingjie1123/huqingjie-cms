<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resource/css/index.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
</head>
<body>
${error}
<div align="center" class="container" style="width: 600px">
		<form id="form1">
			
			<input type="hidden" name="articleId" value="${art.id }">
			<div class="form-group form-inline">
				标题:<input type="text" name="text">
			</div>
			<div class="form-group form-inline">
				收藏:<input type="text" name="url" class="form-control">
			</div>
			<div class="form-group form-inline">
				<button type="button" onclick="addCou()">收藏</button>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
		
		function addCou() {
			
			var formData = new FormData($("#form1")[0]);
			
			$.ajax({
				type : "post",
				url : "/go/collect",
				data : formData,
				processData : false,
				contentType : false,
				success : function(flag) {
					if (flag) {
						alert("收藏成功")
						location.href = "/";
					} else {
						alert("失败，url 不正确")
					}
				}
			})	
		}
	
	</script>
</body>
</html>