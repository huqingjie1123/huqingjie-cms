<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CMS登陆</title>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<link href="/resource/css/jquery/screen.css" rel="stylesheet">
<link href="/resource/css/index.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resource/js/jquery.validate.js"></script>
</head>
<body>
	
<div class="container" >
	<h1>用户登陆</h1>
	<hr>
	<span style="color:red">${error }</span>
	<div class="row">
		<div class="col-md-4">
			<form id="form1" action="/passport/login" method="post" >
				<div class="form-group">
					<label for="username" >用户名:</label>
					<input id="username" class="form-control" type="text" name="username" value="${user.username }" >
				</div>
				
				<div class="form-group">
					<label for="password" >密码:</label>
					<input id="password" class="form-control" type="password" name="password" value="${user.password }" >
				</div>
				<div class="form-group form-inline">
						<label for="isRemember">免登陆:</label> <input 
							class="form-check-input" type="checkbox" name="isRemember">
				</div>
				<!-- <div>
					<label for="lable">记住登陆</label>
					<input id="lable" type="checkbox" name="isRemeber" onclick="denglu()" >
				</div> -->
				<div class="form-group">
					<button class="btn btn-info" type="submit" >登陆</button>
					<button class="btn btn-info" onclick="goReg()" type="submit" >注册</button>
					<button class="btn btn-warning" type="submit" >重置</button>
				</div>
				
			</form>
		</div>
		
		<div class="col-md-8" >
			<img alt="" src="/resource/images/reg-in.jpg" style="width: 450px;height: 350px" class="rounded-circle">
		</div>
			
	</div>
</div>	
<script type="text/javascript">

function goReg() {
	location = "/passport/reg"; 
}


$(function(){
	//页面加载时.执行校验
	$("#form1").validate({
	 //1.定义校验规则
	 rules:{
		  username:{
			  required:true,
		  },
		  password:{
			  required:true,
		  }
	  },
	//规则提示
	messages:{
		username:{
			  required:"用户名必须输入",
		  },
		  password:{
			  required:"密码必须输入",
		  }
	}
	})
})
function denglu() {
	location.href = "/posr";
}
</script>
</body>
</html>