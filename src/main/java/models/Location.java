
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseClass;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Class for handle the tbLocation data.
 *
 * @author Ahmed El Qarfaoui 2016-06-15
 */
public class Location
{
	// Declaration for class variables and constants.   
   
    public static final String FLD_LOCATIONNO			= "LOCATIONNO";
	public static final String FLD_LOCATIONNAME			= "LOCATIONNAME";
	public static final String FLD_CRUSERNO	        = "CRUSERNO";
	public static final String FLD_CRDTTM		    = "CRUSERDTTM";
	public static final String FLD_CHUSERNO		    = "CHUSERNO";
	public static final String FLD_CHDTTM		    = "CHUSERDTTM";
	
	private PreparedStatement pstInsertRecord				= null;
	private PreparedStatement pstGetRecordByLocationName = null;
	public int insertRecord( DatabaseClass clsDB, 
			                 String strLocationName,
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
					 .append( " tb_location( " )
					 .append( " LOCATIONNAME, " )
					 .append( " CrUserNo, " )
					 .append( " CrUserDtTm, " )
					 .append( " ChUserNo, " )
					 .append( " ChUserDtTm )" )
					 .append( " VALUES(  ?, ?, ?, ?, ? ) " );
				// Log' statement and parameters.
				if ( logLogger.isDebugEnabled() )
				{
					sbLog.append( strLocationName ).append( ", " )
					     .append( String.valueOf( iCrUserNo ) ).append( ", " )
						 .append( String.valueOf( dtCrDtTm ) ).append( ", " )
						 .append( String.valueOf( iChUserNo ) ).append( ", " )
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
				
				pstInsertRecord.setString(		1, strLocationName );
				pstInsertRecord.setInt(			2, iCrUserNo );
				pstInsertRecord.setTimestamp(	3, new java.sql.Timestamp( dtCrDtTm.getTime() ) );
				pstInsertRecord.setInt(			4, iChUserNo );
				pstInsertRecord.setTimestamp(	5, new java.sql.Timestamp( dtChDtTm.getTime() ) );
				// Execute query and return count of affected rows.
				return pstInsertRecord.executeUpdate();
	}
	
	/**
     * This method returns a single record, matching a given locationName.
     *
     * <br><br><b>author</b> Ahmed El Qarfaoui 2016-06-15
     *     
	 *
     * @return	The concerning ResultSet.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
    public ResultSet getRecord( DatabaseClass clsDB, String strLocationName )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " SELECT " )			 
			 .append( "LOCATIONNO, " )
			 .append( "LOCATIONNAME, " )
			 .append( "CRUSERNO, " )
			 .append( "CRUSERDTTM, " )
			 .append( "CHUSERNO, " )
			 .append( "CHUSERDTTM" )
			 .append( " FROM " )
			 .append( " tb_location " )
			 .append( " WHERE " )
			 .append( " LOCATIONNAME = ? " );

		// Log' statement and parameter.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + strLocationName + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstGetRecordByLocationName ) )
		{
			pstGetRecordByLocationName = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		pstGetRecordByLocationName.setString( 1, strLocationName );

		// Execute query and return ResultSet.
		return pstGetRecordByLocationName.executeQuery();
    }
    /**
     * close possible open statements.
     *
     * <br><br><b>author</b>  Ahmed El Qarfaoui 2016-06-15
     *
     * @throws java.lang.Throwable
     */
    @Override
    protected void finalize() throws Throwable
    {
		try
		{
			if( pstInsertRecord != null )
			{
				pstInsertRecord.close();
			}
			if( pstGetRecordByLocationName != null )
			{
				pstGetRecordByLocationName.close();
			}
		}
		finally
		{
			super.finalize();
		}
    }
}