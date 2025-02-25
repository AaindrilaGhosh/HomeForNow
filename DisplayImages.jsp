<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display Image</title>
</head>
<body>
<h1 style="color:red"  align="center">Display Image Detail </h1>
<div align="center">
<form action="DisplayImageController" method="post">
Enter Image ID:
<input type="number" name="id">
<input type="submit" value="Display Image">
</form>
</div>
<%--<%

String imgFileName=(String)request.getAttribute("img");
String imgId=(String)request.getAttribute("ID");
System.out.println(imgFileName);
%> --%>

<div align="center">
<table border="5px" style="width:400px" >
<tr>
	<th>Image ID</th>
	<th>Image</th>
</tr>
<%--

   if(imgFileName!="" && imgId!="")
   {

--%>
<tr>
	<td ><img src="demo/<%--=imgFileName %>" style= "width:400px; height:400px;"></td>
	</tr>
	<%
	
   }
	--%>
</table>
</div>
</body>
</html>