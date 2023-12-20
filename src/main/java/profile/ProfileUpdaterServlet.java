package profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import messenger.EmailMessenger;
import models.ContactBean;
import models.UserBean;
import utils.SolideConstants;

/**
 * A servlet that takes message details from user and send it as a new e-mail
 * through an SMTP server.
 *
 * @author www.codejava.net
 *
 */

public class ProfileUpdaterServlet extends HttpServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	String fromName;
	String fromAddress;
	private String resultMessage;
	private UserBean userBean;
	private ContactBean contactBean;
	private ProfileUpdatFacade profileUpdatFacade;
	Logger logLogger = Logger.getLogger("GLSLogger");
	EmailMessenger emailMessenger;

	public void init() {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// reads form fields
		contactBean = new ContactBean();
		userBean = new UserBean();
		contactBean.setEmail(request.getParameter("email"));
		contactBean.setMobile(request.getParameter("MobileNo"));
		userBean.setUserName(request.getSession().getAttribute("loggedInUser").toString());
		userBean.setUserpassword(request.getParameter("password"));
		profileUpdatFacade = new ProfileUpdatFacade(userBean, contactBean);
		try {
			profileUpdatFacade.updateProfile();
			resultMessage = SolideConstants.SUCCESSFULL_PROFILE_UPDATE;
		} catch (Exception ex) {
			resultMessage = SolideConstants.UNSUCCESSFULL_PROFILE_UPDATE;
			ex.printStackTrace();
			logLogger.error(resultMessage);
		}
		finally{
		request.setAttribute("Message", resultMessage);
		getServletContext().getRequestDispatcher("/MessageResultWhenConnected.jsp").forward(request, response);
		}
	}

}