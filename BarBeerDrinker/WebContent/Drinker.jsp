<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Drinker Page</title>
</head>
<body>


	<!-- drop-down list for see transactions  -->
	 Transactions::
	<form method="post" action="DRQueryTrans.jsp">
			<select name="Transactions" size=1>
				<option value="allTrans">All Transactions </option>
				<option value="TransPerBar">Number Of Transactions Of Drinker Per Bars</option>	
			</select>&nbsp;
			<br> <input type="submit" value="submit">
		</form>
	<br>

	<!-- drop-down list for see transactions of different bars  -->
	
	Graph For:
	<br>
	<table>
	<tr>
	<td>	
	<form method="post" action="DrGraph.jsp">
	<!--  another drop down list for monthly and weekly transactions	 -->
			<select name="graphTrans" size=1>
				<option value="beersPerDrinker">Number Of Beers Order By Drinker</option>
				<option value="TransPerBar_Week">Transactions In Different Bars Per Weeks</option>
				<option value="TransPerBar_Month">Transactions In Different Bars Per Months</option>
			</select>&nbsp;<br> <input type="submit" value="submit">
		</form>
	</td>
	</tr>
	</table>

</body>
</html>