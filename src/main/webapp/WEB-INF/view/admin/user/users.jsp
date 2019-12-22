<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户展示页面</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<script type="text/javascript">
	
	function goPage(page) {
		// location.href = "/user/selects?page="+page;
		
		// 在index页面使用，可以在center中部直接显现
		var url = "/admin/user/selects?pageNum="+page;
		$("#center").load(url);
	}
	
	//更新用户状态 locked   1:停用, 0:正常
	function update(id,obj){
		//要改变为的状态
		var locked =$(obj).text()=="正常"?1:0;
		
		$.post("/admin/user/update",{id:id,locked:locked},function(flag){
			if(flag){
				// alert("操作成功");
				//改变内容
				$(obj).text(locked==1?"停用":"正常");
				//改变颜色
				$(obj).attr("class",locked==1?"btn btn-warning":"btn btn-success")
			}else{
				alert("操作失败")
			}
		})
	}
	
	function query() {
		 var url = "/admin/user/selects?name="+$("[name='name']").val();
		 $("#center").load(url)
		// users页面直接使用
		// location.href = "admin/user/selects?name="+$("[name='name']").val();
	}
	
</script>
</head>
<body>

<table class="table table-striped table-bordered table-hover">
	<tr>
		<th colspan="10">
			<div style="margin-bottom: 10px" class="form-inline">
				<label for="username">用户名:</label>
				<input id="username" type="text" name="name" value="${name }" class="form-control">
				&nbsp;
				<button class="btn btn-info" type="button" onclick="query()" >查询</button>
			</div>
		</th>
	</tr>
	<tr>
		<th>序号</th>
		<th>用户名</th>
		<th>昵称</th>
		<th>生日</th>
		<th>注册时间</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${info.list }" var="u" varStatus="i">
		<tr>
			<td>${i.count }</td>
			<td>${u.username }</td>
			<td>${u.nickname }</td>
			<td><fmt:formatDate value="${u.birthday }" pattern="yyyy-MM-dd" /></td>
			<td><fmt:formatDate value="${u.created}" pattern="yyyy-MM-dd" /></td>
			<td>
				<c:if test="${u.locked==0 }">
					<button type="button" class="btn btn-success" onclick="update(${u.id},this)">正常</button>
				</c:if>
				
				<c:if test="${u.locked==1 }">
					<button type="button" class="btn btn-warning" onclick="update(${u.id},this)">停用</button>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	<tr align="center">
		<td colspan="100">
			<jsp:include page="/WEB-INF/view/common/pages.jsp"></jsp:include>
		</td>
	</tr>
</table>

</body>
</html>