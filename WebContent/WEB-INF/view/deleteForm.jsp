<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>
<form action="delete.do" method="post">
글을 삭제하시겠습니까?
<p>
 <input type="hidden" name="no" value="${modReq.articleNumber}">
<input type="hidden" name="title" value="${modReq.title}">
<input type="hidden" name="content" value="${modReq.content}">
</p>
<input type="submit" value="글 삭제">
</form>
</body>
</html>