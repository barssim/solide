package article;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;



import dbServices.Article;
import dbServices.User;
import utils.DatabaseClass;
import utils.NoRanger;
import models.ArticleBean;
import models.UserBean;
import utils.ServerConfiguration;
import utils.SolideConstants;
import utils.SolideLogCreater;

public class ArticleFacade {
	private ArticleBean articleBean;
	private UserBean userBean;
	private Article clsArticle;
	private User clsUser;
	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	public ArticleFacade(ArticleBean articleBean, UserBean userBean) throws Exception {
		this.articleBean = articleBean;
		this.userBean = userBean;
		clsConfig.confige();
		// new SolideLogCreater(clsConfig);
		if (clsArticle == null) {
			clsArticle = new Article();
		}
		if (clsUser == null) {
			clsUser = new User();
		}
	}
	
	

	public ArticleFacade() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		if (clsArticle == null) {
			clsArticle = new Article();
		}
	}



	public int insertNewArticle()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		int inserted = 0;
		int userNo = 0;
		int locNo = 0;
		Connection con = null;

		try (ResultSet rs = clsUser.getRecord(userBean)) {
			if (rs.next()) {
				locNo = rs.getInt(User.FLD_LOCATIONNO);
				userNo = rs.getInt(User.FLD_USERNO);
			}
		}

		articleBean.setArticleNo(NoRanger.getNext( SolideConstants.ARTICLE));
		DatabaseClass clsDB = clsConfig.clsDB;
		con = clsDB.getConnection();
		con.setAutoCommit(false);
		try {
			clsArticle.insertRecord(clsDB, articleBean, userNo);
			NoRanger.incrementNextNo(SolideConstants.ARTICLE);
			con.commit();			
		}

		catch (Exception ex) {
			inserted = -1;
			con.rollback();
			con.setAutoCommit(true);
			
		} 
		return inserted;
	}
	
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int updateArticle()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		int updated = 0;
		int userNo = 0;
		int locNo = 0;
		Connection con = null;
		
		try (ResultSet rs = clsUser.getRecord(userBean)) {
			if (rs.next()) {
				locNo = rs.getInt(User.FLD_LOCATIONNO);
				userNo = rs.getInt(User.FLD_USERNO);
			}
		}
		
