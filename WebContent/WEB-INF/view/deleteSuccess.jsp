<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.hidden_div{ display: none }
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	게시글 삭제가 완료되었습니다.
	 	<div class="hidden_div">${ ctxPath = pageContext.request.contextPath }</div>
	<a href="${ctxPath}/article/list.do">[ 게시글 목록 보기 ]</a>
</body>
</html>