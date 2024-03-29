package article;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ArticleBean;
import models.UserBean;

public class MyArticlesServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserBean userBean;
	ArticleFacade articleFacade = null;
	List<ArticleBean> myArticlesList = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		userBean = new UserBean();
		userBean.setUserName(request.getSession().getAttribute("loggedInUser").toString());

		try {
			articleFacade = new ArticleFacade(null, userBean);
			myArticlesList = articleFacade.fetchMyArticles();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("myArticlesList", myArticlesList);
		getServletContext().getRequestDispatcher("/myArticles.jsp").forward(request, response);

	}
}
