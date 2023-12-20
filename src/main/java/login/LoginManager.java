package login;
import utils.DatabaseClass;
import models.Seller;
import utils.SolideLogCreater;
import utils.ServerConfiguration;

import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class LoginManager {
	private DatabaseClass clsDB = null;
	private Seller clsUser = null;
	final private Logger logLogger = Logger.getLogger("GLSLogger");
	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	public LoginManager() throws Exception {
		
		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		clsDB = clsConfig.clsDB;
//		if (clsDB == null) {
//			throw new Exception("No free database connection available");
//		}
		if (clsUser == null) {
			clsUser = new Seller();
		}
	}

	public boolean checkLoginInputUsername(String username) {
		ResultSet rs = null;
		boolean userExist = false;

		try {			
			rs = clsUser.getRecord(clsDB, username);
			if (rs.next()) {
				userExist = true;
				rs.close();
			}

		} catch (Exception ex) {			
			logLogger.error(ex.getMessage(), ex);

		} finally {
			if (logLogger.isDebugEnabled()) {
				logLogger.debug("Username checked.");
			}
		}
		return userExist;
	}

	public String checkLoginInputPassword(String username, String password) {
		ResultSet rs = null;
		String returnedResult = "noBody";

		try {			
			rs = clsUser.getRecord(clsDB, username);
			if (rs.next()) {
				String fetchedPassword = StringUtils.trimToEmpty(rs.getString(Seller.FLD_PASSWORD));
				String fetchedSurname = StringUtils.trimToEmpty(rs.getString(Seller.FLD_SURNAME ));
				if (fetchedPassword.equals(password)) {
					switch(fetchedSurname)
					{
					case  "Mr" : returnedResult = "Monsieur"; break; 
					case  "Mme" : returnedResult = "Madame"; break; 
					default : returnedResult = "demoiselle";
					}
					
					rs.close();
				}
			}

		} catch (Exception ex) {
			logLogger.error(ex.getMessage(), ex);

		} finally {
			if (logLogger.isDebugEnabled()) {
				logLogger.debug("Username checked.");
			}
		}
		
		return returnedResult;

	}

	@Override
	protected void finalize() throws Throwable {
		if (clsDB != null) {
			clsDB = null;
		}
	}
}
