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
   		PreparedStatement ps =null;
   		ResultSet result =null;
   		
   		String graphType = request.getParameter("graphTrans"); 
   		
   	// check for session and get session attribute
   			String username = (String)request.getSession().getAttribute("FName");
   			
   	//Make a query
   		String query = "" ;
   		if(graphType.equalsIgnoreCase("beersPerDrinker")){
   	   		query = "select sum(t1.quantity) as nbr_beers,  t1.item from bills b1 inner join transactions t1 on b1.bill_id=t1.bill_id where t1.type='beer'   and b1.drinker='" + username + "' group by t1.item order by nbr_beers";
   		}
   		else if(graphType.equalsIgnoreCase("TransPerBar_Week")){
   			// query with choosen bar by user
   			query  = "select Total_amount, concat(bar,' ', day) as barNameDay from( select  b.bar, sum(b.total_price)  as Total_amount, b.day, b.date  from bills b inner join transactions t on b.bill_id=t.bill_id where b.drinker='" + username + "'   group by  b.date,b.bar)as abc";
   		}
   		else{
   			query="select concat(bar,'  ',Months)as barNameMonth, total_amount from( select b.bar, sum(b.total_price) as total_amount, MONTHNAME(b.date) as Months from bills b inner join transactions t on b.bill_id=t.bill_id where b.drinker='" + username + "' and b.date between '2018-01-01' and '2018-12-31' group by Months,b.bar order by date) as abc";  
   		}   		
   		
	   		
	   		//Run the query against the database.
	   		 result = stmt.executeQuery(query);
	   	//Process the result
	   		while (result.next()) { 
	   			map=new HashMap<String,Integer>();
	   			if(graphType.equalsIgnoreCase("beersPerDrinker")){
	   	   			map.put(result.getString("item"),result.getInt("nbr_beers"));
	   	   		}
	   			else if(graphType.equalsIgnoreCase("TransPerBar_Week")){
	   	   			map.put(result.getString("barNameDay"),result.getInt("Total_amount"));
	   			}
	   			else
	   			{
	   				map.put(result.getString("barNameMonth"),result.getInt("Total_amount"));
		   		}
	   				
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
	        if(graphType.equalsIgnoreCase("beersPerDrinker")){
	          chartTitle = "Number Of Beers Order By Drinker";
	          legend = "beers";
	        }
	        else if(graphType.equalsIgnoreCase("TransPerBar_Week")){
	         	 chartTitle = "Transactions In Different Bars Per Weeks";
	         	 legend = "bars_weeks";
	       	 }
	        else{
	        	chartTitle = "Transactions In Different Bars Per Months";
	        	 legend = "bars_months";
	        }
 			
   		

	
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

</body>
</html>