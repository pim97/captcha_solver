package captchaSolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

	public static DatabaseConnection con = new DatabaseConnection();

	public static DatabaseConnection getDatabase() {
		return con;
	}

	public Connection conn;

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		if (conn == null || conn.isClosed()) {
			DatabaseConnection con = new DatabaseConnection();
			return conn = con.connect();
		}
		return conn;
	}

	// init database constants
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://" + "database_ip" + ":3306/" + "database"
			+ "";
//	private static final String DATABASE_URL = "jdbc:mysql://" + "127.0.0.1" + ":3306/" + "toplistbot"
//	+ "";
	
	private static final String USERNAME = "database_username";
//	private static final String USERNAME = "root";
	private static final String PASSWORD = "database_password";

//	private static final String PASSWORD = "";
	private static final String MAX_POOL = "250";

	// init connection object
	private Connection connection;
	// init properties object
	private Properties properties;

	// create properties
	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("MaxPooledStatements", MAX_POOL);
		}
		return properties;
	}

	// connect database
	public Connection connect() throws ClassNotFoundException {
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, getProperties());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	// disconnect database
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws SQLException, ClassNotFoundException {
		System.out.println(getDatabase().getConnection());
	}

}
