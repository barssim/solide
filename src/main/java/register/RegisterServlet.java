package register;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import login.LoginFacade;
import messenger.EmailMessenger;
import models.ContactBean;
import models.LocationBean;
import models.UserBean;
import utils.CodeManager;
import utils.SolideConstants;

/**
 * A servlet that takes message details from user and send it as a new e-mail
 * through an SMTP server.
 *
 * @author www.codejava.net
 *
 */

public class RegisterServlet extends HttpServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	String fromName;
	String fromAddress;
	private String resultMessage;
	private String messageToSend;
	public static UserBean userBean;
	public static ContactBean contactBean;
	public static LocationBean locationBean;
	public static int generatedCode;
	EmailMessenger emailMessenger;
	Logger logLogger = Logger.getLogger("GLSLogger");
	String subject = "Code d'Enregistrement";

	public void init() {
		ServletContext context = getServletContext();
		fromName = context.getInitParameter("fromName");
		fromAddress = context.getInitParameter("fromAddress");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultedMessage = null;
		// reads form fields

		// build new user
		userBean = new UserBean();
		contactBean = new ContactBean();
		locationBean = new LocationBean();
		userBean.setSurname(request.getParameter("civilite"));
		userBean.setUserName(request.getParameter("pseudoName"));
		userBean.setUserpassword(request.getParameter("password"));
		contactBean.setEmail(request.getParameter("email"));
		contactBean.setMobile(request.getParameter("MobileNo"));

		try {
			LoginFacade loginFacade = new LoginFacade(userBean);
			if (loginFacade.checkLoginInputUsername()) {
				resultMessage = SolideConstants.USER_ALREADY_EXIST;
				request.setAttribute("Message", resultMessage);
				getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
			    return;
			} else {
//				generatedCode = CodeManager.generateCode();		
//				logLogger.info("GeneratedCode" + generatedCode);
//				messageToSend = SolideConstants.MESSAGE_TO_SEND + " " + generatedCode;
//				resultMessage = SolideConstants.CODE_MESSAGE;
//				emailMessenger = new EmailMessenger(messageToSend, userBean.getUserName(), contactBean.getEmail(),subject);
//				emailMessenger.sendEmail();	
//				request.setAttribute("Message", resultMessage);
//				getServletContext().getRequestDispatcher("/register_code.jsp").forward(request, response);
				
				UserBean uB = RegisterServlet.userBean;
				LocationBean lB = RegisterServlet.locationBean;
				ContactBean cB = RegisterServlet.contactBean;			
				lB.setLocationName("loc_" + uB.getUserName());
				cB.setContactName("cont_" + uB.getUserName());
				RegisterFacade registerFacade =	new RegisterFacade(uB, cB, lB);
				if (registerFacade.insertNewUSer() != 0) {
					resultedMessage = SolideConstants.THANKS_MESSAGE;
				} else {
					resultedMessage = SolideConstants.REGISTRATION_FAILED;
				}
				request.setAttribute("Message", resultedMessage);
				getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
			    return;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logLogger.error(resultMessage);
		}
	}

}