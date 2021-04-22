<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Transaction details</title>
</head>
<body>
	
 	
	
	<% 
	try{
		
		//Get the database connection
		dbJDBC db = new dbJDBC(); 
		Connection con = db.getConnection();
		ResultSet result = null;
		
		//Create a SQL statement
		Statement stmt = con.createStatement();
		//Get the dropdown list from the drinker.jsp
		String Trans = request.getParameter("Transactions");
		
		// check for session and get session attribute
		String username = (String)request.getSession().getAttribute("FName");
		
		String query = "";
		if(Trans.equalsIgnoreCase("allTrans")){
			
			
			query="select b.bill_id, b.bar, b.date, b.day, b.time, t.quantity, t.item, b.items_price, b.tax_price, b.tip, b.total_price, t.type,  b.bartender  from bills b "
				+"	inner join transactions t on b.bill_id=t.bill_id where b.drinker='" + username + "' ";
			//Run the query against the database.
			 result = stmt.executeQuery(query);
			%>
			<!--  Make an HTML table to show the results in: -->
			<table>
		<!--  make first row -->
			<tr>
			<!-- make a column -->
				<td>bill_id</td>
				<td>bar</td>
				<td>date</td>
				<td>day</td>
				<td>time</td>
				<td>quantity</td>
				<td>item</td>
				<td>items_price</td>
				<td>tax_price</td>
				<td>tip</td>
				<td>total_price</td>
				<td>type</td>
				<td>bartender</td>
				
			</tr>
			<tr>
			<%
			// parse out the result
			while (result.next()) {
				//make a row
				out.print("<tr>");
				//make a column
				out.print("<td>");
				//Print out current bar name:
				out.print(result.getString("bill_id"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current beer name:
				out.print(result.getString("bar"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("date"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("day"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("time"));
				out.print("</td>");

				out.print("<td>");
				//Print out current price
				out.print(result.getInt("quantity"));
				out.print("</td>");

				out.print("<td>");
				//Print out current price
				out.print(result.getString("item"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("items_price"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("tax_price"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("tip"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("total_price"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("type"));
				out.print("</td>");
				
				out.print("<td>");
				//Print out current price
				out.print(result.getString("bartender"));
				out.print("</td>");
			out.print("</tr>");	
							}
		out.print("</table>");
		
		}
		else{
			
			// get the first column of query as string 
			
			query="select  b.bar, sum(t.quantity)as nbr_items,  sum(b.total_price)as total_amount  from bills b inner join transactions t on b.bill_id=t.bill_id where b.drinker= '" + username + "'  group by bar ";
			 result = stmt.executeQuery(query);
			
			
					
			//Make an HTML table to show the results in:
			out.print("<table>");

			//make a row
			out.print("<tr>");
			//make a column
			out.print("<td>");
			//print out column header
			out.print("bar");
			out.print("</td>");
			//make a column
			out.print("<td>");
			out.print("nbr_items");
			out.print("</td>");
			//make a column
			out.print("<td>");
			out.print("total_amount");
			out.print("</td>");
			out.print("</tr>");

			//make another row
			out.print("<tr>");
			//parse out the results
			while (result.next()) {
				
				//make a column
				out.print("<td>");
				//Print out current number of times user been to each bars:
					
					//String NumberOfTimes ="";
					//NumberOfTimes = result.getString(1);
				//out.print(NumberOfTimes);
				
				out.print(result.getString("bar"));
				out.print("</td>");
				out.print("<td>");
				//Print out current bar name:
				out.print(result.getInt("nbr_items"));
				out.print("</td>");
				out.print("<td>");
				//Print out current date
				out.print(result.getInt("total_amount"));
				out.print("</td>");
				out.print("</tr>");

			}
			out.print("</table>");
		
		}
		
		%>
		
		<% 
		con.close();
		
		
		
		}catch(SQLException e){
			out.println(e);
		}
	
	%>

</body>
</html>