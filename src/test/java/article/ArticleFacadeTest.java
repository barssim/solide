/**
 * 
 */
package article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import dbServices.NoRange;
import junit.framework.Assert;
import models.ArticleBean;
import models.ContactBean;
import models.LocationBean;
import models.UserBean;
import register.RegisterFacade;
import utils.DatabaseClass;
import utils.ServerConfiguration;

/**
 * @author Ahmed
 *
 */
@Ignore
public class ArticleFacadeTest {
	public static ServerConfiguration clsConfig;
	public static DatabaseClass clsDB;
	private final static String articleName = "MyArticleName";
	private final static int articleCategory = 2;
	private final static String articleDescription = "MyDescription";
	private final static int articlePrice = -23;
	private final static String articleOldNew = "new";
	private final static int ARTICLENO = -8;
	private static UserBean userBean;
	private static ArticleBean articleBean = new ArticleBean();
	private static ArticleFacade articleFacade;
	static LocationBean locationBean;
	static ContactBean contactBean;
	static RegisterFacade registerFacade;
	private static final String LOC_USER_TEST = "loc_user3";
	private static final String CONT_USER_TEST = "cont_user3";
	private static final String USER_TEST = "user3";
	static PreparedStatement pstCreate = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		clsConfig = ServerConfiguration.getInstance();
		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		clsDB = clsConfig.clsDB;

		articleBean.setArticleName(articleName);
		articleBean.setArticleCategory(articleCategory);
		articleBean.setArticleDescription(articleDescription);
		articleBean.setArticleOldNew(articleOldNew);
		articleBean.setArticlePrice(articlePrice);
		articleBean.setArticleNo(ARTICLENO);

		locationBean = new LocationBean();
		locationBean.setLocationName(LOC_USER_TEST);
		contactBean = new ContactBean();
		contactBean.setContactName(CONT_USER_TEST);
		userBean = new UserBean();
		userBean.setUserName(USER_TEST);
		userBean.setSurname(USER_TEST);
		registerFacade = new RegisterFacade(userBean, contactBean, locationBean);
		registerFacade.insertNewUSer();
		// insert record in tb_Range
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		String sqlDelete = null;
		PreparedStatement pstDelete = null;
		sqlDelete = "delete from tb_article where Name = ? ";
		pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1, articleBean.getArticleName());
		pstDelete.executeUpdate();
		pstDelete.close();
		sqlDelete = "delete from tb_user where username = ? ";
		pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1, USER_TEST);
		pstDelete.executeUpdate();
		pstDelete.close();
		sqlDelete = "delete from tb_location where locationname = ? ";
		pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1, LOC_USER_TEST);
		pstDelete.executeUpdate();
		pstDelete.close();
		sqlDelete = "delete from tb_contact where contactname = ? ";
		pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1, CONT_USER_TEST);
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
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		String sqlDelete = null;
		PreparedStatement pstDelete = null;
		sqlDelete = "delete from tb_article where Name = ? ";
		pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setString(1, articleBean.getArticleName());
		pstDelete.executeUpdate();
		pstDelete.close();
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIncrementNoRangeAfterNewArticle() throws Exception {
		int actualRangeBevor = 0;
		int actualRangeAfter = 0;

		articleFacade = new ArticleFacade(articleBean, userBean);
		
		actualRangeBevor = fetchActualRange();
		articleFacade.insertNewArticle();

		actualRangeAfter = fetchActualRange();
		Assert.assertEquals(actualRangeBevor + 1, actualRangeAfter);
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private int fetchActualRange()
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int actualRangeNo = 0;

		// fetch the locNo corresponding to locationName

		String sqlSrt = "select * from tb_article where noobj = ?  ";
		PreparedStatement ps = clsDB.getConnection().prepareStatement(sqlSrt);
		ps.setString(1, "Article");
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			actualRangeNo = rs.getInt(NoRange.FLD_NEXTNO);
		}
		return actualRangeNo;

	}

}
