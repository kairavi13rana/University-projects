<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
		
	


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Graphs</title>
	
</head>
<body>

<%
	StringBuilder myData = new StringBuilder();
	String strData="";
	String chartTitle="";
	String legend="";
	%>



<% 
try{
		//this list will hold the x-axis and y-axis data as a pair
		ArrayList<Map<String,Integer>> list = new ArrayList();
   		Map<String,Integer> map = null;
   		//Get the database connection
   		dbJDBC db = new dbJDBC(); 
   		Connection con = db.getConnection();		

   		//Create a SQL statement
   		Statement stmt = con.createStatement();
   		
   		String graphType = request.getParameter("BarGraphDay"); 
   		
   	// check for session and get session attribute
		String barNAME = (String)request.getSession().getAttribute("BarNameDrpDwn");
				
   	// check for session and get session attribute
   			String username = (String)request.getSession().getAttribute("FName");
   			
   	//Make a query
   		String query = "" ;
   		
   			
   	   		//query = "select sum(t1.quantity) as nbr_beers,  t1.item from bills b1 inner join transactions t1 on b1.bill_id=t1.bill_id where t1.type='beer'   and b1.drinker='" + username + "' group by t1.item order by nbr_beers";
			
   	   		query= "select bar, date, CONCAT(day,' ','period=', period) as newDayTime,"
   	   				+ "max(nbr_beers_sold)as nbr_beers_sold from(select b.bill_id, b.bar, b.date, b.drinker, b.day, HOUR(b.time)  as period, sum(t.quantity) as nbr_beers_sold, sum(b.total_price) as amt  from bills b inner join transactions t on b.bill_id=t.bill_id where b.bar='" +barNAME + "' and b.time between '08:00' AND '24:00'  group by b.date, period order by b.date, nbr_beers_sold desc) as abc group by date order by date, period";
   		
   		
   		//Run the query against the database.
   		ResultSet result = stmt.executeQuery(query);
   	//Process the result
   		while (result.next()) { 
   			map=new HashMap<String,Integer>();
   			
   	   		map.put(result.getString("newDayTime"),result.getInt("nbr_beers_sold"));
   			list.add(map);
   	    } 
   	    result.close();
   	    
   	 //Create a String of graph data of the following form: ["Caravan", 3],["Cabana",2],...
        for(Map<String,Integer> hashmap : list){
        		Iterator it = hashmap.entrySet().iterator();
            	while (it.hasNext()) { 
           		Map.Entry pair = (Map.Entry)it.next();
           		String key = pair.getKey().toString().replaceAll("'", "");
           		myData.append("['"+ key +"',"+ pair.getValue() +"],");
           	}
        }
 		strData = myData.substring(0, myData.length()-1); //remove the last comma
        
        //Create the chart title according to what the user selected
       
          chartTitle = "Busiest period of the day ";
 	  	 legend = "bars_days";
        
	}catch(SQLException e){
    		out.println(e);
    }
%>
	
	
	<script src="https://code.highcharts.com/highcharts.js"></script>
		<script> 
		
			var data = [<%=strData%>]; //contains the data of the graph in the form: [ ["Caravan", 3],["Cabana",2],...]
			var title = '<%=chartTitle%>'; 
			var legend = '<%=legend%>'
			//this is an example of other kind of data
			//var data = [["01/22/2016",108],["01/24/2016",45],["01/25/2016",261],["01/26/2016",224],["01/27/2016",307],["01/28/2016",64]];
			var cat = [];
			data.forEach(function(item) {
			  cat.push(item[0]);
			});
			document.addEventListener('DOMContentLoaded', function () {
			var myChart = Highcharts.chart('graphContainer', {
			    chart: {
			        defaultSeriesType: 'column',
			        events: {
			            //load: requestData
			        }
			    },
			    title: {
			        text: title
			    },
			    xAxis: {
			        text: 'xAxis',
			        categories: cat
			    },
			    yAxis: {
			        text: 'yAxis'
			    },
			    series: [{
			        name: legend,
			        data: data
			    }]
			});
			});
		
		</script>
		<!-- to get size of graph container -->
		<div id="graphContainer" style="width: 500px; height: 400px; margin: 0 auto"></div>
		
		
	
		
		
		
	
</body>
</html>