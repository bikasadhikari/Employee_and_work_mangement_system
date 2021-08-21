package connection;
import java.sql.*;
import javax.swing.*;

public class DatabaseConnection {
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/employeeDB";
	private String user = "root";
	private String password = "Bikas@333";
	private JFrame dbErrorDialog = new JFrame();
	
	public String getDriver() {
		return driver;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void logMessage(String msg) {
		System.out.println(msg);
	}
	
	public void showErrorMessage(String errorTitle, String errorMsg) {
		JOptionPane.showMessageDialog(dbErrorDialog, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
	}
	
	public Connection getConnection(Connection conn) { 
		try {
			Class.forName(getDriver());
			logMessage("Driver Loaded");
			conn = DriverManager.getConnection(getUrl(),getUser(),getPassword());
			logMessage("Connection established");
			return conn;
		} catch (ClassNotFoundException e) {
			logMessage("Unable to load driver!");
			showErrorMessage("DatabaseConnectionError", "Unable to load driver!");
		} catch (SQLException e) {
			logMessage(e.getMessage());
			showErrorMessage("SQLException", e.getMessage());				
		}
		return null;
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			rs.close();
			logMessage("Closed Resultset");
		} catch (SQLException e) {
			logMessage(e.getMessage());
		}
	}
	
	public void closePreparedStatement(PreparedStatement pstmt) {
		try {
			pstmt.close();
			logMessage("Closed PreparedStatement");
		} catch (SQLException e) {
			logMessage(e.getMessage());
		}
	}

	
	public void closeConnection(Connection conn) {
		try {
			conn.close();
			logMessage("Closed Connection");
		} catch (SQLException e) {
			logMessage(e.getMessage());
		}
	}
	
	
}