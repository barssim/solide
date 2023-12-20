package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseClass;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Class for handle the tbSELLER data.
 *
 * @author Ahmed El Qarfaoui 2016-05-20
 */
public class Seller
{
	// Declaration for class variables and constants.   
   
    public static final String FLD_SELLERNO			= "SELLERNO";
	public static final String FLD_SURNAME			= "SURNAME";
	public static final String FLD_SELLERNAME		= "SELLERNAME";
	public static final String FLD_PASSWORD		    = "PASSWORD";
	public static final String FLD_CONTACTNO		= "CONTACTNO";
	public static final String FLD_LOCATIONNO		= "LOCATIONNO";
	public static final String FLD_CRUSERNO	        = "CRUSERNO";
	public static final String FLD_CRDTTM		    = "CRUSERDTTM";
	public static final String FLD_CHUSERNO		    = "CHUSERNO";
	public static final String FLD_CHDTTM		    = "CHUSERDTTM";
	

	/** Constant for search with altering parameters. */
	public static final String NO_SEARCH_PARAMETER	= "__NO__SEARCH__PARAMETER__";	

	private	PreparedStatement pstActiveSELLER					= null;
	private	PreparedStatement pstGetRecordBySELLERNo			= null;
	private PreparedStatement pstGetRecordByLoginName		= null;
	private PreparedStatement pstGetRecordByLoginNameLike   = null;
	private PreparedStatement pstInsertRecord				= null;
	private PreparedStatement pstUpdateRecord               = null;
	private PreparedStatement pstDeleteRecordBySELLERNo		= null;
	private PreparedStatement pstGetRecordBySELLERName      = null;
	private PreparedStatement pstDeleteRecordByLoginName	= null;
	private PreparedStatement pstSearchSELLERs 	            = null;
	private PreparedStatement pstUpdateSELLERSPassword       = null;
	private PreparedStatement pstUpdateSELLER               = null;

