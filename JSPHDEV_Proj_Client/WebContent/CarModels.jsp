<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="defaultSocket.DefaultSocketClient,java.util.Arrays"%>
	
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

		<form action="CarConfigMenu">
			<h2>Welcome to AK's Cars. Your Car Choice Options:</h2>
			
					<% 
					String[] modelArr = new String[5];
			DefaultSocketClient cSocket = null;
	    	cSocket = new DefaultSocketClient("192.168.0.4", 4444);
	    	cSocket.start();
	    	modelArr = cSocket.list;
	    	   System.out.println("!!!!!!!!!!!!!!!!!!!"+ Arrays.toString(modelArr));
	    	%>
						<select name="makeVal">
						
						    <c:forEach var="make" items="<%=modelArr%>">
						        <option value="${make}"><c:out value="${make}"/></option>
						    </c:forEach>
						     </select>
				<input type="submit" style="color: blue; width: 100px;" value="Configure" name="configCar">
				
					<% cSocket.closeSession(); %>
	    	
	    	
		</form>

	</div>

</body>
</html>