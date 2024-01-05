package article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ArticleBean;
import models.UserBean;
import utils.SolideConstants;

public class ReservationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserBean userBean;
	private ArticleFacade articleFacade;
	private ArticleBean articleBean;
	private String resultMessage = null;
	private static final String RESERVED = "reserved";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		userBean = new UserBean();
		articleBean = new ArticleBean();
		try {
			if (request.getSession().getAttribute("loggedInUser") == null) {
				resultMessage = SolideConstants.PLEASE_LOGIN;
				request.setAttribute("Message", resultMessage);
				getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			}
			else {
				articleBean.setArticleNo(Integer.parseInt(request.getSession().getAttribute("articleNo").toString()));
				userBean.setUserName(request.getSession().getAttribute("loggedInUser").toString());
				articleFacade = new ArticleFacade(null, userBean);
				int userNo = articleFacade.getUserNo();
				if (articleFacade.getArticleStatus(articleBean) == RESERVED) {
					resultMessage = SolideConstants.ARTICLE_IS_ALREADY_RESERVED;
				} else {
					if (articleFacade.changeArticleReservation(articleBean, userNo, RESERVED) == 0) {
						resultMessage = SolideConstants.SUCCESSFULL_ARTICLE_RESERVED;
					} else {
						resultMessage = SolideConstants.UNSUCCESSFULL_ARTICLE_RESERVED;
					}
				}
				request.setAttribute("Message", resultMessage);
				getServletContext().getRequestDispatcher("/MessageResultWhenConnected.jsp").forward(request, response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}


	}
}
