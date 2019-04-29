package Database;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	// You must put your user and password in here for your local database for this
	// to work
	private String driverClass = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://129.3.20.26:3306/scantron?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=EST";
	private String user = "";
	private String password = "";

	private static ConnectionFactory connectionFactory = null;

	/**
	 *
	 * @throws Exception
	 */
	private ConnectionFactory() throws Exception {
		try {
			Class.forName(driverClass);
		} catch (Exception e) {
			throw new Exception("Could not create a connection for the JDBC driver");
		}
	}

	/**
	 * Gets the connection to the database
	 *
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {

		// references the properties file in resource folder to connect to database
		String fileName = "db.properties";
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

			try (InputStream propFile = classLoader.getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            prop.load(propFile);
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (IOException ex) {
        	System.out.println("no properties file found")
        	ex.printStackTrace();
        }

		Connection con = null;
		con = DriverManager.getConnection(url, user, password);
		return con;
	}

	/**
	 * Returns the connection to the database if not null. Creates one if it is null
	 *
	 * @return
	 * @throws Exception
	 */
	public static ConnectionFactory getInstance() throws Exception {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}

		return connectionFactory;
	}

}