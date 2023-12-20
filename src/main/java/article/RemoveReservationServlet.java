package article;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import models.ArticleBean;
import models.UserBean;
import utils.SolideConstants;

public class RemoveReservationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resultMessage;
	private UserBean userBean;
	private ArticleBean articleBean;
	ArticleFacade articleFacade = null;
	Logger logLogger = Logger.getLogger("GLSLogger");
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		userBean = new UserBean();
		articleBean = new ArticleBean();
		userBean.setUserName(request.getSession().getAttribute("loggedInUser").toString());
	
	try {
		articleBean.setArticleNo(Integer.parseInt(request.getSession().getAttribute("articleNo").toString()));
		articleFacade = new ArticleFacade(articleBean, userBean);
		int reservationRemoved = articleFacade.changeArticleReservation(articleBean, articleFacade.getUserNo(), null);
		if(reservationRemoved == 1)
		{
			resultMessage = SolideConstants.SUCCESSFULL_RESERVATION_REMOVE;
		}
	} catch (Exception e) {
		resultMessage = SolideConstants.UNSUCCESSFULL_RESERVATION_REMOVE;		
		logLogger.error(resultMessage);
		e.printStackTrace();
	}
	
	finally{
		request.setAttribute("Message", resultMessage);
		getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
		}
	
}
}