//		articleBean.setArticleNo(NoRanger.getNext( SolideConstants.ARTICLE));
		DatabaseClass clsDB = clsConfig.clsDB;
		con = clsDB.getConnection();
		con.setAutoCommit(false);

		if (SolideConstants.STATUS_RESERVED.equals(getArticleStatus(articleBean))) {
			updated = -2;
			return updated;
		} else {
			try {
				clsArticle.updateRecord(clsDB, articleBean, userNo);
				con.commit();
			}

			catch (Exception ex) {
				updated = -1;
				con.rollback();
				con.setAutoCommit(true);

			}
			return updated;
		}
	}

	public int removeArticle()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		int articleRemoved;
		articleRemoved = clsArticle.removeRecord(articleBean);
		return articleRemoved;
	}
	
	
	/**
	 * 
	 * @param articleBean
	 * @param userBean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * 
	 * @throws SQLException
	 */
	public int changeArticleReservation(ArticleBean articleBean, int userNo , String reservationStatus )
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		DatabaseClass clsDB = clsConfig.clsDB;
		return clsArticle.changeReservation(clsDB, articleBean, userNo, reservationStatus);
	}

	public List<ArticleBean> fetchMyArticles()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		List<ArticleBean> lstArticles = new ArrayList<>();
		int userNo = 0;
		userNo = getUserNo();
		try (ResultSet rs = clsArticle.getRecordByOwnerNo(userNo);) {
			while (rs.next()) {
				ArticleBean articleBean = new ArticleBean();
				articleBean.setArticleNo(rs.getInt( Article.FLD_ARTICLENO ));
				articleBean.setArticleName(rs.getString(Article.FLD_NAME));
				articleBean.setArticleCategory(rs.getInt(Article.FLD_CATEGORYNO));
				articleBean.setArticleDescription(rs.getString(Article.FLD_DESCRIPTION));
				articleBean.setArticleOldNew(rs.getString(Article.FLD_OLD_NEW));
				articleBean.setArticlePrice(rs.getInt(Article.FLD_PRICE));
				articleBean.setArticleImage1(rs.getString(Article.FLD_IMAGE1));
				articleBean.setArticleImage2(rs.getString(Article.FLD_IMAGE2));
				articleBean.setArticleImage3(rs.getString(Article.FLD_IMAGE3));
				articleBean.setBarcode(utils.BarcodeGenerator.generateBarcode(String.valueOf(rs.getInt( Article.FLD_ARTICLENO ))));
				lstArticles.add(articleBean);
				
			}
		}
		return lstArticles;
	}

	/**
	 * 
	 * @param articleBean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public String getArticleStatus(ArticleBean articleBean)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		String articleStatus = null;
		try(ResultSet rs = clsArticle.getRecordsByArticleNo(articleBean.getArticleNo()))
		{
			if(rs.next())
			{
				articleStatus = rs.getString(Article.FLD_STATUS);
			}
		}
		return articleStatus;
	}
	/**
	 * 
	 * @param articleNo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public ArticleBean getArticleByArticleNo(int articleNo)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		ArticleBean articleBean = null;
		try(ResultSet rs = clsArticle.getRecordsByArticleNo(articleNo))
		{
			if(rs.next())
			{
				articleBean = new ArticleBean();
				articleBean.setArticleNo(articleNo);
				articleBean.setArticleCategory(rs.getInt(Article.FLD_CATEGORYNO));
				articleBean.setArticleModel(rs.getString(Article.FLD_MODEL));
				articleBean.setArticleDescription(rs.getString(Article.FLD_DESCRIPTION));
				articleBean.setArticleOldNew(rs.getString(Article.FLD_OLD_NEW));
				articleBean.setArticlePrice(rs.getInt(Article.FLD_PRICE));
				articleBean.setArticleType(rs.getString(Article.FLD_TYPE));
				articleBean.setArticleStatus(rs.getString(Article.FLD_STATUS));
				articleBean.setArticleName(rs.getString(Article.FLD_NAME));
			}
		}
		return articleBean;
	}
	
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public List<ArticleBean> fetchAllArticles()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		List<ArticleBean> lstArticles = new ArrayList<>();

		try (ResultSet rs = clsArticle.getAllNotReservedRecords();) {
			while (rs.next()) {
				ArticleBean articleBean = new ArticleBean();
				articleBean.setArticleNo(rs.getInt( Article.FLD_ARTICLENO ));
				articleBean.setArticleName(rs.getString(Article.FLD_NAME));
				articleBean.setArticleCategory(rs.getInt(Article.FLD_CATEGORYNO));
				articleBean.setArticleDescription(rs.getString(Article.FLD_DESCRIPTION));
				articleBean.setArticleOldNew(rs.getString(Article.FLD_OLD_NEW));
				articleBean.setArticlePrice(rs.getInt(Article.FLD_PRICE));
				articleBean.setArticleImage1(rs.getString(Article.FLD_IMAGE1));
				articleBean.setArticleImage2(rs.getString(Article.FLD_IMAGE2));
				articleBean.setArticleImage3(rs.getString(Article.FLD_IMAGE3));
				lstArticles.add(articleBean);
			}
		}
		return lstArticles;
	}
	
	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public List<ArticleBean> fetchReservedArticles()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		List<ArticleBean> lstArticles = new ArrayList<>();
		
		try (ResultSet rs = clsArticle.getReservedRecords();) {
			while (rs.next()) {
				ArticleBean articleBean = new ArticleBean();
				articleBean.setArticleNo(rs.getInt( Article.FLD_ARTICLENO ));
				articleBean.setArticleName(rs.getString(Article.FLD_NAME));
				articleBean.setArticleCategory(rs.getInt(Article.FLD_CATEGORYNO));
				articleBean.setArticleDescription(rs.getString(Article.FLD_DESCRIPTION));
				articleBean.setArticleOldNew(rs.getString(Article.FLD_OLD_NEW));
				articleBean.setArticlePrice(rs.getInt(Article.FLD_PRICE));
				articleBean.setArticleImage1(rs.getString(Article.FLD_IMAGE1));
				articleBean.setArticleImage2(rs.getString(Article.FLD_IMAGE2));
				articleBean.setArticleImage3(rs.getString(Article.FLD_IMAGE3));
				articleBean.setBarcode(utils.BarcodeGenerator.generateBarcode(String.valueOf(rs.getInt( Article.FLD_ARTICLENO ))));
				lstArticles.add(articleBean);
			}
		}
		return lstArticles;
	}
	public int fetchNumberOfArticles()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		int numberOf = 0;
		
		try (ResultSet rs = clsArticle.getNumberOfExitingArticles();) {
			if (rs.next()) {
				numberOf = rs.getInt(1);
			}
		}
		return numberOf;
	}
	public int fetchNumberOfArticlesFromTypeAiles(int categoryNo)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		int numberOf = 0;
		
		try (ResultSet rs = clsArticle.getNumberOfArticlesFromTypeAiles(categoryNo);) {
			if (rs.next()) {
				numberOf = rs.getInt(1);
			}
		}
		return numberOf;
	}

	public List<ArticleBean> fetchFiltredArticles(String categroy, String manufacturer, String model, String type,
			String oldNew) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		List<ArticleBean> lstArticles = new ArrayList<>();

		try (ResultSet rs = clsArticle.getfiltredRecords(categroy, manufacturer, model, type, oldNew);) {
			while (rs.next()) {
				ArticleBean articleBean = new ArticleBean();
				articleBean.setArticleNo(rs.getInt(Article.FLD_ARTICLENO));
				articleBean.setArticleName(rs.getString(Article.FLD_NAME));
				articleBean.setArticleCategory(rs.getInt(Article.FLD_CATEGORYNO));
				articleBean.setArticleDescription(rs.getString(Article.FLD_DESCRIPTION));
				articleBean.setArticleOldNew(rs.getString(Article.FLD_OLD_NEW));
				articleBean.setArticlePrice(rs.getInt(Article.FLD_PRICE));
				articleBean.setArticleImage1(rs.getString(Article.FLD_IMAGE1));
				articleBean.setArticleImage2(rs.getString(Article.FLD_IMAGE2));
				articleBean.setArticleImage3(rs.getString(Article.FLD_IMAGE3));
				lstArticles.add(articleBean);
			}
		}
		return lstArticles;
	}

	public int getUserNo() {
		int userNo = 0;
		try {
			ResultSet rs = clsUser.getRecord(userBean);
			if (rs.next()) {
				userNo = rs.getInt(User.FLD_USERNO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return userNo;
	}

}
