package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.SolideConstants;

public class LogOutServlet extends HttpServlet {
	String resultMessage = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

		try {
    
//		request.getRequestDispatcher("link.html").include(request, response);  

		request.getSession().invalidate(); 
		resultMessage = SolideConstants.SUCCESSFULLY_LOGGED_OUT;
	
	request.setAttribute("Message", resultMessage);
	getServletContext().getRequestDispatcher("/MessageResult.jsp").forward(request, response);
	return;
} catch (Exception e) {

e.printStackTrace();
}
}
}
