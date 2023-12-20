package login;

import models.UserBean;
import utils.SolideLogCreater;
import utils.ServerConfiguration;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import dbServices.User;

public class LoginFacade {
	private User clsUser = null;
	private UserBean userBean;
	final private Logger logLogger = Logger.getLogger("GLSLogger");
	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	String fetchedPassword;
	String fetchedSurname;
	int fetchedUserNo;
	boolean userExist = false;

	public LoginFacade(UserBean userBean) throws Exception {
		this.userBean = userBean;
		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		if (clsUser == null) {
			clsUser = new User();
		}
		fetchUser();
	}

	public boolean checkLoginInputUsername() {
		return userExist;
	}

	private int fetchUser()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		try (ResultSet rs = clsUser.getRecord( userBean);) {
			if (rs.next()) {
				userExist = true;
				fetchedUserNo = rs.getInt(User.FLD_USERNO);
				fetchedSurname = StringUtils.trimToEmpty(rs.getString(User.FLD_SURNAME));
				fetchedPassword = StringUtils.trimToEmpty(rs.getString(User.FLD_PASSWORD));
			}
		}
		return fetchedUserNo;
	}

	public String checkLoginInputPassword() {
		String returnedResult = "noBody";
		try {
			if (fetchedPassword.equals(userBean.getUserpassword())) {
				switch (fetchedSurname) {
				case "Mr":
					returnedResult = "Monsieur";
					break;
				case "Mme":
					returnedResult = "Madame";
					break;
				default:
					returnedResult = "demoiselle";
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
	
	public String retrievRole() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		String fetchedRole = null;
		try (ResultSet rs = clsUser.getRecord( userBean);) {
			if (rs.next()) {
				fetchedRole = StringUtils.trimToEmpty(rs.getString(User.FLD_ROLE));
			}
		}
		return fetchedRole;
	}

}
