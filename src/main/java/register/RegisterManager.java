
package register;


import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import utils.DatabaseClass;
import models.Seller;
import models.*;
import utils.CodeManager;
import utils.EmailUtility;
import utils.SolideLogCreater;
import utils.ServerConfiguration;
import utils.SolideConstants;

public class RegisterManager {
	
	private DatabaseClass clsDB = null;
	private Seller clsUser = null;
	private Location clsLocation = null;
	private Contact clsContact = null;
	private int generatedCode;
	
	final private Logger logLogger = Logger.getLogger("GLSLogger");
	private ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	ResultSet rs = null;

	public RegisterManager() throws Exception {
		
		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		clsDB = clsConfig.clsDB;
		if (clsDB == null) {
			throw new Exception("No free database connection available");
		}
		if (clsUser == null) {
			clsUser = new Seller();
		}
		if (clsLocation == null) {
			clsLocation = new Location();
		}
		if (clsContact == null) {
			clsContact = new Contact();
		}
	}
	
	public void sendEmail(String toName, String toAddress) throws AddressException, UnsupportedEncodingException, MessagingException
	{
		generatedCode = CodeManager.generateCode();
		String messageToSend = SolideConstants.MESSAGE_TO_SEND + " " + generatedCode;
		
		EmailUtility.sendEmail(clsConfig.emailHost, 
				clsConfig.emailPort,
				clsConfig.emailUser, 
				clsConfig.emailpass,
				clsConfig.emailFromName,
				clsConfig.emailFromAddress,
				toName, 
				toAddress,
				clsConfig.emailSubject,
				messageToSend);
	}

	public int insertNewUSer(SellerBean userbean) {		
		int inserted = 0;
		int iSellerLocNo = 0;
		int iSellerContNo = 0;
		try {	
			//insert first a location for this new seller before
			clsLocation.insertRecord(clsDB,
					"loc_" + userbean.getLoginname(), 
					1, 
					new Date(), 
					1,
					new Date());
			 rs = clsLocation.getRecord(clsDB, "loc_" + userbean.getLoginname());
			if(rs.next())
			{
				iSellerLocNo = rs.getInt(Location.FLD_LOCATIONNO );
			}
			rs.close();
				
			//insert first a contact for this new seller before
			clsContact.insertRecord(clsDB,
					"cont_"+userbean.getLoginname(), 
					userbean.getEmailaddr(), 
					userbean.getEmailaddr(), 
					userbean.getTelefonNr(), 
					1, 
					new Date(), 
					1,
					new Date());
			rs = clsContact.getRecord(clsDB, "cont_" + userbean.getLoginname());
			if(rs.next())
			{
				iSellerContNo = rs.getInt(Contact.FLD_CONTACTNO );
			}
			rs.close();
			//insert new Seller
			 inserted = clsUser.insertRecord(clsDB,
					
					userbean.getCivilite(),
					userbean.getLoginname(),
					userbean.getUserpassword(),
					iSellerContNo, 
					iSellerLocNo, 
					1,
					new Date(), 
					1,
					new Date());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logLogger.error(ex, ex);

		} finally {
			if (logLogger.isDebugEnabled()) {
				logLogger.debug("User inserted.");
			}
		}
		return inserted;
	}
    public int updateUSer(SellerBean userbean) {
    	int updated = 0;
    	int iContactNo = 0;
    	
    	try{    
  		clsUser.updateSELLERSPassword(clsDB, userbean.getLoginname(), userbean.getUserpassword());
    		rs = clsUser.getRecordByName(clsDB, userbean.getLoginname());
    		if( rs.next())
    		{
    			iContactNo = rs.getInt(Seller.FLD_CONTACTNO);
    		}    		
    		rs.close();
    		userbean.setContactNo(iContactNo);
    		clsContact.updateRecord(clsDB, userbean);

	} catch (Exception ex) {
		System.out.println(ex.getMessage());
		logLogger.error(ex, ex);

	} finally {
		if (logLogger.isDebugEnabled()) {
			logLogger.debug("User updated.");
		}
	}
	return updated;
	}
	public  int getGeneratedCode() {
	return generatedCode;

}

}
