<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Main Homepage For BarBeerDrinker</title>
</head>
<body>
	
	<% //compare session is null or not
	    if ((session.getAttribute("FName") == null)
	    || (session.getAttribute("FName") == "")) {
	%>
	You are not logged in<br/>
	<!-- link back to the login page -->
	<a href="UserLogin.jsp">Please Login</a>
	<%} 
	    else {
	%>
				Welcome <%=session.getAttribute("FName")%>
				<br><br>
				
			
				<table>
					<tr>
					<td>
						<form method="post" action="Drinker.jsp">
				<!-- input type clickable for drinker page -->
							<input type="submit" value="Drinker" name="submit">
						</form>
				</td>
			
				
				
				<td>
						<form method="post" action="Bar.jsp">
				<!-- input type clickable for bar page   -->
							<input type="submit" value="Bar" name="submit">
						</form>
				</td>
				
				
				
				<td>
						<form method="post" action="Beer.jsp">
					<!-- input type clickable and for beer page  -->
								<input type="submit" value="Beer" name="submit">
							</form>
				</td>
				
				
			
				<td>
					
						<form method="post" action="Modify.jsp">
					<!-- input type clickable and for beer page  -->
								<input type="submit" value="Modify" name="submit">
						</form>
				</td>
				</tr>
				</table>
 

<%
    }

%>


</body>
</html>