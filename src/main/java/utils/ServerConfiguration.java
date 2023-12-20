package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import utils.DatabaseClass;

public class ServerConfiguration {
	private static ServerConfiguration dbConf = null;
	public DatabaseClass clsDB = null;
	String jdbcDriver = null;
	String dbUrl = null;
	String databaseName = null;
	String databaseType = null;
	String schema = null;
	// Database credentials
	String user = null;
	String pass = null;
	// logging
	String log = null;
	public String path = null;
	// ArticlesPhotos path
	public String articlesPhotosPath = null;
	public String tmp = null;
	public String max_memory_size;
	public String max_request_size;
	// database link for images
	public String ImagesLink = null;
	// Email
	public String emailHost = null;
	public String emailPort = null;
	public String emailUser = null;
	public String emailpass = null;
	public String emailFromName = null;
	public String emailFromAddress = null;
	public String emailSubject = null;
	public String projectName = SolideConstants.PROJECT;
	
	public String  OutputPath = null;
	public String  delimiter = null;
	public String  fileencoding = null;
	public String  maxrecords = null;

	static Properties props = new Properties();

	private ServerConfiguration() {
	}

	public static ServerConfiguration getInstance() {
		if (dbConf == null) {
			dbConf = new ServerConfiguration();
		}
		return dbConf;
	}

	public void confige() throws IOException {

		String strFile = System.getenv().get("CATALINA_HOME") + SolideConstants.USER_CONFIG;
		props.loadFromXML(new FileInputStream(strFile));
		// Email Sender
		emailHost = props.getProperty(SolideConstants.EMAIL_HOST);
		emailPort = props.getProperty(SolideConstants.EMAIL_PORT);
		emailUser = props.getProperty(SolideConstants.EMAIL_USER);
		emailpass = props.getProperty(SolideConstants.EMAIL_PASS);
		emailFromName = props.getProperty(SolideConstants.EMAIL_FROM_NAME);
		emailFromAddress = props.getProperty(SolideConstants.EMAIL_FROM_ADDRESSE);
		emailSubject = props.getProperty(SolideConstants.EMAIL_SUBJECT);

		// JDBC driver name and database URL
		jdbcDriver = props.getProperty(SolideConstants.DRIVER);
		dbUrl = props.getProperty(SolideConstants.URL);
		// Database credentials
		user = props.getProperty(SolideConstants.USER_NAME);
		pass = props.getProperty(SolideConstants.PASSWORD);
		databaseName = props.getProperty(SolideConstants.NAME);
		databaseType = props.getProperty(SolideConstants.DB_TYPE);
		schema = props.getProperty(SolideConstants.SCHEMA);
		log = props.getProperty(SolideConstants.LOG4j);
		path = props.getProperty(SolideConstants.PATH);


		ImagesLink = props.getProperty(SolideConstants.IMAGESLINK);
		// ArticlesPhotos path
		articlesPhotosPath = props.getProperty(SolideConstants.ARTICLE_PHOTOS_PATH);		
		max_memory_size = props.getProperty(SolideConstants.MAX_MEMORY_SIZE);
		max_request_size = props.getProperty(SolideConstants.MAX_REQUEST_SIZE);
		tmp = props.getProperty(SolideConstants.TMP);		
		
		 OutputPath = props.getProperty(SolideConstants.OUTPUTPATH);
		 String strFileSeparator = System.getProperty( "file.separator" );
			if ( !OutputPath.endsWith( strFileSeparator ) )
			{
				OutputPath += strFileSeparator;
			}
		 delimiter = props.getProperty(SolideConstants.DELIMITER);
		 fileencoding = props.getProperty(SolideConstants.FILEENCODING);
		 maxrecords = props.getProperty(SolideConstants.MAXRECORDS);
		

		try {
			// STEP 2: Register JDBC driver
			Class.forName(jdbcDriver);

			// STEP 3: Open a connection
			clsDB = new DatabaseClass(databaseName);
			clsDB.setDriver(jdbcDriver);
			clsDB.setURL(dbUrl);
			clsDB.setSchema(schema);
			clsDB.setUser(user);
			clsDB.setPassword(pass);
			clsDB.setDBType(databaseType);

		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

}