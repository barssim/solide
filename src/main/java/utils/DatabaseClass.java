package utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * container for holding a database connection. *
 * 
 */
public class DatabaseClass
{

    public static final String DATABASE		= "database";
	public static final String NAME			= "name";
    public static final String DRIVER		= "driver";
    public static final String URL			= "url";
    public static final String SCHEMA		= "schema";
    public static final String DBUSER		= "user";
    public static final String DBPASSWORD	= "password";
	public static final String PASSWORDFILE	= "passwordfile";
	public static final String DBTYPE		= "dbtype";


	public enum	EDBType{ UNKNOWN, DB2, POSTGRESQL, DERBY, MYSQL, INFORMIX, INTERBASE, JAVADB, FIREBIRD }

	private String[]	arTables		= null;
    private String		strName			= null;
    private String		strDriver		= null;
    private String		strURL			= null;
    private String		strSchema		= null;
    private String		strUser			= null;
    private String		strPassword		= null;
	private	String		strApplication	= null;
	private EDBType		eDBType			= EDBType.UNKNOWN;
    private Connection	con				= null;
	private Logger logLogger = Logger.getLogger( DatabaseClass.class );

	private boolean bIsInUse = false;
	private Date dtLastUsage = null;

    /**
     *  constructor.
     *  
     *
     *  @param strDbName	the database name for this connection
     *
     **/
    public DatabaseClass( String strDbName )
    {
        this.strName = strDbName;
    }

