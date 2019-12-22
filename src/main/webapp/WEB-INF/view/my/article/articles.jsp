<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文章列表</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
</head>
<body>

	<div class="container-fluid">

		<div>
			<c:forEach items="${info.list }" var="art">
				<ul class="list-unstyled">
					<li class="media">
						<img src="/pic/${art.picture }" class="mr-3" alt="..." style="width: 150px" height="120px" >
						
						<div class="media-body">
							<h5 class="mt-0 mb-1"><a href="/my/article/article?id=${art.id }" target="_blank">${art.title }</a></h5>
							
							<div style="margin-top : 50px" >
								<fmt:formatDate value="${art.created }" pattern="yyyy-MM-dd" />
								
								<span style="float: right">
									<c:if test="${art.deleted==0 }">
										<button type="button" onclick="update(${art.id},this)" class="btn-sm btn-danger" >删除</button>
									</c:if>
									
									<c:if test="${art.deleted==1 }">
										<button type="button" onclick="update(${art.id},this)" class="btn-sm btn-danger" >已删除</button>
									</c:if>
								</span>
							</div>
						
						</div>
					</li>
				</ul>
				<hr/>
			</c:forEach>
		</div>


		<jsp:include page="/WEB-INF/view/common/pages.jsp"></jsp:include>


	</div>


	<script type="text/javascript">
		function goPage(page) {
			// location.href = "/user/selects?page="+page;
			// 在index页面使用，可以在center中部直接显现
			var url = "/my/article/articles?pageNum=" + page;
			$("#center").load(url);
		}
		
		function update(id,obj) {
			
			var deleted = $(obj).text()=="删除"?1:0;
			
			$.post(
				"/my/article/update",
				{id:id,deleted:deleted},
				function(flag) {
					if (flag) {
						$(obj).text(deleted==1?"已删除":"删除")
						$(obj).attr("class",deleted==1?"btn-sm btn-warning":"btn-sm btn-danger")
					}else{
						alert("操作失败")
					}
				}
			)
			
		}
		
		
	</script>
</body>
</html>