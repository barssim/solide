
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseClass;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Class for handle the tbCONTACT data.
 *
 * @author Ahmed El Qarfaoui 2016-06-15
 */
public class Contact
{
	// Declaration for class variables and constants.   
   
    public static final String FLD_CONTACTNO		= "CONTACTNO";
    public static final String FLD_CONTACTNAME		= "CONTACTNAME";
	public static final String FLD_Addresse			= "ADDRESSE";
	public static final String FLD_EMAIL	        = "EMAIL";
	public static final String FLD_TELFONNR	        = "TELEFONNR";
	public static final String FLD_CRUSERNO	        = "CRUSERNO";
	public static final String FLD_CRDTTM		    = "CRUSERDTTM";
	public static final String FLD_CHUSERNO		    = "CHUSERNO";
	public static final String FLD_CHDTTM		    = "CHUSERDTTM";
	
	private PreparedStatement pstInsertRecord				= null;
	private PreparedStatement pstUpdateRecord             = null;
	private PreparedStatement pstGetRecordByCONTACTName = null;
	
	/**
     * This method insert new contact record
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
	
	public int insertRecord( DatabaseClass clsDB, 
			                 String strCONTACTName,
			                 String strAddresse,
			                 String strEmail,
			                 String strTelefonNr,
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
					 .append( " tb_contact( " )
					 .append( " CONTACTNAME, " )
					 .append( " ADDRESSE, " )
					 .append( " EMAIL, " )
					 .append( " TELEFONNR, " )
					 .append( " CrUserNo, " )
					 .append( " CrUserDtTm, " )
					 .append( " ChUserNo, " )
					 .append( " ChUserDtTm )" )
					 .append( " VALUES(  ?, ?, ?, ?, ?,?,?,? ) " );
				{
					sbLog.append( strCONTACTName ).append( ", " )
					.append( strAddresse ).append( ", " )
					.append( strEmail ).append( ", " )
					.append( strTelefonNr ).append( ", " )
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
				
				pstInsertRecord.setString(		1, strCONTACTName );
				pstInsertRecord.setString(		2, strAddresse );
				pstInsertRecord.setString(		3, strEmail );
				pstInsertRecord.setString(		4, strTelefonNr );
				pstInsertRecord.setInt(			5, 1 );
				pstInsertRecord.setTimestamp(	6, new java.sql.Timestamp( dtCrDtTm.getTime() ) );
				pstInsertRecord.setInt(			7, 1 );
				pstInsertRecord.setTimestamp(	8, new java.sql.Timestamp( dtChDtTm.getTime() ) );
				// Execute query and return count of affected rows.
				return pstInsertRecord.executeUpdate();
	}
	
	/**
     * This update contact for a given sellername
     *
     * <br><br><b>author</b> Ahmed El Qarfaoui 2016-06-17
     *     
	 *
     * @return	The concerning ResultSet.
	 *
	 * @throws	SQLException
	 * @throws	ClassNotFoundException
	 * @throws	InstantiationException
	 * @throws	IllegalAccessException
	 */
	public int updateRecord( DatabaseClass clsDB, 
            SellerBean sellerBean)
           		 throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
{
// Declaration of method variables.
Logger logLogger	= Logger.getLogger( "GLSLogger" );
StringBuilder sbSQL	= new StringBuilder();
StringBuilder sbLog	= new StringBuilder();

// Create SQL statement.
sbSQL.append( " update " )
	 .append( " tb_contact " )
	 .append( " set  ( EMAIL ," )
	 .append( "  TELEFONNR, ChUserNo ,ChUserDtTm )  " )
	 .append( "  = (?, ?, ?, ?) " )	 
	 .append( " where CONTACTNO = ?  " );
// Log' statement and parameters.
if ( logLogger.isDebugEnabled() )
{
	sbLog.append( sellerBean.getEmailaddr() ).append( ", " )
	.append( sellerBean.getTelefonNr() ).append( ", " );
	logLogger.debug( "          SQL : " + sbSQL );
	logLogger.debug( "PARAM for SQL : " + sbLog );
}

// Prepare statement, if needed.
if( ! clsDB.isStatementPrepared( pstUpdateRecord ) )
{
	pstUpdateRecord = clsDB.getConnection().prepareStatement( sbSQL.toString() );
}

// Set statement parameters.


pstUpdateRecord.setString(		1, sellerBean.getEmailaddr() );
pstUpdateRecord.setString(		2, sellerBean.getTelefonNr() );
pstUpdateRecord.setInt(			3, 1 );
pstUpdateRecord.setTimestamp(	4, new java.sql.Timestamp( new Date().getTime() ) );
pstUpdateRecord.setInt(		5, sellerBean.getContactNo() );
// Execute query and return count of affected rows.
return pstUpdateRecord.executeUpdate();
}

	/**
     * This method returns a single record, matching a given CONTACTName.
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
    public ResultSet getRecord( DatabaseClass clsDB, String strCONTACTName )
		throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
		// Declaration of method variables.
		Logger logLogger	= Logger.getLogger( "GLSLogger" );
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append( " SELECT " )			 
			 .append( "CONTACTNO, " )
			 .append( "CONTACTNAME, " )
			 .append( "ADDRESSE, " )
			 .append( "EMAIL, " )
			 .append( "TELEFONNR, " )
			 .append( "CRUSERNO, " )
			 .append( "CRUSERDTTM, " )
			 .append( "CHUSERNO, " )
			 .append( "CHUSERDTTM" )
			 .append( " FROM " )
			 .append( " tb_contact " )
			 .append( " WHERE " )
			 .append( " CONTACTNAME = ? " );

		// Log' statement and parameter.
		if ( logLogger.isDebugEnabled() )
		{
			logLogger.debug( "          SQL : " + sbSQL );
			logLogger.debug( "PARAM for SQL : " + strCONTACTName + "." );
		}

		// Prepare statement, if needed.
		if( ! clsDB.isStatementPrepared( pstGetRecordByCONTACTName ) )
		{
			pstGetRecordByCONTACTName = clsDB.getConnection().prepareStatement( sbSQL.toString() );
		}

		// Set statement parameters.
		pstGetRecordByCONTACTName.setString( 1, strCONTACTName );

		// Execute query and return ResultSet.
		return pstGetRecordByCONTACTName.executeQuery();
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
			if( pstGetRecordByCONTACTName != null )
			{
				pstGetRecordByCONTACTName.close();
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