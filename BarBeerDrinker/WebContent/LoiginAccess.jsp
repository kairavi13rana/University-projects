<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="DBPJCTpkg.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Insert title here</title>
</head>
  
<body>
<% 
	try{
		//Get the database connection
				dbJDBC db = new dbJDBC(); 
				Connection con = db.getConnection();
				
				ResultSet rs=null;
				PreparedStatement  ps=null;
				String dbName,dbPhone;
				//Make a SELECT query from the table
				ps=con.prepareStatement("select * from drinker where name=? and phone=?");
				
				//Get the selected text_fields from the User_login.jsp
				String t_name = request.getParameter("FName");
				String t_phone= request.getParameter("Phone");
				
				//check for the both textfileds are not empty
				if( !(t_name.equals(null) || t_name.equals("") )
				  && !(t_phone.equals(null) || t_phone.equals("") )	
						) 
				{
					try{
						ps.setString(1,t_name);
						ps.setString(2,t_phone);
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							// get the column values from the databse
							
							dbName=rs.getString("name");
							dbPhone=rs.getString("phone");
							//compare both coulmn name values and user input
							if(t_name.equals(dbName) && t_phone.equals(dbPhone))
							{
								session.setAttribute("FName", t_name);
								response.sendRedirect("MainBBDPage.jsp"); 
							}
						}
						else
						 {
						 out.println("Invalid account <a href='Login.jsp'>try again</a>");
						    
							rs.close();
							ps.close();
						}
					}catch(SQLException sq){
						out.println(sq);
					}
				}
				
				con.close();
	}catch(Exception e){
		out.println(e);
	}
				
%>



</body>
</html>