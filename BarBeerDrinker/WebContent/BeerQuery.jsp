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
		
		//Get the dropdown list from the Bar.jsp
		String beerNmeDrpDwn = request.getParameter("BeerNameDrpDwn");
		//Get the dropdown list from the Bar.jsp
		String beerDetailFromDDwn = request.getParameter("BeersDrpDwnDetails");
		
		
				
		String query = "";
		
		// for first item from the dropdown menu
		if(beerDetailFromDDwn.equalsIgnoreCase("Top5Bars")){
			query="select bar from (select b.bill_id, b.bar,  b.drinker,  sum(t.quantity) as total_sells, t.item, b.total_price, t.type, b.day, b.date  from bills b inner join transactions t on b.bill_id=t.bill_id where t.item='" +beerNmeDrpDwn + "'  group by b.bar order by total_sells desc) as abc LIMIT 5";
			//Run the query against the database.
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
				out.print("</tr>");
			
				//parse out the results
				while (result.next()) {
					//make a row
					out.print("<tr>");
					//make a column
					out.print("<td>");
					//Print out current bar name:
					out.print(result.getString("bar"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("</table>");
				con.close();
		}
		else if(beerDetailFromDDwn.equalsIgnoreCase("allDrinkers")){
			
			query=  "select drinker, total_beers from(select b.bill_id, b.bar,  b.drinker,  sum(t.quantity) as total_beers, t.item  from bills b inner join transactions t on b.bill_id=t.bill_id where t.item='" +beerNmeDrpDwn + "'  "
				+	"group by b.drinker order by total_beers desc) as abc LIMIT 10 ";
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
				
				//make a column
				out.print("<td>");
				//print out column header
				out.print("total_beers");
				out.print("</td>");
				out.print("</tr>");
				//parse out the results
				while (result.next()) {
					
					//make a row
					out.print("<tr>");
					//make a column
					out.print("<td>");
					//get a column name from table:
					out.print(result.getString("drinker"));
					out.print("</td>");
					
					//make a column
					out.print("<td>");
					//get a column name from table:
					out.print(result.getString("total_beers"));
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("</table>");
				con.close();
		}
		else{
				query= "select bar,date,day,period, max(nbr_beers_sold)as nbr_beers_sold, WEEKNO from(select b.bill_id, b.bar, b.date, b.drinker, b.day, YEARWEEK(b.date) as WEEKNO, hour(b.time) as period, sum(t.quantity) as nbr_beers_sold, sum(b.total_price) as amt  from bills b inner join transactions t on b.bill_id=t.bill_id "
					+ "	where t.item='" +beerNmeDrpDwn + "' and b.time between '08:00' AND '24:00' group by bar, date, WEEKNO,period order by date, nbr_beers_sold desc) as abc group by bar,WEEKNO";  
				
				//Run the query against the database.
				 result = stmt.executeQuery(query);
				
				out.println("Time Distribution Per WEEK::");
				out.print("<br><br>");
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
					//print out column header
					out.print("date");
					out.print("</td>");
					
					//make a column
					out.print("<td>");
					//print out column header
					out.print("day");
					out.print("</td>");
					
					//make a column
					out.print("<td>");
					//print out column header
					out.print("period");
					out.print("</td>");
					
					//make a column
					out.print("<td>");
					//print out column header
					out.print("nbr_beers_sold");
					out.print("</td>");
					
					//make a column
					out.print("<td>");
					//print out column header
					out.print("WEEKNO");
					out.print("</td>");
					
					out.print("</tr>");
					//parse out the results
					while (result.next()) {
						
						//make a row
						out.print("<tr>");
						
						//make a column
						out.print("<td>");
						//Print out current bar name:
						out.print(result.getString("bar"));
						out.print("</td>");
						
						//make a column
						out.print("<td>");
						//Print out current bar name:
						out.print(result.getString("date"));
						out.print("</td>");
						
						//make a column
						out.print("<td>");
						//Print out current bar name:
						out.print(result.getString("day"));
						out.print("</td>");
						
						//make a column
						out.print("<td>");
						//Print out current bar name:
						out.print(result.getString("period"));
						out.print("</td>");
						
						//make a column
						out.print("<td>");
						//Print out current bar name:
						out.print(result.getString("nbr_beers_sold"));
						out.print("</td>");
						
						//make a column
						out.print("<td>");
						//Print out current bar name:
						out.print(result.getString("WEEKNO"));
						out.print("</td>");
						
						out.print("</tr>");
					}
					out.print("</table>");
					con.close();
				
		}
	}catch(Exception e){
		out.println(e);	
	}
	%>
</body>
</html>