    /**
     *  returns the Connection of the database alias.     *
     *  
     *
	 *  @return Connection	the connection for this database
	 *
	 *  @throws SQLException
	 *  @throws ClassNotFoundException
	 *  @throws InstantiationException
	 *  @throws IllegalAccessException
     *
     **/
    public Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        if ( this.con == null )
        {
			Class.forName( this.strDriver ).newInstance();
            this.con = DriverManager.getConnection( this.strURL, this.strUser, this.strPassword );
			if( logLogger.isDebugEnabled() )
			{
				logLogger.debug( "Set default transaction isolation level <READ UNCOMMITTED>." );
			}
			this.con.setTransactionIsolation( Connection.TRANSACTION_READ_UNCOMMITTED );

			// Set database type specific settings.
			// (e.g. clientinfo to lookup the process on "green screen" (i5), if DB2 is used)
			String strSQL = null;
			switch( this.getDBType() )
			{
				case DB2		:	// Setting the schema is similar from DB2 to Derby/JavaDB.
				case JAVADB		:	// JavaDB is similar to Derby.
				case DERBY		:	// Set schema, if defined.
									if( this.strSchema != null )
									{
										strSQL = String.format( "SET SCHEMA %1$s", this.strSchema );
										logLogger.debug( "Set schema for connection <" + this + "> : " + strSQL );
										this.con.createStatement().execute( strSQL );
									}
									break;

				case POSTGRESQL	:	// Set schema, if defined.
									if( this.strSchema != null )
									{
										logLogger.debug( "Set search path for connection <" + this + "> : " + strSQL );
										strSQL = String.format( "SET SEARCH_PATH TO %1$s", this.strSchema );
										this.con.createStatement().execute( strSQL );
									}
									break;

				case MYSQL		:	// Set schema, if defined.
									if( this.strSchema != null )
									{
										logLogger.debug( "Set schema for connection <" + this + "> : " + strSQL );
										strSQL = String.format( "USE %1$s", this.strSchema );
										this.con.createStatement().execute( strSQL );
									}
									break;

				case INFORMIX	:	// Schemas are not supported with Informix.
				case INTERBASE	:	// Interbase is similar to Firebird.
				case FIREBIRD	:	// Schemas and namespaces are not supported. (see http://www.firebirdfaq.org/faq232/)
									break;

				default			:	break;
			}
		}
        return ( this.con );
    }

    /**
     *  returns the "name" of the database alias.    *
     *  
     *
     *  @return String	the database name of this connection
     *
     **/
    public String getName()
    {
        return ( this.strName );
    }

    /**
     *  sets the driver.
     *
     *
     *  @param strDriv	the driver for this connection
     *
     **/
    public void setDriver( String strDriv )
    {
        this.strDriver = strDriv;
    }

	/**
     *  sets the database type, defined by enum EDBType.
     *
     *
     *  @param strDBType	An enum type to identify the database.
     **/
    public void setDBType( String strDBType )
    {
		try
		{
			this.eDBType = EDBType.valueOf( strDBType );
		}
		catch( Exception e )
		{
			this.eDBType = EDBType.UNKNOWN;
		}
    }

	/**
     *  Simple getter to return the set enum for the database type.
     *
     *
     *  @return EDBType		An enum type to identify the database.
     **/
    public EDBType getDBType()
    {
		return this.eDBType;
    }

    /**
     *  returns the driver of the database alias.
     *
     *
     *  @return String	the driver of this connection
     *
     **/
    public String getDriver()
    {
        return ( this.strDriver );
    }

    /**
     *  sets the password.
     *
     *
     *  @param strPassword	the password for this connection
     *
     **/
    public void setPassword( String strPassword )
    {
        this.strPassword = strPassword;
    }

    /**
     *  returns the password of the database alias.
     *
     *
     *  @return String	the password of this connection
     *
     **/
    public String getPassword()
    {
        return ( this.strPassword );
    }

    /**
     *  sets the schema.
     *
     *
     *  @param strSchema	the schema for this connection
     *
     **/
    public void setSchema( String strSchema )
    {
        this.strSchema = strSchema;
    }

    /**
     *  returns the schema of the database alias.
     *
     *
     *  @return String	the schema of this connection
     *
     **/
    public String getSchema()
    {
        return ( this.strSchema );
    }

    /**
     *  sets the url.
     *
     *
     *  @param strURL	the url for this connection
     *
     **/
    public void setURL( String strURL )
    {
        this.strURL = strURL;
    }

    /**
     *  returns the url of the database alias.
     *
     *
     *  @return String	the url of this connection
     *
     **/
    public String getURL()
    {
        return ( this.strURL );
    }

    /**
     *  sets the user.
     *
     *
     *  @param strUser	the user for this connection
     *
     **/
    public void setUser( String strUser )
    {
        this.strUser = strUser;
    }

    /**
     *  returns the user of the database alias.
     *
     *
     *  @return String	the user of this connection
     *
     **/
    public String getUser()
    {
        return ( this.strUser );
    }

    /**
     *  sets the applicationname.
     *
     *
     *  @param strApplication 	the name of the application
     *
     **/
    public void setProcessName( String strApplication )
    {
        this.strApplication = strApplication;
    }

    /**
     * returns a copy of this class.
     *
     *
     * @return DatabaseClass	the copy of this connection
     **/
    public DatabaseClass getCopy()
    {
        DatabaseClass clsDb	= new DatabaseClass( this.strName );
        clsDb.setDriver(	this.strDriver );
        clsDb.setURL(		this.strURL );
        clsDb.setSchema(	this.strSchema );
        clsDb.setUser(		this.strUser );
        clsDb.setPassword(	this.strPassword );
		clsDb.setProcessName( this.strApplication );
		clsDb.setDBType(	this.getDBType().name() );
        return ( clsDb );
    }

	/**
	 * getTables returns string array of tablenames.
     *
     *
	 * @return String[]
	 */
	public String[] getTables()
	{
		if ( this.arTables == null )
		{
			ArrayList< String > arTbl = new ArrayList< String >();
			try
			{
				String[] arTblTypes = { "TABLE", "VIEW", "SYSTEM TABLE", "ALIAS", "SYNONYM" };
				DatabaseMetaData clsMetaData = this.getConnection().getMetaData();
				ResultSet rsMeta = clsMetaData.getTables( null, this.getSchema(), "%", arTblTypes );
				while ( rsMeta.next() )
				{
					arTbl.add( rsMeta.getString( "TABLE_NAME" ) );
				}
				rsMeta.close();
				this.arTables = new String[ arTbl.toArray().length ];
				for ( int i = 0; i < arTbl.size(); i++ )
				{
					this.arTables[ i ] = arTbl.get( i ).toUpperCase();
				}
			}
			catch( Exception ex )
			{
				logLogger.error( ex.getClass().getSimpleName() + " occured. Cannot retrieve tables ("
							   + ex.getMessage() + ")");
			}
		}
		return ( arTables );
	}

	/**
	 * This method closes the internal Connection object, if possible and sets NULL.
	 * Due to this, the next call of getConnection() creates a new instance of Connection.
	 */
	void resetInternalConnection()
	{
		logLogger.debug( "start resetInternalConnection" );
		CloseNativeConnection clsNative = new CloseNativeConnection();
		clsNative.start();
		logLogger.debug( "started thread resetInternalConnection" );
	}

	/**
	 * Set this connection to status "in use". The caller may decide, if the last usage timestamp should be updated, or not.
	 * @param bInUse
	 * @param bUpdateUsage
	 */
	void setInUse( boolean bInUse, boolean bUpdateLastUsage )
	{
		if( bUpdateLastUsage )
		{
			this.dtLastUsage = new Date();
		}
		this.bIsInUse = bInUse;
	}

	/**
	 * Get last usage timestamp.
	 * @return Return timestamp (Date) of last usage.
	 */
	public Date getLastUsage()
	{
		return this.dtLastUsage;
	}

	/**
	 * Return boolean value, whether this database class is in use, or not.
	 * @return Boolean value, whether this DatabaseClass is currently in use, or not.
	 */
	public boolean isInUse()
	{
		return this.bIsInUse;
	}

	/**
	 * This method overrides the toString() method. The result is almost the same, but the full qualified class name
	 * is replaced by the simple classname (DatabaseClass) to minimize logging output.
	 * <br><br>
	 * getClass().getSimpleName() + '@' + Integer.toHexString( hashCode() )
	 * @return The String representation of this object.
	 */
	@Override
	public String toString()
	{
		return ( getClass().getSimpleName() + '@' + Integer.toHexString( hashCode() ) );
	}

	/**
	 * This method checks, if the given PreparedStatement is already ready prepared, or not.
	 * @param  pstCheck
	 * @return Boolean value, whether the given PreparedStatement is valid and prepared, or not.
	 */
	@SuppressWarnings("finally")
	public boolean isStatementPrepared( PreparedStatement pstCheck )
    {
        try
        {
			if ( pstCheck != null && pstCheck.getConnection() != this.getConnection() )
			{
				pstCheck.close();
				pstCheck = null;
			}
			else
			{
				if ( this.getConnection() != null && this.getConnection().isClosed() )
				{
					logLogger.debug( "Connection " + this + " was closed" );
					pstCheck = null;
					this.con = null;
				}
			}
		}
		catch( Error err )
        {
			pstCheck = null;
			this.con = null;
			logLogger.warn( err.getClass().getSimpleName() +
							" error occured during check of PreparedStatement : recreate new (" + err.getMessage() + ")" );
        }
		catch ( SQLException ex )
		{
			pstCheck = null;
			this.con = null;
			logLogger.warn( ex.getClass().getSimpleName() +
							" SQLException occured during check of PreparedStatement( SQLState :" + ex.getSQLState() +
							" / " + ex.getErrorCode() + " : recreate new(" + ex.getMessage() + ")" );
		}
		catch ( Exception ex )
		{
			pstCheck = null;
			this.con = null;
			logLogger.warn( ex.getClass().getSimpleName() +
							" exception occured during check of PreparedStatement : recreate new(" + ex.getMessage() + ")" );
		}
		finally
        {
            return pstCheck != null;
        }
	}
	/**
	 * This method closes the native datbase connection
	 */
	private class CloseNativeConnection extends Thread
	{
		@Override
		public void run()
		{
			logLogger.debug( "start CloseNativeConnection" );
			try
			{
				if ( con != null )
				{
					con.close();
				}
			}
			catch ( Error err )
			{
				logLogger.error( 
					err.getClass().getSimpleName() + " occured during close operation(" + err.getMessage() + ")" );
			}
			catch ( SQLException ex )
			{
				logLogger.error( 
					ex.getClass().getSimpleName() + " occured during close operation(" + ex.getMessage() + ")" );
			}
			finally
			{
				con = null;
				logLogger.debug( "end CloseNativeConnection" );
			}

		}
	}
}
