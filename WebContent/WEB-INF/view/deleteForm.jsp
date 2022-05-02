<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글삭제</title>
</head>
<body>
	<form action="delete.do?no=${delReq.articleNumber}" method="post">
	정말 삭제 하시겠습니까?
		<input type="submit" value="삭제">
		
	</form>
</body>
</html>