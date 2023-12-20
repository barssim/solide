
package dbServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DatabaseClass;
import models.ArticleBean;
import utils.ServerConfiguration;
import utils.SolideConstants;
import utils.SolideLogCreater;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

/**
 * Class for handle the tbARTICLE data.
 *
 * @author Ahmed El Qarfaoui 2016-06-15
 */
public class Article {
	// Declaration for class variables and constants.

	public static final String FLD_ARTICLENO = "ARTICLENO";
	public static final String FLD_NAME = "NAME";
	public static final String FLD_MANUFACTURER = "MANUFACTURER";
	public static final String FLD_TYPE = "TYPE";
	public static final String FLD_MODEL = "MODEL";
	public static final String FLD_DESCRIPTION = "DESCRIPTION";
	public static final String FLD_OLD_NEW = "OLD_NEW";
	public static final String FLD_PRICE = "PRICE";
	public static final String FLD_CATEGORYNO = "CATEGORYNO";
	public static final String FLD_OWNERNO = "OWNERNO";
	public static final String FLD_CRUSERNO = "CRUSERNO";
	public static final String FLD_IMAGE1 = "IMAGE1";
	public static final String FLD_IMAGE2 = "IMAGE2";
	public static final String FLD_IMAGE3 = "IMAGE3";
	public static final String FLD_CRDTTM = "CRUSERDTTM";
	public static final String FLD_CHUSERNO = "CHUSERNO";
	public static final String FLD_CHDTTM = "CHUSERDTTM";
	public static final String FLD_STATUS = "STATUS";

	private PreparedStatement pstInsertRecord = null;
	private PreparedStatement pstUpdateRecord = null;
	private PreparedStatement pstRemoveRecord = null;
	private PreparedStatement pstGetRecordByARTICLEName = null;
	private PreparedStatement pstGetRecordByOwnerNo = null;
	private PreparedStatement pstGetRecordByCategoryNo = null;
	private PreparedStatement pstGetRecordByArticleNo = null;
	private PreparedStatement pstApdateToReserved = null;
	private PreparedStatement pstGetRecords = null;
	private PreparedStatement pstGetReservedRecords = null;
	private PreparedStatement pstGetNumberOfExitingArticles = null;
	private PreparedStatement pstGetNumberOfArticlesFromTypeAiles = null;
	private PreparedStatement pstGetFiltredRecords = null;
	DatabaseClass clsDB;
	private static final String RESERVED = "reserved";
	private static final Logger logLogger = LoggerFactory.getLogger(Article.class);