	/**
     * returns a resultset for the query using the loginname.
	 *
	 * -- Please use getRecord instead. --
     * 
     * <br><br><b>author</b>  Ahmed El Qarfaoui 2016-05-20
     * 
     * @param	clsDB			the database class
     * @param	strLoginName	the login name where we want to have the result set from
     * @return	the resultset of the query
     * @throws java.sql.SQLException
     */
    public ResultSet getActiveSELLER( DatabaseClass clsDB, String strLoginName) throws SQLException
    {
	    ResultSet   resActiveSELLER   =   null;
		Logger      logLogger = Logger.getLogger( "GLSLogger" );

        try
        {
			String strSQL = "SELECT LOGIN_NAME, SELLER_PASSWORD, PASS_EXP_DT, PASS_EXP_DAYS, SELLER_NO, SELLER_STATUS_NO, GIVEN_NAME, SURNAME " +
							"FROM TB_SELLER WHERE LOGIN_NAME = ? ";
			if ( logLogger.isDebugEnabled() )
			{
				logLogger.debug( "          SQL : " + strSQL );
				logLogger.debug( "PARAM for SQL : " + strLoginName );
			}

			if( ! clsDB.isStatementPrepared( pstActiveSELLER ) )
			{
				pstActiveSELLER = clsDB.getConnection().prepareStatement( strSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			}

			pstActiveSELLER.setString( 1, strLoginName );
            resActiveSELLER = pstActiveSELLER.executeQuery();
            if ( logLogger.isDebugEnabled() )
            {
                logLogger.debug( "Query open " );
            }
            return ( resActiveSELLER );
        }
        catch ( Exception ex )
        {
            logLogger.error( ex.getClass().getName()  + " " + ex.getMessage() );
            return ( null );
        }
    }

	/**
	 * This method inserts a single record into tbSELLER.
	 *
	 * <br><br><b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @param	clsDB					Database connection
	 
	 *
	 * @return	The count of affected rows.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
	public int insertRecord( DatabaseClass clsDB,
							 String strSurName,
							 String strSellerName,
							 String strSELLERPassword,
							 int iContactNo,
							 int iLocationNo,
							 int iCrUserNo,
							 Date dtCrDtTm,
							 int iChUserNo,
							 Date dtChDtTm )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL	= new StringBuilder();
		StringBuilder sbLog	= new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " INSERT INTO " )
			 .append( " tb_SELLER( " )
			 .append( " SurName, " )
			 .append( " SellerName, " )
			 .append( " Password, " )
			 .append( " ContactNo, " )
			 .append( " LocationNo, " )
			 .append( " CrUserNo, " )
			 .append( " CrUserDtTm, " )
			 .append( " ChUserNo, " )
			 .append( " ChUserDtTm )" )
			 .append( " VALUES(  ?, ?, ?, ?, ?, ?, ?, ?, ? ) " );

		// Log' statement and parameters.
		if ( logLogger.isDebugEnabled() )
		{
			sbLog.append( strSurName ).append( ", " )
				 .append( strSellerName ).append( ", " )
				 .append( strSELLERPassword ).append( ", " )
				 .append( strSELLERPassword ).append( ", " )
				 .append( iContactNo ).append( ", " )
				 .append( iLocationNo ).append( ", " )
				 .append( String.valueOf( dtCrDtTm ) ).append( ", " )
				 .append( String.valueOf( dtChDtTm ) ).append( "." );

			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + sbLog );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstInsertRecord ) )
		{
			pstInsertRecord = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		
		pstInsertRecord.setString(		1, strSurName );
		pstInsertRecord.setString(		2, strSellerName );
		pstInsertRecord.setString(		3, strSELLERPassword );
		pstInsertRecord.setInt(			4, iContactNo );
		pstInsertRecord.setInt(			5, iLocationNo );
		pstInsertRecord.setInt(			6, 1 );
		pstInsertRecord.setTimestamp(	7, new java.sql.Timestamp( dtCrDtTm.getTime() ) );
		pstInsertRecord.setInt(			8, 1 );
		pstInsertRecord.setTimestamp(	9, new java.sql.Timestamp( dtChDtTm.getTime() ) );

		// Execute query and return count of affected rows.
		return pstInsertRecord.executeUpdate();
	}

	/**
	 * This method updates a single record into tbSELLER.
	 *
	 * <br><br><b>author</b> Ahmed El Qarfaoui 2016-06-17
	 *
	 * @param	clsDB					Database connection
	 
	 *
	 * @return	The count of affected rows.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
	public int updateRecord( DatabaseClass clsDB,
							 String strSellerName,
							 int iContactNo )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL	= new StringBuilder();
		StringBuilder sbLog	= new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " update " )
			 .append( " tb_SELLER( " )			 
			 .append( " set ContactNo = ? " )			 
			 .append( " Where SellerName = ?  " );

		// Log' statement and parameters.
		if ( logLogger.isDebugEnabled() )
		{
			sbLog.append( strSellerName ).append( ", " )
				 .append( iContactNo );

			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + sbLog );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstUpdateRecord ) )
		{
			pstUpdateRecord = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		
		pstUpdateRecord.setString(		1, strSellerName );
		pstUpdateRecord.setInt(		2, iContactNo );

		// Execute query and return count of affected rows.
		return pstUpdateRecord.executeUpdate();
	}

	
	/**
	 * This method deletes a single record, using the unique SELLERNo.
	 *
	 * <br><br><b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @param	clsDB					Database connection
	 * @param	iSELLERNo					SELLERNo
	 *
	 * @return	The count of affected rows.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
	public int deleteRecord( DatabaseClass clsDB,
							 int iSELLERNo )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );

		// Create SQL statement.
		String strSQL = " DELETE FROM tb_SELLER WHERE SELLER_No = ? ";

		// Log' statement and parameters.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + strSQL );
			logLogger.debug( "PARAM for SQL : " + iSELLERNo + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstDeleteRecordBySELLERNo ) )
		{
			pstDeleteRecordBySELLERNo = clsDB.getConnection().prepareStatement( strSQL );
		}

		// Set statement parameters.
		pstDeleteRecordBySELLERNo.setInt( 1, iSELLERNo );

		// Execute query and return count of affected rows.
		return pstDeleteRecordBySELLERNo.executeUpdate();
	}

	/**
	 * This method deletes a single record, using the unique LoginName.
	 *
	 * <br><br><b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @param	clsDB					Database connection
	 * @param	strLoginName			LoginName
	 *
	 * @return	The count of affected rows.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
	public int deleteRecord( DatabaseClass clsDB,
							 String strLoginName )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );

		// Create SQL statement.
		String strSQL = " DELETE FROM tb_SELLER WHERE Login_Name = ? ";

		// Log' statement and parameters.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + strSQL );
			logLogger.debug( "PARAM for SQL : " + strLoginName + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstDeleteRecordByLoginName ) )
		{
			pstDeleteRecordByLoginName = clsDB.getConnection().prepareStatement( strSQL );
		}

		// Set statement parameters.
		pstDeleteRecordByLoginName.setString( 1, strLoginName );

		// Execute query and return count of affected rows.
		return pstDeleteRecordByLoginName.executeUpdate();
	}

	
    /**
     * This method returns a single record, matching a given SELLERName.
     *
     * <br><br><b>author</b> Ahmed El Qarfaoui 2016-06-27
     *
     * @param	clsDB			The database connection.
     * @param	iSELLERNo			A given SELLERNo.
	 *
     * @return	The concerning ResultSet.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
     */
    public ResultSet getRecordByName( DatabaseClass clsDB, String strSELLERName )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " SELECT " )
		     .append( "SELLERNO, " )
			 .append( "SURNAME, " )
			 .append( "SELLERNAME, " )
			 .append( "PASSWORD, " )
			 .append( "CONTACTNO, " )
			 .append( "LOCATIONNO, " )
			 .append( "CRUSERNO, " )
			 .append( "CRUSERDTTM, " )
			 .append( "CHUSERNO, " )
			 .append( "CHUSERDTTM" )
			 .append( " FROM " )
			 .append( " tb_SELLER " )
			 .append( " WHERE " )
			 .append( " SELLERNAME = ? " );

