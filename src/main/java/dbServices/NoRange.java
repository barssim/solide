package dbServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import org.apache.log4j.Logger;

import models.UserBean;
import utils.DatabaseClass;
import utils.ServerConfiguration;
import utils.SolideConstants;
import utils.SolideLogCreater;

public class NoRange {

	public static final String FLD_NOOBJ = "NOOBJ";
	public static final String FLD_RANGEFROM = "RANGEFROM";
	public static final String FLD_RANGETO = "RANGETO";
	public static final String FLD_NEXTNO = "NEXTNO";
	public static final String FLD_CRDTTM = "CRUSERDTTM";
	public static final String FLD_CHUSERNO = "CHUSERNO";
	public static final String FLD_CHDTTM = "CHUSERDTTM";
	public static final String FLD_LOCNO = "LOCNO";
	
	public static final int RANGEFROM = 900000001;
	public static final int RANGETO = 999999999;
	
	DatabaseClass clsDB;
	private PreparedStatement pstGetRange = null;
	private PreparedStatement pstUpdateRange = null;
	private PreparedStatement pstInsertRecord = null;
	
	
	private static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	public NoRange() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
//		clsConfig.confige();
		clsDB = clsConfig.clsDB;
		// new SolideLogCreater(clsConfig);
	}
	
	public ResultSet getRange( String noObj)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append( FLD_NEXTNO ).append( "," ).append( FLD_RANGEFROM ).append(",")
		.append(FLD_RANGETO).append(" FROM ").append(" tb_norange ").append(" WHERE ")
		.append( FLD_NOOBJ + " = ? ");
		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {			
			logLogger.debug("          SQL : " + sbSQL);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRange)) {
			pstGetRange = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstGetRange.setString(1, noObj);
		
		// Execute query and return count of affected rows.
		return pstGetRange.executeQuery();
	}
	
	public int updateRange(String noObj, int nextNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" UPDATE ").append(" tb_norange ").append(" SET ").append(FLD_NEXTNO + " = ? ").append(" WHERE ").append( FLD_NOOBJ + " = ? ");
		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {			
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + noObj + "," + nextNo + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstUpdateRange)) {
			pstUpdateRange = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstUpdateRange.setInt(1, nextNo);
		pstUpdateRange.setString(2, noObj);
		
		// Execute query and return count of affected rows.
		return pstUpdateRange.executeUpdate();
	}
	
	/**
	 * 
	 * @param strNoObj
	 * @param iLocationNo
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int insertRecord(String strNoObj, int iLocNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" INSERT INTO ").append(" tb_norange( ").append(" NoObj, ").append(" RangeFrom, ")
				.append(" RangeTo, ").append(" NextNo, ").append(" CrUserNo, ")
				.append(" ChUserNo, ").append(" ChUserDtTm )").append(" LocNo, ").append("COLUMN1")
				.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {
			sbLog.append(strNoObj).append(", ").append(iLocNo).append(".");

			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstInsertRecord)) {
			pstInsertRecord = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstInsertRecord.setString(1, strNoObj);
		pstInsertRecord.setInt(2, RANGEFROM);
		pstInsertRecord.setInt(3, RANGETO);
		pstInsertRecord.setInt(4, RANGEFROM);
		pstInsertRecord.setInt(5, 1);
		pstInsertRecord.setInt(6, 1);
		pstInsertRecord.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
		pstInsertRecord.setInt(8, iLocNo);
		pstInsertRecord.setInt(9, 2);

		// Execute query and return count of affected rows.
		return pstInsertRecord.executeUpdate();
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			if (pstGetRange != null) {
				pstGetRange.close();
			}
			if (pstInsertRecord != null) {
				pstInsertRecord.close();
			}
			if (pstUpdateRange != null) {
				pstUpdateRange.close();
			}

		} finally {
			super.finalize();
		}
	}

}
