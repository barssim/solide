
package register;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.PreparedStatement;

import dbServices.Article;
import dbServices.Location;
import dbServices.User;
import utils.DatabaseClass;
import models.ContactBean;
import models.LocationBean;
import models.UserBean;
import utils.SolideLogCreater;
import utils.ServerConfiguration;

/**
 * @author Ahmed
 *
 */
public class RegisterFacadeTest {
	public static ServerConfiguration clsConfig;
	public static DatabaseClass clsDB;
	static LocationBean locationBean;
	static ContactBean contactBean;
	static UserBean userBean;
	RegisterFacade registerFacade;
	private static final String LOC_USER_TEST = "loc_userTest"; 
	private static final String CONT_USER_TEST = "cont_userTest"; 
	private static final String USER_TEST = "userTest"; 

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		    clsConfig = ServerConfiguration.getInstance();			
			clsConfig.confige();
			// new SolideLogCreater(clsConfig);
			clsDB = clsConfig.clsDB;			
			locationBean = new LocationBean();
			locationBean.setLocationName(LOC_USER_TEST);
			contactBean = new ContactBean();
			contactBean.setContactName(CONT_USER_TEST);
			userBean = new UserBean();
			userBean.setUserName(USER_TEST);
			userBean.setSurname(USER_TEST);
			
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		String sqlDelete = null;
		PreparedStatement pstDelete = null;
		 sqlDelete = "delete from tb_location where locationname = ? " ;
		 pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1 ,LOC_USER_TEST );
		pstDelete.executeUpdate();
		pstDelete.close();
		 sqlDelete = "delete from tb_contact where contactname = ? " ;
		 pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1 ,CONT_USER_TEST );
		pstDelete.executeUpdate();
		pstDelete.close();
		 sqlDelete = "delete from tb_user where username = ? " ;
		 pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1 ,USER_TEST );
		pstDelete.executeUpdate();
		pstDelete.close();
		pstDelete = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exceptionrs
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsertNewUSer() throws Exception {
		 registerFacade = new RegisterFacade(userBean,contactBean,locationBean);
		 registerFacade.insertNewUSer();
		 try (ResultSet rs = new User().getRecord(userBean))
		 {
			 assertTrue(rs.next());
		 }
		
	}
	
	

}
