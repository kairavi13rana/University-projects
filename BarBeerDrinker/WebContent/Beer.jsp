<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Bar Page</title>
</head>
<body>

<% 
	try{

			dbJDBC db = new dbJDBC(); 
	   		Connection con = db.getConnection();		
	
	   		//Create a SQL statement
	   		Statement stmt = con.createStatement();
	   		PreparedStatement ps =null;
	   		ResultSet rs =null;
	   		// get parameter from Drinker.jsp 
	   		String BarName = request.getParameter("Bar"); 
	   		
	   
	   			//Make a query
		   		String query="";
		   	
		   		 	query = "select name from beer";
		   		 	ps=con.prepareStatement(query);
					rs = ps.executeQuery();
					String BeerNameAll="";
	%>
					Select Beer Name:
			<form method="post" action="BeerQuery.jsp">
					<select name="BeerNameDrpDwn" size=1>
		<%
					 while(rs.next())
						{  // get column name from databse
						 BeerNameAll = rs.getString("name"); 
		%>
						<!-- get values for dropdown list -->
						<option value="<%=BeerNameAll %>"><%=BeerNameAll %></option>
						<% }%>			
					</select>&nbsp;
					
					<!-- drop-down list for see transactions  -->
			<p>Check Beer Details::</p>
				<select name="BeersDrpDwnDetails" size=1>
					<option value="Top5Bars">Top 5 Bars Where Beer Sells The Most </option>
					<option value="allDrinkers">All The Drinkers Who Are The Biggest Consumers Of This Beer</option>	
					<option value="TimeWhenBeerSellsMost"> Time Distribution Of When This Beer Sells The most</option>	
				
				</select>&nbsp;<br> <input type="submit" value="submit">
			</form>
			<br>
 				
<%
	}catch(SQLException e)
	{
		out.println(e);
	}
%>
	

</body>
</html>