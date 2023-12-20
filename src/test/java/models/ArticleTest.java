/**
 * 
 */
package models;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import java.sql.PreparedStatement;
import dbServices.Article;
import dbServices.User;
import register.RegisterFacade;
import utils.DatabaseClass;
import utils.SolideLogCreater;
import utils.ServerConfiguration;

/**
 * @author Ahmed
 *
 */
@Ignore
public class ArticleTest {
	private static Article article;
	public static ServerConfiguration clsConfig;
	public static DatabaseClass clsDB;
	private final static String ARTICLENAME = "MyArticleName";
	private final static String ARTICLENAME_TOBE_REMOVE = "MyArticleName_tobe_remove";
	private final static int ARTICLECATEGORY = 2;
	private final static String ARTICLEDESCRIPTION = "MyDescription";
	private final static int ARTICLEPRICE = -23;
	private final static int NEXT_NO = 555555555;
	private final static int OWNERNO = -854;
	private final static int OWNERNO_TOBE_REMOVE = -999;

	private final static String ARTICLEOLDNEW = "new";
	private final static int ARTICLENO = -8;
	private final static int ARTICLENO_TOBE_REMOVE = -854;
	
	private static UserBean userBean;
	private static ArticleBean articleBean = new ArticleBean();
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
		article = new Article();

		articleBean.setArticleName(ARTICLENAME);
		articleBean.setArticleCategory(ARTICLECATEGORY);
		articleBean.setArticleDescription(ARTICLEDESCRIPTION);
		articleBean.setArticleOldNew(ARTICLEOLDNEW);
		articleBean.setArticlePrice(ARTICLEPRICE);
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
		String sqlGetLocNo = "select locationNo from tb_location where locationName = ? ";
		int locNo = 0;
		try (PreparedStatement pstGetLocNo = clsDB.getConnection().prepareStatement(sqlGetLocNo)) {
			pstGetLocNo.setString(1, LOC_USER_TEST);
			ResultSet rs = pstGetLocNo.executeQuery();
			if (rs.next()) {
				locNo = rs.getInt(Location.FLD_LOCATIONNO);
			}
		}
		;
		String sql = "insert into tb_article values('Article', '900000001','999999999', ? ,'2016-11-06','1','1', ? , 451)";
		if (!clsDB.isStatementPrepared(pstCreate)) {

			pstCreate = clsDB.getConnection().prepareStatement(sql);
		}
		pstCreate.setInt(1, NEXT_NO);
		pstCreate.setInt(2, locNo);
		pstCreate.executeUpdate();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		String sqlDelete = null;
		PreparedStatement pstDelete = null;
		sqlDelete = "delete from tb_article where NextNo = ? ";
		pstDelete = clsDB.getConnection().prepareStatement(sqlDelete);
		pstDelete.setInt(1, NEXT_NO);
		pstDelete.executeUpdate();
		pstDelete.close();
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

	@Test
	public void testGetRecordByOwnerNo() throws Exception {
		User clsUser = new User();
		int ownerNo = 0;
		try (ResultSet rs = clsUser.getRecord(userBean)) {
			if (rs.next()) {
				ownerNo = rs.getInt(User.FLD_USERNO);
			}
		}
		article.insertRecord(clsDB, articleBean, ownerNo);
		try (ResultSet rs = article.getRecordByOwnerNo(ownerNo);) {
			assertTrue(rs.next());
			assertEquals(ARTICLENAME, rs.getString(Article.FLD_NAME));
			assertFalse(rs.next());
		}

	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testremoveRecord() throws Exception {
		User clsUser = new User();
		ArticleBean articleBean = new ArticleBean();
		articleBean.setArticleName(ARTICLENAME_TOBE_REMOVE);
		articleBean.setArticleCategory(ARTICLECATEGORY);
		articleBean.setArticleDescription(ARTICLEDESCRIPTION);
		articleBean.setArticleOldNew(ARTICLEOLDNEW);
		articleBean.setArticlePrice(ARTICLEPRICE);
		articleBean.setArticleNo(ARTICLENO_TOBE_REMOVE);
		int ownerNo = 0;
		try (ResultSet rs = clsUser.getRecord(userBean)) {
			if (rs.next()) {
				ownerNo = rs.getInt(User.FLD_USERNO);
			}
		}
		article.insertRecord(clsDB, articleBean, ownerNo);
		article.removeRecord( articleBean);
		try (ResultSet rs = article.getRecordsByArticleNo(articleBean.getArticleNo());) {
			assertFalse(rs.next());
		}
		
	}

}
