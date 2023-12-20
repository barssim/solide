package article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.ArticleFacade;
import models.ArticleBean;
import utils.SolideConstants;

public class FiltredArticlesServlet extends HttpServlet {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ArticleFacade articleFacade = null;
		List<ArticleBean> myArticlesList = new ArrayList<>();
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
		try {
			articleFacade = new ArticleFacade(null, null);
			String categroy = request.getParameter("category");
			String manufacturer = request.getParameter("constructeur");
			String model = request.getParameter("model");
			String type = request.getParameter("type");
			String oldNew = request.getParameter("oldNew");
			myArticlesList = articleFacade.fetchFiltredArticles(categroy,manufacturer, model, type, oldNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(myArticlesList.isEmpty())
		{
			request.setAttribute("Message", SolideConstants.NO_ARTICLE);
			getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
		}
		else
		{
		request.setAttribute("myArticlesList", myArticlesList);
		getServletContext().getRequestDispatcher("/allArticles.jsp").forward(request, response);
		}
	}
	}



