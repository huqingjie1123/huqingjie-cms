<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的收藏</title>
</head>
<body>

<table class="table table-striped table-bordered table-hover">
	<tr>
		<td>ID</td>
		<td>文章</td>
		<td>URL</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${info.list }" var="list">
		<tr>
			<td>${list.id }</td>
			<td>${list.text }</td>
			<td>${list.url }</td>
			<td><button onclick="del(${list.id })">删除</button></td>
		</tr>
	</c:forEach>
	<tr align="center">
		<td colspan="100">
			<jsp:include page="/WEB-INF/view/common/pages.jsp"></jsp:include>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function del(id) {
		
		$.post(
			"my/collect/del",
			{"id":id},
			function(msg) {
				if (msg>0) {
					alert("删除成功");
					location = "/my/index";
				}else{
					alert("删除失败")
				}
			}
		)
		
	}
</script>
</body>
</html>