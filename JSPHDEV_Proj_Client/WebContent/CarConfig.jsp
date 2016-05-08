<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.Arrays"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
body {
background-image:
	url('http://cdn3.crunchify.com/wp-content/uploads/2013/03/Crunchify.bg_.300.png');
}
input[type=submit] {
    padding:15px 15px; 
    background:#58FAF4; 
    border:0 none;
    cursor:pointer;
    -webkit-border-radius: 5px;
    border-radius: 5px; 
    font:Georgia, serif;
    font-size: 15px;
    font-weight: bold;
    margin-left:23%;margin-right:50%;display:block;margin-top:2%;margin-bottom:95%
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Best Cars</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
</head>
<body>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>

	<div align="left" style="margin-top: 50px;">

		<form action="CarConfigMenu">
		   <input name="ch" type="hidden" value="${choice}"/>
			<h2>You have selected </h2>
			<h1>${choice}</h1>
			<h2>Configure your Car</h2>

			<br>
			<table border="1" style="width: 60%">
				
				<tr>
					<td>Color</td>
					<td><select name="colorVal">
							<c:forEach var="col" items="${colorVal}">
								<option value="${col}"><c:out value="${col}" /></option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>Transmission</td>
					<td><select name="transmissionVal">
							<c:forEach var="col" items="${transmissionVal}">
								<option value="${col}"><c:out value="${col}" /></option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>Brakes</td>
					<td><select name="brakeVal">
							<c:forEach var="col" items="${brakeVal}">
								<option value="${col}"><c:out value="${col}" /></option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>AirBags</td>
					<td><select name="airbagsVal">
							<c:forEach var="col" items="${airbagsVal}">
								<option value="${col}"><c:out value="${col}" /></option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>PowerMoonroof</td>
					<td><select name="pmroofVal">
							<c:forEach var="col" items="${pmroofVal}">
								<option value="${col}"><c:out value="${col}" /></option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
			<input type="submit" id="submit"  value="Show total price" name="showPrice">
		</form>

	</div>

</body>
</html>