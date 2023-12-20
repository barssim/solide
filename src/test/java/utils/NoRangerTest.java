package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import dbServices.NoRange;
import models.Location;

import org.junit.Assert;

import utils.NoRanger;;
@Ignore
public class NoRangerTest {
	public static ServerConfiguration clsConfig;
	public static DatabaseClass clsDB;
	public static final int LOCATIONNO = 14;
	public static final String STRLOCATIONNAME = "testLocationName"; 
	public static final int ICRUSERNO = 1; 
	public static final int ICHUSERNO = 2;
	static int locNo = 0 ;
	static PreparedStatement pstCreate				= null;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		    clsConfig = ServerConfiguration.getInstance();			
			clsConfig.confige();
			// new SolideLogCreater(clsConfig);
			
			clsDB = clsConfig.clsDB;		
			clsDB.getConnection().setAutoCommit(false);
			Location location = new Location();
			location.insertRecord(clsDB, STRLOCATIONNAME, ICRUSERNO, new Date() , ICHUSERNO, new Date());
			String sqlGetLocNo = "select locationNo from tb_location where locationName = ? ";
			try(PreparedStatement pstGetLocNo = clsDB.getConnection().prepareStatement(sqlGetLocNo))
			{
				pstGetLocNo.setString(1, STRLOCATIONNAME);
				ResultSet rs = pstGetLocNo.executeQuery();
				if (rs.next())
				{
					locNo = rs.getInt(location.FLD_LOCATIONNO);
				}
			};


	}
	
	
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
//		String sql = "delete from tb_norange where noobj = ? and locationno = ? ";
//		try(PreparedStatement pstDelete = clsDB.getConnection().prepareStatement(sql))
//		{
//			pstDelete.setString(1, "Article");
//			pstDelete.setInt(2, LOCATIONNO);
//			pstDelete.execute();
//		};
	}
	
	@Test
	public void testGetNext() throws Exception {
		 int iNext = NoRanger.getNext("ArticleTest");	
		 Assert.assertEquals(555555555, iNext);
	}
	
	 /**
     * close possible open statements.
     *
     * <br><br><b>author</b>  Ahmed El Qarfaoui 2016-06-15
     *
     * @throws java.lang.Throwable
     */
    @Override
    protected void finalize() throws Throwable
    {
		try
		{
			if( pstCreate != null )
			{
				pstCreate.close();
			}
		}
		finally
		{
			super.finalize();
		}
    }
}
