package register;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ContactBean;
import models.LocationBean;
import models.UserBean;
import utils.CodeManager;
import utils.SolideConstants;

/**
 * Servlet implementation class RegisterResult
 */
public class RegisterResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String registerCode;
	String strAttempt = null;
	static int iAttempt = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String resultedMessage = null;

		registerCode = request.getParameter("registerCode");

		try {
			UserBean uB = RegisterServlet.userBean;
			LocationBean lB = RegisterServlet.locationBean;
			ContactBean cB = RegisterServlet.contactBean;			
			lB.setLocationName("loc_" + uB.getUserName());
			cB.setContactName("cont_" + uB.getUserName());
			RegisterFacade registerFacade =	new RegisterFacade(uB, cB, lB);
			registerFacade.setRegisterCode(registerCode);
		  
		
//			if (new CodeManager().checkCode(Integer.parseInt(registerCode), RegisterServlet.generatedCode)) {
				if (registerFacade.insertNewUSer() != 0) {
					resultedMessage = SolideConstants.THANKS_MESSAGE;
				} else {
					resultedMessage = SolideConstants.REGISTRATION_FAILED;
				}
				request.setAttribute("Message", resultedMessage);
				getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
			    return;
//			} else {
//				if (iAttempt < 3) {
//					iAttempt++;
//					resultedMessage = SolideConstants.CODE_INCORRECT;
//					request.setAttribute("Message", resultedMessage);
//					getServletContext().getRequestDispatcher("/register_code.jsp").forward(request, response);
//
//				} else {
//					resultedMessage = SolideConstants.MORE_ATTEMP_TO_REGISTE;
//					request.setAttribute("Message", resultedMessage);
//					getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
//                    return;
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
