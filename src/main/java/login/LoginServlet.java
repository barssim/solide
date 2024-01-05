package login;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import article.ArticleFacade;
import models.UserBean;
import utils.SolideConstants;

/**
 * Servlet implementation class Login
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	String resultMessage = null;
	List<String> myArticlesList = null;
	ArticleFacade articleFacade = null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			username = request.getParameter("username");
			password = request.getParameter("password");
			UserBean userBean = new UserBean();
			userBean.setUserName(username);
			userBean.setUserpassword(password);
			LoginFacade loginFacade = new LoginFacade(userBean);
			boolean userExist = loginFacade.checkLoginInputUsername();
			String surname = loginFacade.checkLoginInputPassword();
			if (userExist && !surname.equals("noBody")) {
				request.getSession().setAttribute("loggedInUser", username);
				resultMessage = SolideConstants.WELCOMEON_SITE + "  " + surname + "  " + username;
				request.setAttribute("Message", resultMessage);
				if (loginFacade.retrievRole().equals(SolideConstants.DEPOT_MANAGER)) {

					getServletContext().getRequestDispatcher("/DepotManager.jsp").forward(request, response);
				} else {
					if (request.getSession().getAttribute("articleNo") != null) {
						resultMessage = SolideConstants.RECOMMANDER;
						request.setAttribute("Message", resultMessage);
						getServletContext().getRequestDispatcher("/mySolide.jsp").forward(request, response);
					} else {
						getServletContext().getRequestDispatcher("/mySolide.jsp").forward(request, response);
					}
				}
				return;
			} else {
				if (!userExist) {
					resultMessage = SolideConstants.USERNAME_INCORRECT;
				} else if (surname.equals("noBody")) {
					resultMessage = SolideConstants.PASSWORD_INCORRECT;
				}
				request.setAttribute("Message", resultMessage);
				getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
