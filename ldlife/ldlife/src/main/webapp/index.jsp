<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>...</title>
</head>
<body>
	springmvc文件上传
	<form action="manage/product/upload" method="post" enctype="multipart/form-data">
		<input type="file" name="uploadFile"/>
		<input type="submit" value="springmvc上传文件">
	</form>
	
	richtextImgUpload文件上传
	<form action="manage/product/richtextImgUpload" method="post" enctype="multipart/form-data">
		<input type="file" name="uploadFile"/>
		<input type="submit" value="富文本上传图片文件">
	</form>
</body>
</html>
