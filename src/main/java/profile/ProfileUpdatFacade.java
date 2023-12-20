package profile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;

import dbServices.*;
import models.ContactBean;
import models.UserBean;

public class ProfileUpdatFacade {

	UserBean userBean;
	ContactBean contactBean;
	Contact contact;
	User user ;
	
	public ProfileUpdatFacade(UserBean userBean, ContactBean contactBean) throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		super();
		this.userBean = userBean;
		this.contactBean = contactBean;
		contact = new Contact();
		user = new User();
	}
	
	public void updateProfile() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		contact.updateRecord( contactBean,userBean);
		user.updateUserPassword(userBean);
	}
}