		// Log' statement and parameter.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + strSELLERName + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstGetRecordBySELLERName ) )
		{
			pstGetRecordBySELLERName = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		pstGetRecordBySELLERName.setString( 1, strSELLERName );

		// Execute query and return ResultSet.
		return pstGetRecordBySELLERName.executeQuery();
    }

	/**
     * This method returns a single record, matching a given SELLERNo.
     *
     * <br><br><b>author</b> Ahmed El Qarfaoui 2016-05-20
     *
     * @param	strLoginName	A given LoginName.
	 *
     * @return	The concerning ResultSet.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
    public ResultSet getRecord( DatabaseClass clsDB, String strSellerName )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " SELECT " )			 
			 .append( "SELLERNO, " )
			 .append( "SURNAME, " )
			 .append( "SELLERNAME, " )
			 .append( "PASSWORD, " )
			 .append( "CONTACTNO, " )
			 .append( "LOCATIONNO, " )
			 .append( "CRUSERNO, " )
			 .append( "CRUSERDTTM, " )
			 .append( "CHUSERNO, " )
			 .append( "CHUSERDTTM" )
			 .append( " FROM " )
			 .append( " tb_SELLER " )
			 .append( " WHERE " )
			 .append( " SELLERNAME = ? " );

		// Log' statement and parameter.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + strSellerName + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstGetRecordByLoginName ) )
		{
			pstGetRecordByLoginName = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		pstGetRecordByLoginName.setString( 1, strSellerName );

		// Execute query and return ResultSet.
		return pstGetRecordByLoginName.executeQuery();
    }

	/**
     * get record where LoginName is like specified value
     *
     * <br><br><b>author</b> Ahmed El Qarfaoui 2016-05-20
     *
     * @param	strLoginName   LoginName value containing wildcards
	 *
     * @return	The concerning ResultSet.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
    public ResultSet getRecordByLoginNameLike( DatabaseClass clsDB, String strLoginNameLike )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " SELECT " )
			 .append( "GIVEN_NAME, " )
			 .append( "SURNAME, " )
			 .append( "LOGI_NNAME, " )
			 .append( "SELLER_NO, " )
			 .append( "SELLER_PASSWORD, " )
			 .append( "EMAIL_ADDR, " )
			 .append( "SELLERS_TATUS_NO " )
			 .append( " FROM " )
			 .append( " tb_SELLER " )
			 .append( " WHERE " )
			 .append( " Login_Name LIKE ? " );

		// Log' statement and parameter.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + strLoginNameLike + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstGetRecordByLoginNameLike ) )
		{
			pstGetRecordByLoginNameLike = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		pstGetRecordByLoginNameLike.setString( 1, strLoginNameLike );

		// Execute query and return ResultSet.
		return pstGetRecordByLoginNameLike.executeQuery();
    }

	/**
	 * search for SELLERs
	 * all search parameters areoptional
	 *
	 * <br><br><b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @param clsDB
	 * @param iSELLERNo       SELLERno or a value less than 0 (if not included in this search)
	 * @param strGivenName  givenname or NO_SEARCH_PARAMETER (if not included in this search)
	 * @param strSurName    surname or NO_SEARCH_PARAMETER (if not included in this search)
	 * @param strLoginName  loginname or NO_SEARCH_PARAMETER (if not included in this search)
	 * @param strEmailAddr  emailaddr or NO_SEARCH_PARAMETER (if not included in this search)
	 * @param iNRows        maximum number of rows, zero means there is no limit
	 * @return ResultSet
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet searchSELLERs( DatabaseClass clsDB,
								  int iSELLERNo,
								  String  strGivenName,
								  String  strSurName,
								  String  strLoginName,
								  String  strEmailAddr,
								  int iNRows )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " SELECT " )
			 .append( " SELLERNo " )
			 .append( ", Given_Name " )
			 .append( ", SurName" )
			 .append( ", Login_Name" )
			 .append( ", SELLER_Password" )
			 .append( ", EMail_Addr" )
			 .append( ", SELLER_Type_No" )
			 .append( ", SELLER_Status_No" )
			 .append( ", Pass_Exp_Dt" )
			 .append( ", Pass_Exp_Days" )
			 .append( ", Prev_Passwords" )
			 .append( ", Cr_SELLER_No" )
			 .append( ", Cr_Dt_Tm" )
			 .append( ", Ch_Use_rNo" )
			 .append( ", Ch_Dt_Tm" )
			 .append( " FROM tb_SELLER " )
             .append( " WHERE " )
	         .append( " (SELLER_No = ? OR ? < 0)" )
			 .append( " AND (Given_Name = ? OR ? = '" + NO_SEARCH_PARAMETER + "')" )
			 .append( " AND (SurName   = ? OR ? = '" + NO_SEARCH_PARAMETER + "')" )
			 .append( " AND (Login_Name = ? OR ? = '" + NO_SEARCH_PARAMETER + "')" )
			 .append( " AND (EMail_Addr = ? OR ? = '" + NO_SEARCH_PARAMETER + "')" );

		// Log' statement and parameter.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + sbSQL );
			StringBuilder sbLog = new StringBuilder();
			sbLog.append( iSELLERNo ).append( "," )
				 .append( iSELLERNo ).append( "," )
				 .append( strGivenName ).append( "," )
				 .append( strGivenName ).append( "," )
				 .append( strSurName ).append( "," )
				 .append( strSurName ).append( "," )
				 .append( strLoginName ).append( "," )
				 .append( strLoginName ).append( "," )
                 .append( strEmailAddr ).append( "," )
                 .append( strEmailAddr ).append( "." );

			logLogger.debug( "PARAM for SQL : " + sbLog );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstSearchSELLERs ) )
		{
			pstSearchSELLERs = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		pstSearchSELLERs.setInt(    1, iSELLERNo );
		pstSearchSELLERs.setInt(    2, iSELLERNo );
		pstSearchSELLERs.setString( 3, strGivenName );
		pstSearchSELLERs.setString( 4, strGivenName );
		pstSearchSELLERs.setString( 5, strSurName );
		pstSearchSELLERs.setString( 6, strSurName );
		pstSearchSELLERs.setString( 7, strLoginName );
		pstSearchSELLERs.setString( 8, strLoginName );
		pstSearchSELLERs.setString( 9, strEmailAddr );
		pstSearchSELLERs.setString( 10, strEmailAddr );

		// maximum number of rows
		pstSearchSELLERs.setMaxRows( iNRows );
		// Execute query and return ResultSet.
		return pstSearchSELLERs.executeQuery();
    }

	/**
	 * This method updates the password of a SELLER.
	 * <br><br><b>author</b> Ahmed El Qarfaoui 2016-06-17
	 * @param clsDB
	 
	 */
	public int updateSELLERSPassword( DatabaseClass clsDB,
								   String strSELLERName,
								   String strSELLERPassowd)
		throws SQLException,
			   ClassNotFoundException,
			   InstantiationException,
			   IllegalAccessException
	{
		Logger logLogger = Logger.getLogger( "GLSLogger" );

		String strSQL = "UPDATE tb_SELLER SET PASSWORD = ?  WHERE SELLERNAME = ? ";		

		// Log SQL statement and parameter
		if ( logLogger.isDebugEnabled() )
		{
			StringBuilder sbParam = new StringBuilder();
			sbParam.append( "PARAM FOR SQL: " )
			        .append( strSELLERName ).append( ", ")
				    .append( strSELLERPassowd ) ;

			logLogger.debug( "SQL          : ".concat( strSQL ) );
			logLogger.debug( sbParam.toString() );
		}

		// Prepare Statement if needed
		if( ! clsDB.isStatementPrepared( pstUpdateSELLERSPassword ) )
		{
			pstUpdateSELLERSPassword = clsDB.getConnection().prepareStatement( strSQL );
		}

		// Set parameter
		pstUpdateSELLERSPassword.setString(			1, strSELLERPassowd );
		pstUpdateSELLERSPassword.setString(			2, strSELLERName );
		
		// Execute update and return the number of affected rows
		return pstUpdateSELLERSPassword.executeUpdate();
	}

