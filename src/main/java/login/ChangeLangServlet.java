package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChangeLangServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  

		try {
    
		String actLang = request.getParameter("lang"); 

		request.getSession().putValue("actLang", actLang);
		response.setIntHeader("Refresh", 5);
		getServletContext().getRequestDispatcher("/index.jsp?language="+actLang).forward(request, response);
		
	
} catch (Exception e) {

e.printStackTrace();
}
}
}
