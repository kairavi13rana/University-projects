<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>User Page</title>
</head>
<body>

	
	<br><br>
	
	<!-- search drinker transaction by First name, last name and mobile no -->
	<form method="post" action="LoiginAccess.jsp">
	<!-- input type search  -->
	<table>
	<tr>
	<td>
		<label for="FName"> Full Name: </label>
		</td>
		<td>
		<input type="text" id="FName" name="FName" placeholder="First_name Last_name"><br>
	</td>
	</tr>
		
	<tr>
	<td>
		<label for="phone"> Phone:</label>
		</td>
		<td>
		<input type="tel" id="Phone" name="Phone" placeholder="123-456-6789"
		pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required><br>
	</td>
	</tr>
	
	<tr>
	<td>	
		<input type="submit" value="Login" name="submit">
	</td>
	</tr>
	</table>
	</form>
<br>
<br>

</body>
</html>