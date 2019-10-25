package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;
import javaBeans.BOperations;

/**http://localhost:8080/Compte/gestionOperations?NoDeCompte=12A5
 * Servlet implementation class SOperations
 */
@WebServlet("/SOperations")
public class SOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SOperations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request : http://localhost:8080/Compte/GestionOperations?NoDeCompte=12A5
		var query = request.getQueryString();
		
		if(query == null) {
			getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
		}
		
		var NoDeCompte = query.split("=")[1];
		response.setStatus(200); 
		PrintWriter out = response.getWriter();
	    out.println( "<HTML>" );
	    out.println( "<HEAD>");
	    out.println( "<TITLE>"+NoDeCompte+"</TITLE>" );
	    out.println( "</HEAD>" );
	    out.println( "<BODY>" );
	    out.println( "<H1>"+NoDeCompte+"</H1>" );
	    out.println( "</BODY>" );
	    out.println( "</HTML>" );
	    out.close();
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		var query = request.getQueryString();
		
		if(query == null) {
			//TODO Ameliorer gestion des erreurs
			PrintWriter out = response.getWriter();
		    out.println( "<HTML>" );
		    out.println( "<HEAD>");
		    out.println( "<TITLE>ERROR</TITLE>" );
		    out.println( "</HEAD>" );
		    out.println( "<BODY>" );
		    out.println( "<H1> No argument</H1>" );
		    out.println( "</BODY>" );
		    out.println( "</HTML>" );
		    out.close();
			return;
		}
		
		var parameters = request.getQueryString().split("[=,&]");
		if(parameters.length < 4) {
			//TODO Ameliorer gestion des erreurs
			PrintWriter out = response.getWriter();
		    out.println( "<HTML>" );
		    out.println( "<HEAD>");
		    out.println( "<TITLE>ERROR</TITLE>" );
		    out.println( "</HEAD>" );
		    out.println( "<BODY>" );
		    out.println( "<H1> No argument</H1>" );
		    out.println( "</BODY>" );
		    out.println( "</HTML>" );
		    out.close();
			return;
		}
		
		
		doGet(request, response);
		var op = new BOperations("","","",BigDecimal.ONE);
		op.setNoDeCompte(NoDeCompte);
		try {
			op.ouvrirConnexion();
			op.consulter();
			/*******Process the response***********/
			response.setStatus(200); 
			PrintWriter out = response.getWriter();
		    out.println( "<HTML>" );
		    out.println( "<HEAD>");
		    out.println( "<TITLE>"+NoDeCompte+"</TITLE>" );
		    out.println( "</HEAD>" );
		    out.println( "<BODY>" );
		    out.println( "<H1>"+op+"</H1>" );
		    out.println( "</BODY>" );
		    out.println( "</HTML>" );
		    out.close();
			/**************************************/
			op.fermerConnexion();
		}catch(TraitementException te) {
			System.err.println(MessagesDErreurs.getMessageDErreur(te.getMessage()));
		}
	}

}
