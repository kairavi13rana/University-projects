<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Modify Page</title>
</head>
<body>
	
	Inserting New Data Into SellsBeer
	<br>
		<form method="post" action="ModifyQuery.jsp">
		<table>
		<tr>    
		<td>Bar</td><td><input type="text" name="bar"></td>
		</tr>
		<tr>
		<td>Beer</td><td><input type="text" name="beer"></td>
		</tr>
		<tr>
		<td>Price</td><td><input type="text" name="price"></td>
		</tr>
		</table>
		<input type="submit" value="Add me!">
		</form>
	<br>	
	
	Inserting New Data Into Beer Table
	<br>
		<form method="post" action="ModifyQryInsrt.jsp">
		<table>
		<tr>    
		<td>Beer</td><td><input type="text" name="beerName"></td>
		</tr>
		<tr>
		<td>Manf</td><td><input type="text" name="manf"></td>
		</tr>
	
		</table>
		<input type="submit" value="Add me!">
		</form>
	
	

</body>
</html>