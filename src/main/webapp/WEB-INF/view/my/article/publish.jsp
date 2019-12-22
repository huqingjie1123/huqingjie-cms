<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content") != null ? request.getParameter("content") : "";
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>KindEditor JSP</title>
	<link rel="stylesheet" href="/resource/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="/resource/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="/resource/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/plugins/code/prettify.js"></script>
	<script>
	$(function() {
		KindEditor.ready(function(K) {
			window.editor1 = K.create('textarea[name="content"]', {
				cssPath : '/resource/kindeditor/plugins/code/prettify.css',
				uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
		
	})
	</script>
</head>
<body>
	<form id="form1"  >
		<div class="form-group">
			<label for="title">文章标题</label>
			<input id="title" class="form-control" type="text" name="title" >
		</div>	
		
		<div class="form-group form-inline">
			<label>栏目:</label>
			<select id="channel" class="form-control" name="channelId" >
				<option value="-1" >--请选择--</option>
			</select>
			
			<label>分类:</label>
			<select id="category" class="form-control" name="categoryId" >
				<option value="-1" >--请选择--</option>
			</select>
		</div>	
	
		<div class="form-group">
			<label>文章标题图片</label>
			<input type="file" name="myFile" class="form-control" >
		</div>
	
		<textarea name="content" cols="100" rows="8" style="width:100%;height:260px;visibility:hidden;"><%=htmlspecialchars(htmlData)%></textarea>
		<br />
		<button class="btn btn-info" type="button" onclick="publish()" >发布</button>	
	</form>
</body>
<script type="text/javascript">
	// 发布文章
	function publish() {
		var formData = new FormData($("#form1")[0]);
		// 封装带html格式的内容
		formData.set("content",editor1.html());
		 $.ajax({
			 type:"post",
			 url:"/my/article/publish",
			 data:formData,
			 processData:false,
			 contentType:false,
			 success:function(flag){
				 if (flag) {
					alert("发布成功");
					location.href="/my";
				}else{
					alert("发布失败");
				}
			 }
			 
		 })
		
	}

	// 查询所有栏目并回显
	$(function() {
		$.get(
			"/my/channel/selects",
			function(list) {
				for (var i in list) {
					$("#channel").append("<option value='"+list[i].id+"' >"+list[i].name+"</option>");
				}
			}
		)
		// 为栏目添加点击事件，即当栏目改变的时候去查询栏目下的分类
		$("#channel").change(function() {
			var channelId = $(this).val();
			
			// 先清空原有栏目
			$("#category").empty();
			$("#category").append("<option>请选择</option>");
			if (channelId=="请选择") {
				return;
			}
			$.get(
				"/my/category/selectsByChannelId",
				{channelId:channelId},
				function(list) {
					for (var i in list) {
						$("#category").append("<option value='"+list[i].id+"' >"+list[i].name+"</option>");
					}
				}
			)
		})
		
	})
	


</script>
</html>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>
	
	
	
</form>

</body>
</html>