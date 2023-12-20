package register;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import dbServices.Contact;
import dbServices.Location;
import dbServices.NoRange;
import dbServices.User;
import utils.DatabaseClass;
import models.*;
import utils.SolideLogCreater;
import utils.CodeManager;
import utils.ServerConfiguration;
import utils.SolideConstants;

public class RegisterFacade {

	private DatabaseClass clsDB = null;
	private User clsUser = null;
	private Location clsLocation = null;
	private Contact clsContact = null;
	private NoRange clsNoRange = null;
	private String registerCode;

	final private Logger logLogger = Logger.getLogger("GLSLogger");
	private ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	ResultSet rs = null;
	UserBean userbean;
	ContactBean contactBean;
	LocationBean locationBean;

	public RegisterFacade(UserBean userbean, ContactBean contactBean, LocationBean locationBean) throws Exception {

		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		this.userbean = userbean;
		this.contactBean = contactBean;
		this.locationBean = locationBean;
		clsDB = clsConfig.clsDB;
		if (clsDB == null) {
			throw new Exception("No free database connection available");
		}
		if (clsUser == null) {
			clsUser = new User();
		}
		if (clsLocation == null) {
			clsLocation = new Location();
		}
		if (clsContact == null) {
			clsContact = new Contact();
		}
		if (clsNoRange == null) {
			clsNoRange = new NoRange();
		}
	}

	public int generateRegisterCode() {
		return CodeManager.generateCode();
	}

	public int insertNewUSer()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		int inserted = 0;
		int iUserLocNo = 0;
		int iUserContNo = 0;

		try {
			// Begin transaction
			clsDB.getConnection().setAutoCommit(false);
			// insert first a location for this new User before
			clsLocation.insertRecord(clsDB, locationBean);
			clsDB.getConnection().commit();
			rs = clsLocation.getRecord(clsDB, "loc_" + userbean.getUserName());
			if (rs.next()) {
				iUserLocNo = rs.getInt(Location.FLD_LOCATIONNO);
			}
			rs.close();

			// insert first a contact for this new User before
			clsContact.insertRecord(clsDB, contactBean);
			clsDB.getConnection().commit();
			rs = clsContact.getRecord(clsDB, contactBean);
			if (rs.next()) {
				iUserContNo = rs.getInt(Contact.FLD_CONTACTNO);
			}
			rs.close();
			// insert first a article NoRange for this new User before
			clsDB.getConnection().commit();
			
			// insert new User
			inserted = clsUser.insertRecord(userbean, iUserLocNo, iUserContNo);
			clsDB.getConnection().commit();
			
			if (logLogger.isDebugEnabled()) {
				logLogger.debug("User inserted.");
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logLogger.error(ex, ex);
			clsDB.getConnection().rollback();

		} finally {

			clsDB.getConnection().setAutoCommit(true);
		}

		return inserted;

	}

	public String getRegisterCode() {
		return registerCode;
	}

	public DatabaseClass getClsDB() {
		return clsDB;
	}

	public void setClsDB(DatabaseClass clsDB) {
		this.clsDB = clsDB;
	}

	public User getClsUser() {
		return clsUser;
	}

	public void setClsUser(User clsUser) {
		this.clsUser = clsUser;
	}

	public Location getClsLocation() {
		return clsLocation;
	}

	public void setClsLocation(Location clsLocation) {
		this.clsLocation = clsLocation;
	}

	public Contact getClsContact() {
		return clsContact;
	}

	public void setClsContact(Contact clsContact) {
		this.clsContact = clsContact;
	}

	public ServerConfiguration getClsConfig() {
		return clsConfig;
	}

	public void setClsConfig(ServerConfiguration clsConfig) {
		this.clsConfig = clsConfig;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public UserBean getUserbean() {
		return userbean;
	}

	public void setUserbean(UserBean userbean) {
		this.userbean = userbean;
	}

	public ContactBean getContactBean() {
		return contactBean;
	}

	public void setContactBean(ContactBean contactBean) {
		this.contactBean = contactBean;
	}

	public LocationBean getLocationBean() {
		return locationBean;
	}

	public void setLocationBean(LocationBean locationBean) {
	}

	public Logger getLogLogger() {
		return logLogger;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
}
