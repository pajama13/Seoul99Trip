<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
</head>
<body>
<div class="wrapper row3">
  <main class="container clear">
    <h2 class="sectiontitle">자유 게시판</h2>
    <div style="height: 5px"></div>
    <div style="height: 550px">
    	<table class="table">
    		<tr>
    		<c:if test="${sessionScope.id==null }">
    		<td><span class="whitegray_btn" style="float:left">글쓰기</span> &nbsp; <span style="margin:0 20px;float:left;line-height:46px;color:gray;">로그인 후 이용 가능합니다</span></td>
    		</c:if>
    		<c:if test="${sessionScope.id!=vo.id }">
    			<td>
    				<a href="../board/insert.do" class="whitegray_btn" style="float:left">글쓰기</a>
    			</td>
    		</c:if>
    		</tr>
    	</table>
    	<table class="table">
    	 <tr>
    	 	<th width=10% class="text-center">번호</th>
    	 	<th width=30% class="text-center">제목</th>
    	 	<th width=15% class="text-center">이름</th>
    	 	<th width=15% class="text-center">작성일</th>
    	 	<th width=10% class="text-center">조회수</th>
    	 	<th width=20% class="text-center">첨부파일</th>
    	 </tr>
    	 <%--
    	 		for(FreeBoardVO vo:request.getAttribute("list"));
    	  --%>
    	 <c:forEach var="vo" items="${list }"> <%-- request.setAttribute("list", list)  => request.getAttribute("list") == ${list }--%>
    	 	<tr>
    	 		<td width="10%" class="text-center">${vo.bno }</td> <%-- vo.getNo() --%>
    	 		<td width="30%" style="color: black">
    	 		
    	 		<a href="../board/detail.do?bno=${vo.bno }"style="color: black">${vo.title }</a> &nbsp;
    	 		<c:if test="${vo.dbday==today }">
    	 			<sup><img src="../img/new.gif"></sup>
    	 		</c:if>
    	 		</td>
    	 		<td width="15%" class="text-center">${vo.id }</td>
    	 		<td width="15%" class="text-center">${vo.dbday }</td>
    	 		<td width="10%" class="text-center">${vo.hit }</td>
    	 		<td width="20%" class="text-center">
    	 			<c:if test="${vo.filesize>0 }">
    	 				<img src="../img/file.png" style="width:20px;height:20px" class="img-circle">
    	 			</c:if>
    	 		</td>
    	 	</tr>
    	 </c:forEach>
    	</table>
    	</div>
    	<table class="table">
    		<tr>
    			<td class="text-center">
    				<a href="../board/list.do?page=${curpage>1?curpage-1:curpage }" class="btn btn-sm btn-primary">이전</a>
    				${curpage } page / ${totalpage } pages
    				<a href="../board/list.do?page=${curpage<totalpage?curpage+1:curpage }" class="btn btn-sm btn-primary">다음</a>
    			</td>
    		</tr>
    	</table>
  </main>
  </div>
</body>
</html>