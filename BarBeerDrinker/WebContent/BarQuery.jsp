<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Bar Looking into page</title>
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
		
		//Get the dropdown list from the Bar.jsp
		String barNmeDrpDwn = request.getParameter("BarNameDrpDwn");
		//Get the dropdown list from the Bar.jsp
		String barDetailFromDDwn = request.getParameter("BarsDetailsDrpDwn");
		
		// check for session and get session attribute
		session.setAttribute("BarNameDrpDwn", barNmeDrpDwn);
	
		String username = (String)request.getSession().getAttribute("FName");
		
		String query = "";
		if(barDetailFromDDwn.equalsIgnoreCase("Top10Drinkers")){
			query="select drinker from(select b.bill_id, b.bar, b.drinker, sum(t.quantity) as qnty, sum(b.total_price) as amount  from bills b inner join transactions t on b.bill_id=t.bill_id where b.bar='" +barNmeDrpDwn + "' group by b.drinker order by amount desc) as ab LIMIT 10";
	
			//Run the query against the database.
			 result = stmt.executeQuery(query);
			
			//Make an HTML table to show the results in:
				out.print("<table>");

				//make a row
				out.print("<tr>");
				//make a column
				out.print("<td>");
				//print out column header
				out.print("drinker");
				out.print("</td>");
				out.print("</tr>");
			
				//parse out the results
				while (result.next()) {
					//make a row
					out.print("<tr>");
					//make a column
					out.print("<td>");
					//Print out current bar name:
					out.print(result.getString("drinker"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("</table>");
				con.close();
		}
		else if(barDetailFromDDwn.equalsIgnoreCase("Top10Beers")){
			
			query="select item from(select b.bill_id, b.bar, sum(t.quantity) as nbr_beers_sold, t.item  from bills b "
					+ "inner join transactions t on b.bill_id=t.bill_id where b.bar='" +barNmeDrpDwn + "' and t.type='beer' "
					+ "group by t.item order by nbr_beers_sold desc) as ab LIMIT 10";
			//Run the query against the database.
			 result = stmt.executeQuery(query);
			
			//Make an HTML table to show the results in:
				out.print("<table>");

				//make a row
				out.print("<tr>");
				//make a column
				out.print("<td>");
				//print out column header
				out.print("beer");
				out.print("</td>");
				out.print("</tr>");
			
				//parse out the results
				while (result.next()) {
					//make a row
					out.print("<tr>");
					//make a column
					out.print("<td>");
					//Print out current bar name:
					out.print(result.getString("item"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("</table>");
				con.close();
			
		}
		else if(barDetailFromDDwn.equalsIgnoreCase("TopManufacturers")){
		
			query=   "select * from (select ab.item, br.manf from (select  sum(t.quantity) as nbr_beers_sold, t.item  from bills b " 
					+ "inner join transactions t on b.bill_id=t.bill_id where b.bar='" +barNmeDrpDwn + "' and t.type='beer' group by t.item order by nbr_beers_sold desc) as ab"
					+" inner join beer br on ab.item=br.name) as abc LIMIT 5 ";
			//Run the query against the database.
			 result = stmt.executeQuery(query);
			
			//Make an HTML table to show the results in:
				out.print("<table>");
	
				//make a row
				out.print("<tr>");
				//make a column
				out.print("<td>");
				//print out column header
				out.print("beer");
				out.print("</td>");
				
				//make a column
				out.print("<td>");
				//print out column header
				out.print("manf");
				out.print("</td>");
				out.print("</tr>");
				//parse out the results
				while (result.next()) {
					
					//make a row
					out.print("<tr>");
					//make a column
					out.print("<td>");
					//Print out current bar name:
					out.print(result.getString("item"));
					out.print("</td>");
					
					//make a column
					out.print("<td>");
					//Print out current bar name:
					out.print(result.getString("manf"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("</table>");
				con.close();
		}
		else
		{
	%>	
		<p>Click On Buttons to see Graph</p>
		<form method="post" action="BarGraphDay.jsp">
			<!-- input type clickable and for beer page  -->
				<input type="submit" value="BarGraphDay" name="submit">
		</form>
		<br>
		<form method="post" action="BarGraphWeek.jsp">
		<!-- input type clickable and for beer page  -->
				<input type="submit" value="BarGraphWeek" name="submit">
		</form>
	<% 	}
		
		
	}catch(Exception e){
		out.println(e);	
	}
	%>
	
</body>
</html>	

