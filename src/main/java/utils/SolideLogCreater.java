package utils;
import java.io.File;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;

/**
 * creates a logfile with log4J
 * 
 *
 */
public class SolideLogCreater
{
    
    static final String LOG = "log";
    static final String PATH = "path";
    static final String LOG4J = "log4j";

    private String strPropertyFile = "log4j.properties";
    
    /**
	 * calls immedeatly the {@link #initLog4J(java.lang.String, java.lang.String)} method.
     * 
     * 
     * @param strUserEnvDir	the directory where the logfile is stored
     */
    public SolideLogCreater( String strUserEnvDir )
    {
        this.initLog4J( strUserEnvDir, this.strPropertyFile);
    }
    /**
	 * calls immedeatly the {@link #initLog4J(java.lang.String, java.lang.String) } method.
     * 
     * 
     * @param strUserEnvDir	the directory where the logfile is stored
     * @param strProperty	the propertyfile where to read the properties from( normally "log4j.properies" )
     */
    public SolideLogCreater( String strUserEnvDir, String strProperty )
    {
        this.strPropertyFile = strProperty;
        this.initLog4J( strUserEnvDir, this.strPropertyFile);
    }
    /**
     * constructor using the configuration to find stuff for logging ( log4j.property file ... )
     * and calls the {@link #initLog4J(java.lang.String, java.lang.String) } method.
     * 
     * 
     * @param  clsGLSConfig for handle the config file
     */
    public SolideLogCreater( ServerConfiguration clsConfig )
    {
    	ServerConfiguration serverConfig = ServerConfiguration.getInstance();
    	String strLogPath = serverConfig.path;
        
        try
        {			
            if ( ! strLogPath.endsWith( System.getProperty( SolideConstants.FILE_SEPARATOR ) ) )
            {
                strLogPath = strLogPath + System.getProperty( SolideConstants.FILE_SEPARATOR);
            }
            String strProperty = serverConfig.log;
            this.initLog4J( strLogPath, strProperty );
        }
        catch ( Exception Ex )
        {
            System.err.println( Ex.getMessage() );
        }
    }
    /**
     * The <b>initLog4J</b> method calls the {@link #initLog4J(java.lang.String, java.lang.String, long) } using the a default delay of 1 min.
     *
     *
     * @param   strUserEnvDir    the directory to write the log file
     * @param   strProperty      the file for the properties
     */
    private void initLog4J( String strUserEnvDir, String strProperty )
    {
		this.initLog4J( strUserEnvDir, strProperty, 1000 * 60 );
	}
	/**
     * The <b>initLog4J</b> method load the log4j properties file for the
     * application and set the right path to the log file.
     *
     *
     * @param   strUserEnvDir    the directory to write the log file
     * @param   strProperty      the file for the properties
	 * @param	dDelay			 the delay, when to watch the property file
     */
    private void initLog4J( String strUserEnvDir, String strProperty, long dDelay )
    {
        System.setProperty( "user.envdir", strUserEnvDir );

        if ( ! ( new File( strUserEnvDir ) ).exists() )
        {
           ( new File( strUserEnvDir ) ).mkdir(); 
        }
        // get url to config file
        //URL urlLog4JConfig = getClass().getResource( KaboConstants.LOG4J_PROPERTIES );
        URL urlLog4JConfig = null;
        try
        {
            urlLog4JConfig = new File( strProperty ).toURI().toURL();
        }
        catch ( Exception Ex )
        {
            System.err.println( "initLog4J: initialisation of Log4J failed: Can't find file( " + strProperty + " )!" );
            return;
        }
        // load properties form url
        if ( urlLog4JConfig == null )
        {
            System.err.println( "initLog4J: initialisation of Log4J failed: Can't find file log4j.properties in classpath!" );
            return;
        }
        // init from configuration file
		if ( dDelay == 0 )
		{
			PropertyConfigurator.configure( urlLog4JConfig );
		}
		else
		{
			PropertyConfigurator.configureAndWatch( urlLog4JConfig.getFile(), dDelay );
		}
    } // end method initLog4J
    
}

