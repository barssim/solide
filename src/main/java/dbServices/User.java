package dbServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseClass;
import models.UserBean;
import utils.ServerConfiguration;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import org.apache.log4j.Logger;

/**
 * Class for handle the tbUSER data.
 *
 * @author Ahmed El Qarfaoui 2016-05-20
 */
public class User {
	// Declaration for class variables and constants.

	public static final String FLD_USERNO = "USERNO";
	public static final String FLD_SURNAME = "SURNAME";
	public static final String FLD_USERNAME = "USERNAME";
	public static final String FLD_PASSWORD = "PASSWORD";
	public static final String FLD_CONTACTNO = "CONTACTNO";
	public static final String FLD_LOCATIONNO = "LOCATIONNO";
	public static final String FLD_CRUSERNO = "CRUSERNO";
	public static final String FLD_CRDTTM = "CRUSERDTTM";
	public static final String FLD_CHUSERNO = "CHUSERNO";
	public static final String FLD_CHDTTM = "CHUSERDTTM";
	public static final String FLD_ROLE = "ROLE";

	/** Constant for search with altering parameters. */
	public static final String NO_SEARCH_PARAMETER = "__NO__SEARCH__PARAMETER__";
	DatabaseClass clsDB;
	private static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	private PreparedStatement pstActiveUSER = null;
	private PreparedStatement pstGetRecordByUSERNo = null;
	private PreparedStatement pstGetRecordByLoginName = null;
	private PreparedStatement pstGetRecordByLoginNameLike = null;
	private PreparedStatement pstInsertRecord = null;
	private PreparedStatement pstUpdateRecord = null;
	private PreparedStatement pstDeleteRecordByUSERNo = null;
	private PreparedStatement pstGetRecordByUSERName = null;
	private PreparedStatement pstDeleteRecordByLoginName = null;
	private PreparedStatement pstSearchUSERs = null;
	private PreparedStatement pstUpdateUSERPassword = null;
	private PreparedStatement pstUpdateUSER = null;

	public User() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		clsConfig.confige();
		clsDB = clsConfig.clsDB;
	}

	/**
	 * This method inserts a single record into tbUSER.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @param clsDB
	 *            Database connection
	 *
	 * 
	 * @return The count of affected rows.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int insertRecord(UserBean userBean, int iLocationNo, int iContactNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" INSERT INTO ").append(" tb_user( ").append(" SurName, ").append(" USERName, ")
				.append(" Password, ").append(" ContactNo, ").append(" LocationNo, ").append(" CrUserNo, ")
				.append(" CrUserDtTm, ").append(" ChUserNo, ").append(" ChUserDtTm )")
				.append(" VALUES(  ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");

		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {
			sbLog.append(userBean.getSurname()).append(", ").append(userBean.getUserName()).append(", ")
					.append(userBean.getUserpassword()).append(", ").append(iContactNo).append(", ").append(iLocationNo)
					.append(", ").append(String.valueOf(new Date())).append(", ").append(String.valueOf(new Date()))
					.append(".");

			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstInsertRecord)) {
			pstInsertRecord = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstInsertRecord.setString(1, userBean.getSurname());
		pstInsertRecord.setString(2, userBean.getUserName());
		pstInsertRecord.setString(3, userBean.getUserpassword());
		pstInsertRecord.setInt(4, iContactNo);
		pstInsertRecord.setInt(5, iLocationNo);
		pstInsertRecord.setInt(6, 1);
		pstInsertRecord.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
		pstInsertRecord.setInt(8, 1);
		pstInsertRecord.setTimestamp(9, new java.sql.Timestamp(new Date().getTime()));

		// Execute query and return count of affected rows.
		return pstInsertRecord.executeUpdate();
	}

	/**
	 * This method updates a single record into tbUSER.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-06-17
	 *
	 * @param clsDB
	 *            Database connection
	 *
	 * 
	 * @return The count of affected rows.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int updateUserPassword( UserBean userBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" update ").append(" tb_user ").append(" set Password = ? ").append(" Where USERNAME = ?  ");

		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {
			sbLog.append(userBean.getUserpassword()).append(",")
			.append(userBean.getUserName());

			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstUpdateRecord)) {
			pstUpdateRecord = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstUpdateRecord.setString(1, userBean.getUserpassword());
		pstUpdateRecord.setString(2, userBean.getUserName());

		// Execute query and return count of affected rows.
		return pstUpdateRecord.executeUpdate();
	}

	/**
	 * This method returns a single record, matching a given USERNo.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @param strLoginName
	 *            A given LoginName.
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getRecord(UserBean userBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ")
		.append(FLD_USERNO ).append(",")
		.append(FLD_SURNAME ).append(",")
		.append(FLD_USERNAME ).append(",")
		.append(FLD_PASSWORD ).append(",")
				.append(FLD_CONTACTNO ).append(",")
				.append(FLD_LOCATIONNO ).append(",")
				.append(FLD_ROLE ).append(",")
				.append(FLD_CRUSERNO ).append(",")
				.append(FLD_CRDTTM ).append(",")
				.append(FLD_CHUSERNO ).append(",")
				.append(FLD_CHDTTM )
				.append(" FROM ")
				.append(" tb_user ")
				.append(" WHERE ")
				.append(" USERNAME = ? ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + userBean.getUserName() + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByLoginName)) {
			pstGetRecordByLoginName = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.
		pstGetRecordByLoginName.setString(1, userBean.getUserName());

		// Execute query and return ResultSet.
		return pstGetRecordByLoginName.executeQuery();
	}

	/**
	 * close possible open statements.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2016-05-20
	 *
	 * @throws java.lang.Throwable
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			if (pstActiveUSER != null) {
				pstActiveUSER.close();
			}
			if (pstGetRecordByUSERNo != null) {
				pstGetRecordByUSERNo.close();
			}
			if (pstGetRecordByLoginName != null) {
				pstGetRecordByLoginName.close();
			}
			if (pstSearchUSERs != null) {
				pstSearchUSERs.close();
			}
			if (pstUpdateUSERPassword != null) {
				pstUpdateUSERPassword.close();
			}
			if (pstInsertRecord != null) {
				pstInsertRecord.close();
			}

			if (pstDeleteRecordByUSERNo != null) {
				pstDeleteRecordByUSERNo.close();
			}
			if (pstGetRecordByUSERName != null) {
				pstGetRecordByUSERName.close();
			}
			if (pstDeleteRecordByLoginName != null) {
				pstDeleteRecordByLoginName.close();
			}
			if (pstUpdateUSER != null) {
				pstUpdateUSER.close();
			}
			if (pstGetRecordByLoginNameLike != null) {
				pstGetRecordByLoginNameLike.close();
			}
			if (pstUpdateRecord != null) {
				pstUpdateRecord.close();
			}

		} finally {
			super.finalize();
		}
	}
}