	/**
	 * update a record
	 * @author Ahmed El Qarfaoui 2016-05-20
	 * @param clsDB
	 * @param iSELLERNo
	 * @param strGivenName
	 * @param strSurName
	 * @param strLoginName
	 * @param strSELLERPassword
	 * @param strEmailAddr
	 * @param iSELLERStatusNo
	 * @param dtPassExpDt
	 * @param strPrevPasswords
	 * @param iChSELLERNo
	 * @param dtChDtTm
	 * @return Count of affected rows
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int updateSELLER( DatabaseClass clsDB,
		                   int iSELLERNo,
						   String strGivenName,
						   String strSurName,
						   String strLoginName,
						   String strSELLERPassword,
						   String strEmailAddr,
						   int iSELLERStatusNo,
						   Date dtPassExpDt,
						   String strPrevPasswords,
						   int iChSELLERNo,
						   Date dtChDtTm )
		throws SQLException,
			   ClassNotFoundException,
			   InstantiationException,
			   IllegalAccessException
	{
		Logger logLogger = Logger.getLogger( "GLSLogger" );

		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append( "UPDATE tb_SELLER SET ")
			 .append( "Given_Name = ?," )
			 .append( "SurName = ?," )
			 .append( "Login_Name = ?," )
			 .append( "SELLER_Password = ?," )
			 .append( "Email_Addr = ?," )
			 .append( "SELLER_Status_No = ?," )
			 .append( "Pass_Exp_Dt = ?," )
			 .append( "Prev_Passwords = ?," )
			 .append( "Ch_SELLER_No = ?," )
			 .append( "Ch_Dt_Tm = ? " )
			 .append( "WHERE SELLER_No = ?" );

		// Log SQL statement and parameter
		if ( logLogger.isDebugEnabled() )
		{
			StringBuilder sbParam = new StringBuilder();
			sbParam.append( "PARAM FOR SQL: " )
				   .append( strGivenName ).append( ",")
				   .append( strSurName ).append( ",")
				   .append( strLoginName ).append( ",")
				   .append( strSELLERPassword ).append(",")
				   .append( strEmailAddr ).append(",")
				   .append( iSELLERStatusNo ).append(",")
				   .append( dtPassExpDt ).append(",")
				   .append( strPrevPasswords ).append(",")
				   .append( iChSELLERNo ).append(",")
				   .append( dtChDtTm ).append(",")
				   .append( iSELLERNo );


			logLogger.debug( "SQL          : ".concat( sbSQL.toString() ) );
			logLogger.debug( sbParam.toString() );
		}

		// Prepare Statement if needed
		if( ! clsDB.isStatementPrepared( pstUpdateSELLER ) )
		{
			pstUpdateSELLER = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set parameter
		pstUpdateSELLER.setString(     1, strGivenName );
		pstUpdateSELLER.setString(     2, strSurName );
		pstUpdateSELLER.setString(     3, strLoginName );
		pstUpdateSELLER.setString(     4, strSELLERPassword );
		
		if( strEmailAddr != null )
		{
			pstUpdateSELLER.setString( 5, strEmailAddr );
		}
		else
		{
			pstUpdateSELLER.setNull( 5, java.sql.Types.VARCHAR );
		}
		
		pstUpdateSELLER.setInt(        6, iSELLERStatusNo );
		pstUpdateSELLER.setDate(       7, new java.sql.Date( dtPassExpDt.getTime() ) );
		pstUpdateSELLER.setString(     8, strPrevPasswords );
		pstUpdateSELLER.setInt(        9, iChSELLERNo );
		pstUpdateSELLER.setTimestamp( 10, new Timestamp( dtChDtTm.getTime() ) );
		pstUpdateSELLER.setInt(       11, iSELLERNo );

		// Execute update and return the number of affected rows
		return pstUpdateSELLER.executeUpdate();
	}
   /**
     * close possible open statements.
     *
     * <br><br><b>author</b>  Ahmed El Qarfaoui 2016-05-20
     *
     * @throws java.lang.Throwable
     */
    @Override
    protected void finalize() throws Throwable
    {
		try
		{
			if( pstActiveSELLER != null )
			{
				pstActiveSELLER.close();
			}
			if( pstGetRecordBySELLERNo != null )
			{
				pstGetRecordBySELLERNo.close();
			}
			if( pstGetRecordByLoginName != null )
			{
				pstGetRecordByLoginName.close();
			}
			if( pstSearchSELLERs != null )
			{
				pstSearchSELLERs.close();
			}
			if( pstUpdateSELLERSPassword != null )
			{
				pstUpdateSELLERSPassword.close();
			}
			if( pstInsertRecord != null )
			{
				pstInsertRecord.close();
			}
			
			if( pstDeleteRecordBySELLERNo != null )
			{
				 pstDeleteRecordBySELLERNo.close();
			}
			if( pstGetRecordBySELLERName != null )
			{
				pstGetRecordBySELLERName.close();
			}
			if( pstDeleteRecordByLoginName != null )
			{
				pstDeleteRecordByLoginName.close();
			}
			if( pstUpdateSELLER != null )
			{
				pstUpdateSELLER.close();
			}
			if( pstGetRecordByLoginNameLike != null )
			{
				pstGetRecordByLoginNameLike.close();
			}
			if( pstUpdateRecord != null )
			{
				pstUpdateRecord.close();
			}
			
		}
		finally
		{
			super.finalize();
		}
    }
}