	private static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	public Article() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		clsConfig.confige();
		clsDB = clsConfig.clsDB;
//	    new SolideLogCreater(clsConfig);
	}

	public int removeRecord(ArticleBean articleBean)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" DELETE FROM ").append(" tb_article ").append(" WHERE ").append(FLD_ARTICLENO + " = ? ");
		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + articleBean.getArticleNo());
		}
		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstRemoveRecord)) {
			pstRemoveRecord = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		pstRemoveRecord.setInt(1, articleBean.getArticleNo());
		// Execute query and return count of affected rows.
		return pstRemoveRecord.executeUpdate();
	}

	public int insertRecord(DatabaseClass clsDB, ArticleBean articleBean, int ownerNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" INSERT INTO ").append(" tb_article( ").append(FLD_ARTICLENO).append(",").append(FLD_NAME)
				.append(",").append(FLD_DESCRIPTION).append(",").append(FLD_OLD_NEW).append(",")
				.append(FLD_MANUFACTURER).append(",").append(FLD_MODEL).append(",").append(FLD_TYPE).append(",")
				.append(FLD_PRICE).append(",").append(FLD_CATEGORYNO).append(",").append(FLD_OWNERNO).append(",")
				.append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3).append(",")
				.append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO).append(",")
				.append(FLD_CHDTTM).append(")").append(" VALUES(  ?, ?, ?, ?, ? ,?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?,?) ");
		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {
			sbLog.append(articleBean.getArticleNo()).append(", ").append(articleBean.getArticleName()).append(", ")
					.append(articleBean.getArticleDescription()).append(", ").append(articleBean.getArticleOldNew())
					.append(", ").append(articleBean.getArticleManufacturer()).append(", ")
					.append(articleBean.getArticleModel()).append(", ").append(articleBean.getArticleType())
					.append(", ").append(articleBean.getArticlePrice()).append(", ")
					.append(articleBean.getArticleCategory()).append(", ").append(ownerNo).append(", ")
					.append(articleBean.getArticleImage1()).append(", ").append(articleBean.getArticleImage2())
					.append(", ").append(articleBean.getArticleImage3()).append(", ").append(ownerNo).append(", ")
					.append(new java.sql.Timestamp(new Date().getTime())).append(", ").append(ownerNo).append(", ")
					.append(new java.sql.Timestamp(new Date().getTime())).append(".");
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstInsertRecord)) {
			Connection con = clsDB.getConnection();
			pstInsertRecord = con.prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.

		pstInsertRecord.setInt(1, articleBean.getArticleNo());
		pstInsertRecord.setString(2, articleBean.getArticleName());
		pstInsertRecord.setString(3, articleBean.getArticleDescription());
		pstInsertRecord.setString(4, articleBean.getArticleOldNew());
		pstInsertRecord.setString(5, articleBean.getArticleManufacturer());
		pstInsertRecord.setString(6, articleBean.getArticleModel());
		pstInsertRecord.setString(7, articleBean.getArticleType());
		pstInsertRecord.setInt(8, articleBean.getArticlePrice());
		pstInsertRecord.setInt(9, articleBean.getArticleCategory());
		pstInsertRecord.setInt(10, ownerNo);
		pstInsertRecord.setString(11, articleBean.getArticleImage1());
		pstInsertRecord.setString(12, articleBean.getArticleImage2());
		pstInsertRecord.setString(13, articleBean.getArticleImage3());
		pstInsertRecord.setInt(14, ownerNo);
		pstInsertRecord.setTimestamp(15, new java.sql.Timestamp(new Date().getTime()));
		pstInsertRecord.setInt(16, ownerNo);
		pstInsertRecord.setTimestamp(17, new java.sql.Timestamp(new Date().getTime()));
		// Execute query and return count of affected rows.
		return pstInsertRecord.executeUpdate();
	}

	/**
	 * 
	 * @param clsDB
	 * @param articleBean
	 * @param ownerNo
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int updateRecord(DatabaseClass clsDB, ArticleBean articleBean, int ownerNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		StringBuilder sbSQL = new StringBuilder();
		StringBuilder sbLog = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" Update ").append(" tb_article ").append(" SET ");
		if (articleBean.getArticleName() != null) {
			sbSQL.append(FLD_NAME + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleName()).append(", ");
		}
		if (articleBean.getArticleManufacturer() != null) {
			sbSQL.append(FLD_MANUFACTURER + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleManufacturer()).append(", ");
		}
		if (articleBean.getArticleType() != null) {
			sbSQL.append(FLD_TYPE + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleType()).append(", ");
		}
		if (articleBean.getArticleModel() != null) {
			sbSQL.append(FLD_MODEL + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleModel()).append(", ");
		}
		if (articleBean.getArticleDescription() != null) {
			sbSQL.append(FLD_DESCRIPTION + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleDescription()).append(", ");
		}
		if (articleBean.getArticleOldNew() != null) {
			sbSQL.append(FLD_OLD_NEW + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleOldNew()).append(", ");
		}
		if (articleBean.getArticlePrice() != 0) {
			sbSQL.append(FLD_PRICE + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticlePrice()).append(", ");
		}
		if (articleBean.getArticleCategory() != 0) {
			sbSQL.append(FLD_CATEGORYNO + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleCategory()).append(", ");
		}
		if (ownerNo != 0) {
			sbSQL.append(FLD_OWNERNO + " = ? ").append(" , ");
			sbLog.append(ownerNo).append(", ");

		}
		if (articleBean.getArticleImage1() != null) {
			sbSQL.append(FLD_IMAGE1 + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleImage1()).append(", ");
		}
		if (articleBean.getArticleImage2() != null) {
			sbSQL.append(FLD_IMAGE2 + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleImage2()).append(", ");
		}
		if (articleBean.getArticleImage3() != null) {
			sbSQL.append(FLD_IMAGE3 + " = ? ").append(" , ");
			sbLog.append(articleBean.getArticleImage3()).append(",");
		}
		if (ownerNo != 0) {
			sbSQL.append(FLD_CHUSERNO + " = ? ").append(" , ");
			sbLog.append(ownerNo).append(",");
		}
		sbSQL.append(FLD_CHDTTM + " = ? ").append(" , ");
		sbLog.append(new java.sql.Timestamp(new Date().getTime())).append(",");

		if (articleBean.getArticleStatus() != null) {
			sbSQL.append(FLD_STATUS + " = ? ");
			sbLog.append(articleBean.getArticleStatus()).append(",");
		}

		String sqlString = sbSQL.toString().substring(0, sbSQL.lastIndexOf(",")) + " WHERE " + FLD_ARTICLENO + " = ? ";

		sbLog.append(articleBean.getArticleNo());

		// Log' statement and parameters.
		if (logLogger.isDebugEnabled()) {

			logLogger.debug("          SQL : " + sqlString);
			logLogger.debug("PARAM for SQL : " + sbLog);
		}
        int i = 1;
		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstInsertRecord)) {
			Connection con = clsDB.getConnection();
			pstUpdateRecord = con.prepareStatement(sqlString);
		}

		if (articleBean.getArticleName() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleName());
		}
		if (articleBean.getArticleManufacturer() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleManufacturer());
		}
		if (articleBean.getArticleType() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleType());
		}
		if (articleBean.getArticleModel() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleModel());
		}
		if (articleBean.getArticleDescription() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleDescription());
		}
		if (articleBean.getArticleOldNew() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleOldNew());
		}
		if (articleBean.getArticlePrice() != 0) {
			pstUpdateRecord.setInt(i++, articleBean.getArticlePrice());
		}
		if (articleBean.getArticleCategory() != 0) {
			pstUpdateRecord.setInt(i++, articleBean.getArticleCategory());
		}
		if (ownerNo != 0) {
			pstUpdateRecord.setInt(i++, ownerNo);

		}
		if (articleBean.getArticleImage1() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleImage1());
		}
		if (articleBean.getArticleImage2() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleImage1());
		}
		if (articleBean.getArticleImage3() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleImage1());
		}
		if (ownerNo != 0) {
			pstUpdateRecord.setInt(i++, ownerNo);
		}
		pstUpdateRecord.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime()));

		if (articleBean.getArticleStatus() != null) {
			pstUpdateRecord.setString(i++, articleBean.getArticleStatus());
		}
		pstUpdateRecord.setInt(i++, articleBean.getArticleNo());

		// Execute query and return count of affected rows.
		return pstUpdateRecord.executeUpdate();
	}

	/**
	 * This method returns a single record, matching a given a Manufacturer , Type,
	 * Modele, category or oldNew
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
	// public ResultSet getRecord(DatabaseClass clsDB,
	// String strManufacturer,
	// String strType,
	// String strModel,
	// int iCategoryNo,
	// String strOldNew)
	// throws SQLException, ClassNotFoundException, InstantiationException,
	// IllegalAccessException {
	// // Declaration of method variables.
	// Logger logLogger = Logger.getLogger("GLSLogger");
	// StringBuilder sbSQL = new StringBuilder();
	//
	// // Create SQL statement.
	// sbSQL.append(" SELECT ").append("ARTICLENO,
	// ").append(FLD_NAME).append(",").append(FLD_DESCRIPTION).append(",")
	// .append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO).append(",")
	// .append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3).append(",")
	// .append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO).append(",")
	// .append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append("
	// WHERE ");
	// if(!strManufacturer.isEmpty())
	// {
	// sbSQL.append(FLD_MANUFACTURER + " = ? ");
	// }
	// if(!strType.isEmpty())
	// {
	// sbSQL.append(FLD_TYPE + " = ? ");
	// }
	// if(!strModel.isEmpty())
	// {
	// sbSQL.append(FLD_MODEL + " = ? ");
	// }
	// if(!strOldNew.isEmpty())
	// {
	// sbSQL.append(FLD_OLD_NEW + " = ? ");
	// }
	// if(iCategoryNo != 0)
	// {
	// sbSQL.append(FLD_CATEGORYNO + " = ? ");
	// }
	//
	// // Log' statement and parameter.
	// if (logLogger.isDebugEnabled()) {
	// logLogger.debug(" SQL : " + sbSQL);
	// }
	//
	// // Prepare statement, if needed.
	// if (!clsDB.isStatementPrepared(pstGetRecordByARTICLEName)) {
	// pstGetRecordByARTICLEName =
	// clsDB.getConnection().prepareStatement(sbSQL.toString());
	// }
	//
	// // Set statement parameters.
	// pstGetRecordByARTICLEName.setString(1, strARTICLEName);
	//
	// // Execute query and return ResultSet.
	// return pstGetRecordByARTICLEName.executeQuery();
	// }

	/**
	 * This method returns a single record, matching a given ownerNo .
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
	public ResultSet getRecordByOwnerNo(int ownerNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" WHERE ")
				.append(FLD_OWNERNO + " = ? ").append(" ORDER BY ").append(FLD_CRDTTM).append(" DESC ");
		;

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + ownerNo + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByOwnerNo)) {
			pstGetRecordByOwnerNo = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.
		pstGetRecordByOwnerNo.setInt(1, ownerNo);

		// Execute query and return ResultSet.
		return pstGetRecordByOwnerNo.executeQuery();
	}

	/**
	 * This method returns a single record, matching a given categoryNo .
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2017-03-15
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getRecordByCategoryNo(int categoryNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" WHERE ")
				.append(FLD_CATEGORYNO + " = ? ").append(" ORDER BY ").append(FLD_CRDTTM).append(" DESC ");
		;

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + categoryNo + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByOwnerNo)) {
			pstGetRecordByOwnerNo = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.
		pstGetRecordByOwnerNo.setInt(1, categoryNo);

		// Execute query and return ResultSet.
		return pstGetRecordByOwnerNo.executeQuery();
	}

	/**
	 * This method returns a all records.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2017-01-04
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getRecords()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" ORDER BY ")
				.append(FLD_CRDTTM).append(" DESC ");
		;

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecords)) {
			pstGetRecords = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Execute query and return ResultSet.
		return pstGetRecords.executeQuery();
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getReservedRecords()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" WHERE ")
				.append(FLD_STATUS + " = ? ").append(" ORDER BY ").append(FLD_CRDTTM).append(" DESC ");
		;

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetReservedRecords)) {
			pstGetReservedRecords = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Execute query and return ResultSet.
		pstGetReservedRecords.setString(1, SolideConstants.STATUS_RESERVED);
		return pstGetReservedRecords.executeQuery();
	}
	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getAllNotReservedRecords()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();
		
		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
		.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
		.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
		.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
		.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" WHERE ")
		.append(FLD_STATUS + " <> ? ").append(" OR ").append(FLD_STATUS + " is null ").append(" ORDER BY ").append(FLD_CRDTTM).append(" DESC ");
		;
		
		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
		}
		
		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetReservedRecords)) {
			pstGetReservedRecords = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		
		// Execute query and return ResultSet.
		pstGetReservedRecords.setString(1, SolideConstants.STATUS_RESERVED);
		return pstGetReservedRecords.executeQuery();
	}

	public ResultSet getNumberOfExitingArticles()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append("count(*)").append(" FROM ").append(" tb_article ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetNumberOfExitingArticles)) {
			pstGetNumberOfExitingArticles = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Execute query and return ResultSet.
		return pstGetNumberOfExitingArticles.executeQuery();
	}

	public ResultSet getNumberOfArticlesFromTypeAiles(int categoryNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append("count(*)").append(" FROM ").append(" tb_article ").append(" WHERE ")
				.append(FLD_CATEGORYNO + " = ? ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetNumberOfArticlesFromTypeAiles)) {
			pstGetNumberOfArticlesFromTypeAiles = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		pstGetNumberOfArticlesFromTypeAiles.setInt(1, categoryNo);
		// Execute query and return ResultSet.
		return pstGetNumberOfArticlesFromTypeAiles.executeQuery();
	}

	/**
	 * This method returns a all records by category.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2017-01-05
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getRecordsByCategory(int categoryNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" WHERE ")
				.append(FLD_CATEGORYNO + " = ? ").append(" ORDER BY ").append(FLD_CRDTTM).append(" DESC ");
		;

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + categoryNo + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByCategoryNo)) {
			pstGetRecordByCategoryNo = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		// Set statement parameters.
		pstGetRecordByCategoryNo.setInt(1, categoryNo);

		// Execute query and return ResultSet.
		return pstGetRecordByCategoryNo.executeQuery();
	}

	/**
	 * 
	 * @param articleNo
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */

	public ResultSet getRecordsByArticleNo(int articleNo)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(",").append(FLD_STATUS).append(",").append(FLD_MODEL).append(",")
				.append(FLD_MANUFACTURER).append(",").append(FLD_TYPE).append(" FROM ").append(" tb_article ")
				.append(" WHERE ").append(FLD_ARTICLENO + " = ? ").append(" ORDER BY ").append(FLD_CRDTTM)
				.append(" DESC ");
		;

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + articleNo + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByArticleNo)) {
			pstGetRecordByArticleNo = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		// Set statement parameters.
		pstGetRecordByArticleNo.setInt(1, articleNo);

		// Execute query and return ResultSet.
		return pstGetRecordByArticleNo.executeQuery();
	}

	/**
	 * 
	 * @param clsDB
	 * @param articleBean
	 * @param userNo
	 * @param reservationStatus
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int changeReservation(DatabaseClass clsDB, ArticleBean articleBean, int userNo, String reservationStatus)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");

		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" Update ")
			 .append(" tb_article ")
			 .append(" SET ")			 
			 .append(FLD_STATUS + " = ? ")
			 .append(" , ")
			 .append(FLD_CHUSERNO + " = ? ")
			 .append(" , ")
			 .append(FLD_CHDTTM + " = ? ")
			 .append(" WHERE ")
			 .append(FLD_ARTICLENO + " = ? ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + articleBean.getArticleStatus() + "," + userNo + ","
					+ articleBean.getArticleNo() + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstApdateToReserved)) {
			pstApdateToReserved = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		// Set statement parameters.
		pstApdateToReserved.setString(1, reservationStatus);
		pstApdateToReserved.setInt(2, userNo);
		pstApdateToReserved.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
		pstApdateToReserved.setInt(4, articleBean.getArticleNo());

		// Execute query and return ResultSet.
		return pstApdateToReserved.executeUpdate();
	}

	/**
	 * This method returns filtred records by manufacturer, model, type and oldNew.
	 *
	 * <br>
	 * <br>
	 * <b>author</b> Ahmed El Qarfaoui 2017-04-20
	 * 
	 *
	 * @return The concerning ResultSet.
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ResultSet getfiltredRecords(String category, String manufacturer, String model, String type, String oldNew)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		// Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ").append(FLD_ARTICLENO).append(",").append(FLD_NAME).append(",").append(FLD_DESCRIPTION)
				.append(",").append(FLD_OLD_NEW).append(",").append(FLD_PRICE).append(",").append(FLD_CATEGORYNO)
				.append(",").append(FLD_IMAGE1).append(",").append(FLD_IMAGE2).append(",").append(FLD_IMAGE3)
				.append(",").append(FLD_CRUSERNO).append(",").append(FLD_CRDTTM).append(",").append(FLD_CHUSERNO)
				.append(",").append(FLD_CHDTTM).append(" FROM ").append(" tb_article ").append(" WHERE ")
				.append(" ( 1  = ? ").append(" OR ").append(FLD_MANUFACTURER + " = ? ) ").append(" AND ")
				.append(" ( 1  = ? ").append(" OR ").append(FLD_MODEL + " = ? ) ").append(" AND ").append(" ( 1  = ? ")
				.append(" OR ").append(FLD_TYPE + " = ? )").append(" AND ").append(" ( 1  = ? ").append(" OR ")
				.append(FLD_OLD_NEW + " = ? )").append(" AND ").append(" ( 1  = ? ").append(" OR ")
				.append(FLD_CATEGORYNO + " IN ( select categoryno from tb_category where categoryname = ? ) )")
				.append(" AND ").append(FLD_STATUS + " IS NULL ").append(" ORDER BY ").append(FLD_CRDTTM).append(" DESC ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + manufacturer + "," + model + "," + type + "," + oldNew + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetFiltredRecords)) {
			pstGetFiltredRecords = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}
		// Set statement parameters.
		if (manufacturer.equals("Choisir...")) {
			pstGetFiltredRecords.setInt(1, 1);
			pstGetFiltredRecords.setString(2, FLD_MANUFACTURER);
		} else {
			pstGetFiltredRecords.setInt(1, 0);
			pstGetFiltredRecords.setString(2, manufacturer);
		}
		if (model.equals("Choisir...")) {
			pstGetFiltredRecords.setInt(3, 1);
			pstGetFiltredRecords.setString(4, FLD_MODEL);
		} else {
			pstGetFiltredRecords.setInt(3, 0);
			pstGetFiltredRecords.setString(4, model);
		}
		if (type.equals("Choisir...")) {
			pstGetFiltredRecords.setInt(5, 1);
			pstGetFiltredRecords.setString(6, FLD_TYPE);
		} else {
			pstGetFiltredRecords.setInt(5, 0);
			pstGetFiltredRecords.setString(6, type);
		}
		if (oldNew.equals("Choisir...")) {
			pstGetFiltredRecords.setInt(7, 1);
			pstGetFiltredRecords.setString(8, FLD_OLD_NEW);
		} else {
			pstGetFiltredRecords.setInt(7, 0);
			pstGetFiltredRecords.setString(8, oldNew);
		}
		if (category.equals("Choisir...")) {
			pstGetFiltredRecords.setInt(9, 1);
			pstGetFiltredRecords.setString(10, "categoryname");
		} else {
			pstGetFiltredRecords.setInt(9, 0);
			pstGetFiltredRecords.setString(10, category);
		}
		// Execute query and return ResultSet.
		return pstGetFiltredRecords.executeQuery();
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
			if (pstGetRecordByARTICLEName != null) {
				pstGetRecordByARTICLEName.close();
			}
			if (pstGetRecordByOwnerNo != null) {
				pstGetRecordByOwnerNo.close();
			}
			if (pstGetRecords != null) {
				pstGetRecords.close();
			}
			if (pstGetNumberOfExitingArticles != null) {
				pstGetNumberOfExitingArticles.close();
			}
			if (pstGetNumberOfArticlesFromTypeAiles != null) {
				pstGetNumberOfArticlesFromTypeAiles.close();
			}
			if (pstGetRecordByCategoryNo != null) {
				pstGetRecordByCategoryNo.close();
			}
			if (pstGetRecordByArticleNo != null) {
				pstGetRecordByArticleNo.close();
			}

			if (pstGetFiltredRecords != null) {
				pstGetFiltredRecords.close();
			}
			if (pstApdateToReserved != null) {
				pstApdateToReserved.close();
			}
			if (pstGetReservedRecords != null) {
				pstGetReservedRecords.close();
			}
			if (pstUpdateRecord != null) {
				pstUpdateRecord.close();
			}

		} finally {
			super.finalize();
		}
	}
}