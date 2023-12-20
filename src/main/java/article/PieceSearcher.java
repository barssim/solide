package article;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PieceSearcher extends HttpServlet {
	private static final long serialVersionUID = 1L;	
       
	
	String constructeur = null;
	String modele = null;
	String type = null;
	String category = null;
	
    public PieceSearcher() {
        super();       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		constructeur = request.getParameter("constructeur");
		modele = request.getParameter("modele");
		type = request.getParameter("type");
		category = request.getParameter("category");
		
		if(constructeur.isEmpty())
		{
			response.getWriter().append("Entrer svp. un constructeur");
			return;
		}
		if(modele.isEmpty())
		{
			response.getWriter().append("Entrer svp. un modele");
			return;
		}
		if(type.isEmpty())
		{
			response.getWriter().append("Entrer svp. un type");
			return;
		}
		if(category.isEmpty())
		{
			response.getWriter().append("Entrer svp. un category");
			return;
		}				
		
			response.getWriter().append("Vous chercher un "+ category + " de " + constructeur + " " + modele + " " + type);		
	    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
