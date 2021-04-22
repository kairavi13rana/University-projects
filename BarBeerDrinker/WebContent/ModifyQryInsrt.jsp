<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Beer Looking into page</title>
</head>
<body>
	
 	
	
	<% 
	try{
		
		//Get the database connection
		dbJDBC db = new dbJDBC(); 
		Connection con = db.getConnection();
		ResultSet result = null;
		PreparedStatement ps =null;
		
		//Create a SQL statement
		Statement stmt = con.createStatement();
		
		//Get parameters from the HTML form at the Modify.jsp
				String newBar = request.getParameter("beerName");
				String newManf = request.getParameter("manf");
				
		
		
				
		String query = "";
		
		//Make an insert statement for the Sells table:
				query = "INSERT INTO beer(name, manf)"
						+ "VALUES (?, ?)";
				//Create a Prepared SQL statement allowing you to introduce the parameters of the query
				ps = con.prepareStatement(query);

				//Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
				ps.setString(1, newBar);
				ps.setString(2, newManf);
				
				//Run the query against the DB
				ps.executeUpdate();
				
				
				//Close the connection. Don't forget to do it, otherwise you're keeping the resources of the server allocated.
				con.close();
				out.print("insert succeeded");
	
   		} catch (SQLException e) {
      		  out.println(e);
  	}
		
	%>

</body>
</html>