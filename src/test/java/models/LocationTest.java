/**
 * 
 */
package models;

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

import utils.DatabaseClass;
import utils.SolideLogCreater;
import utils.ServerConfiguration;
import utils.SolideConstants;

/**
 * @author Ahmed
 *
 */
public class LocationTest {
	private static  Location location;
	public static ServerConfiguration clsConfig;
	public static DatabaseClass clsDB;
	private final static String strLocationName = "MyLocation_Test";
	private final  static int iCrUserNo = -1;
	private final  static Date dtCrDtTm = new Date();
	private final  static int iChUserNo = -1;
	private final  static Date dtChDtTm = new Date(); 

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		    clsConfig = ServerConfiguration.getInstance();			
			clsConfig.confige();
			// new SolideLogCreater(clsConfig);
			clsDB = clsConfig.clsDB;
			clsDB.getConnection().setAutoCommit(false);
			location = new Location();
			location.insertRecord(clsDB, strLocationName, iCrUserNo, dtCrDtTm, iChUserNo, dtChDtTm);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		clsDB.getConnection().rollback();
		clsDB.getConnection().setAutoCommit(true);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		location = new Location();
		try( ResultSet rs = location.getRecord(clsDB, strLocationName))
		{
			assertTrue(rs.next());
//			assertFalse(rs.next());
		}
		
	}

}
