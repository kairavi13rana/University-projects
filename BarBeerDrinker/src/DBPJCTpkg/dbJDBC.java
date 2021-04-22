package DBPJCTpkg;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbJDBC {

	public dbJDBC() {
		
	}
	
	//Establishes a connection from the given database URL 
	//@SuppressWarnings("deprecation")
	public Connection getConnection() {
		
		// create connection string
		// here URL = localdatabase name
		String connectionURL = "jdbc:mysql://localhost:3306/barbeerdrinkersample";
		//String connectionURL = "jdbc:mysql://localhost:3306/BarBeerDrinkersample";

		Connection connection = null;
	
		try {
			//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		}catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//Create a connection to your DB
			//connection = DriverManager.getConnection(connectionUrl,"username", "PSWd");
			
			// for access my databse username= root, password Root$123
			connection = DriverManager.getConnection(connectionURL,"root", "Root$123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		dbJDBC db = new dbJDBC();
		Connection connection = db.getConnection();
		
		System.out.println(connection);
		db.closeConnection(connection);

	}

}





