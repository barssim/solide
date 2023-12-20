package article;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ArticleBean;

public class SelectedArticleServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<ArticleBean> myArticlesList = null;
	ArticleBean articleBean = new ArticleBean();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		try {
			articleBean.setArticleName(request.getParameter("myTr"));
			myArticlesList.add(articleBean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("myArticlesList", myArticlesList);
		getServletContext().getRequestDispatcher("/myArticles.jsp").forward(request, response);

	}
}
