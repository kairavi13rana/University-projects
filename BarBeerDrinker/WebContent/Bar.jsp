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
	   		
	   	// check for session and get session attribute
	   			String username = (String)request.getSession().getAttribute("FName");
	   			
	   			//Make a query
		   		String query="";
		   	
		   		 	query = "select name from bar";
		   		 	ps=con.prepareStatement(query);
					rs = ps.executeQuery();
					String BnameAll="";
	%>
					Select Bar Name:
		<form method="post" action="BarQuery.jsp">
					<select name="BarNameDrpDwn" size=1>
		<%
					 while(rs.next())
						{  // get column name from databse
						 BnameAll = rs.getString("name"); 
		%>
						<!-- get values for dropdown list -->
						<option value="<%=BnameAll %>"><%=BnameAll %></option>
						<% }%>			
					</select>&nbsp;
					
					<!-- drop-down list for see transactions  -->
	<p> Check Bars ::</p>
			<select name="BarsDetailsDrpDwn" size=1>
				<option value="Top10Drinkers">Top 10 Largest Spending Drinkers </option>
				<option value="Top10Beers">Top 10 Most Popular Beers</option>	
				<option value="TopManufacturers">TOP 5 Manufacturers Who Sell The Most Beers.</option>	
				<option value="BusyDayWeek">Busiest Period Of The Day And Week</option>	
				
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