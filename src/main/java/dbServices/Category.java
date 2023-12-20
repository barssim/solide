package dbServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;

import org.apache.log4j.Logger;

import utils.DatabaseClass;
import utils.ServerConfiguration;

public class Category {

	public static final String FLD_CATEGORYNO = "CATEGORYNO";
	public static final String FLD_CATEGORYNAME = "CATEGORYNAME";
	public static final String FLD_CRUSERNO = "CRUSERNO";
	public static final String FLD_CRDTTM = "CRUSERDTTM";
	public static final String FLD_CHUSERNO = "CHUSERNO";
	public static final String FLD_CHDTTM = "CHUSERDTTM";
	DatabaseClass clsDB;
	private static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	private PreparedStatement pstGetRecordByCategoryName = null;
	public Category() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		clsConfig.confige();
		clsDB = clsConfig.clsDB;
	}
	/**
	 * This method returns a single record, matching a given categoryName.
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
	public ResultSet getRecord(String categoryName)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		// Declaration of method variables.
		Logger logLogger = Logger.getLogger("GLSLogger");
		StringBuilder sbSQL = new StringBuilder();

		// Create SQL statement.
		sbSQL.append(" SELECT ")
			.append("CATEGORYNO, ")
			.append("CATEGORYNAME, ")				
			.append("CRUSERNO, ")
			.append("CRUSERDTTM, ")
			.append("CHUSERNO, ")
			.append("CHUSERDTTM")
			.append(" FROM ")
			.append(" tb_category ")
			.append(" WHERE ")
			.append(" CATEGORYNAME = ? ");

		// Log' statement and parameter.
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("          SQL : " + sbSQL);
			logLogger.debug("PARAM for SQL : " + categoryName + ".");
		}

		// Prepare statement, if needed.
		if (!clsDB.isStatementPrepared(pstGetRecordByCategoryName)) {
			pstGetRecordByCategoryName = clsDB.getConnection().prepareStatement(sbSQL.toString());
		}

		// Set statement parameters.
		pstGetRecordByCategoryName.setString(1, categoryName);

		// Execute query and return ResultSet.
		return pstGetRecordByCategoryName.executeQuery();
	}
}
