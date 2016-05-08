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

		<form  action="CarConfigMenu">
			<h2>Your configuration for :</h2>
			<h1>${ch}</h1>
			<br>
			<table border="1" style="width: 60%">
				<tr>
					<td>Model</td>
					<td id="modelVal">${ch}</td>
					<td id="colorPri">18445</td>
				</tr>
				<tr>
					<td>Color</td>
					<td id="colorVal">${colorVal}</td>
					<td id="colorPri">${colorPri}</td>
				</tr>
				<tr>
					<td>Transmission</td>
					<td id="transmissionVal">${transmissionVal}</td>
					<td id="transmissionPri">${transmissionPri}</td>
				</tr>
				<tr>
					<td>Brakes</td>
					<td id="brakeVal">${brakeVal}</td>
					<td id="brakePri">${brakePri}</td>
				</tr>
				<tr>
					<td>AirBags</td>
					<td id="airbagsVal">${airbagsVal}</td>
					<td id="airbagsPri">${airbagsPri}</td>
				</tr>
				<tr>
					<td>PowerMoonroof</td>
					<td id="pmroofVal">${pmroofVal}</td>
					<td id="pmroofPri">${pmroofPri}</td>
				</tr>
			</table>
			
			<h3>Total Price for your car selection is : ${18445+colorPri+transmissionPri+brakePri+airbagsPri+pmroofPri} :</h3>
			<br>
			<!-- <a href="http://192.168.0.4:8080/JSPHDEV_Proj_Client/CarModels.jsp">Return to Home-Page</a> -->
			   </form>

	</div>

</body>
</html>