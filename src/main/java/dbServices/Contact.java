
package dbServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import org.apache.log4j.Logger;

import utils.DatabaseClass;
import models.ContactBean;
import models.UserBean;
import utils.ServerConfiguration;

/**
 * Class for handle the tbCONTACT data.
 *
 * @author Ahmed El Qarfaoui 2016-06-15
 */
public class Contact {
	// Declaration for class variables and constants.

	public static final String FLD_CONTACTNO = "CONTACTNO";
	public static final String FLD_CONTACTNAME = "CONTACTNAME";
	public static final String FLD_Addresse = "ADDRESSE";
	public static final String FLD_EMAIL = "EMAIL";
	public static final String FLD_TELFONNR = "TELEFONNR";
	public static final String FLD_CRUSERNO = "CRUSERNO";
	public static final String FLD_CRDTTM = "CRUSERDTTM";
	public static final String FLD_CHUSERNO = "CHUSERNO";
	public static final String FLD_CHDTTM = "CHUSERDTTM";

	private PreparedStatement pstInsertRecord = null;
	private PreparedStatement pstUpdateRecord = null;
	private PreparedStatement pstGetRecordByCONTACTName = null;
	DatabaseClass clsDB;
	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	
	public Contact() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		clsConfig.confige();
		clsDB = clsConfig.clsDB;
	}

	/**
	 * This method insert new contact record
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-06-15
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */

	public int insertRecord(DatabaseClass clsDB, ContactBean contactBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" INSERT INTO ").append(" tb_contact( ").append(" CONTACTNAME, ").append(" ADDRESSE, ")
				.append(" EMAIL, ").append(" TELEFONNR, ").append(" CrUserNo, ").append(" CrUserDtTm, ")
				.append(" ChUserNo, ").append(" ChUserDtTm )").append(" VALUES(  ?, ?, ?, ?, ?,?,?,? ) ");
		{
			sbLog.append(contactBean.getContactName()).append(", ").append(contactBean.getAddresse()).append(", ")
					.append(contactBean.getEmail()).append(", ").append(contactBean.getMobile()).append(", ")
					.append(String.valueOf(new Date().getTime())).append(", ")
					.append(String.valueOf(new Date().getTime())).append(".");
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstInsertRecord)) {
			pstInsertRecord = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstInsertRecord.setString(1, contactBean.getContactName());
		pstInsertRecord.setString(2, contactBean.getAddresse());
		pstInsertRecord.setString(3, contactBean.getEmail());
		pstInsertRecord.setString(4, contactBean.getMobile());
		pstInsertRecord.setInt(5, 1);
		pstInsertRecord.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
		pstInsertRecord.setInt(7, 1);
		pstInsertRecord.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
		// Execute query and return count of affected rows.
		return pstInsertRecord.executeUpdate();
	}

	/**
	 * This update contact for a given sellername
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-06-17
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int updateRecord(ContactBean contactBean, UserBean userBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();
		String userName = "cont_"+userBean.getUserName();

		// Create SQL statement.
		sbSQL.append(" update ").append(" tb_contact ").append(" set  EMAIL = ? ,")
				.append("  TELEFONNR = ? ,").append(" ChUserNo = ? ,").append(" ChUserDttm = ? " )
				.append(" where CONTACTNAME = ?  ");
		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {
			sbLog.append(contactBean.getEmail()).append(", ").append(contactBean.getMobile()).append(", ")
					.append(userName).append(".");
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstUpdateRecord)) {
			pstUpdateRecord = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstUpdateRecord.setString(1, contactBean.getEmail());
		pstUpdateRecord.setString(2, contactBean.getMobile());
		pstUpdateRecord.setInt(3, 1);
		pstUpdateRecord.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
		pstUpdateRecord.setString(5, "cont_"+userBean.getUserName());
		// Execute query and return count of affected rows.
		return pstUpdateRecord.executeUpdate();
	}

	/**
	 * This method returns a single record, matching a given CONTACTName.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-06-15
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getRecord(DatabaseClass clsDB, ContactBean contactBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append("CONTACTNO, ").append("CONTACTNAME, ").append("ADDRESSE, ").append("EMAIL, ")
				.append("TELEFONNR, ").append("CRUSERNO, ").append("CRUSERDTTM, ").append("CHUSERNO, ")
				.append("CHUSERDTTM").append(" FROM ").append(" tb_contact ").append(" WHERE ")
				.append(" CONTACTNAME = ? ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + contactBean.getContactName() + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByCONTACTName)) {
			pstGetRecordByCONTACTName = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.
		pstGetRecordByCONTACTName.setString(1, contactBean.getContactName());

		// Execute query and return ResultSet.
		return pstGetRecordByCONTACTName.executeQuery();
	}

	/**
	 * This method returns a single record, matching a given UserName.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-06-15
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getRecordByUserName(DatabaseClass clsDB, UserBean userBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append("CONTACTNO, ").append("CONTACTNAME, ").append("ADDRESSE, ").append("EMAIL, ")
				.append("TELEFONNR, ").append("CRUSERNO, ").append("CRUSERDTTM, ").append("CHUSERNO, ")
				.append("CHUSERDTTM").append(" FROM ").append(" tb_contact, tb_user ").append(" WHERE ")
				.append(" tb_contact.CONTACTNO = tb_user.CONTACTNO ").append(" AND tb_user.USERNAME = ? ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + userBean.getUserName() + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByCONTACTName)) {
			pstGetRecordByCONTACTName = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.
		pstGetRecordByCONTACTName.setString(1, userBean.getUserName());

		// Execute query and return ResultSet.
		return pstGetRecordByCONTACTName.executeQuery();
	}

	/**
	 * close possible open statements.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-06-15
	 *
	 * @throws java.lang.Throwable
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			if (pstInsertRecord != null) {
				pstInsertRecord.close();
			}
			if (pstGetRecordByCONTACTName != null) {
				pstGetRecordByCONTACTName.close();
			}
			if (pstUpdateRecord != null) {
				pstUpdateRecord.close();
			}

		} finally {
			super.finalize();
		}
	}
}