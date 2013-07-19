package com.project.servlets;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import com.project.dao.DAOConfigurationException;
import com.project.xmlgen.DomParser;
import com.project.xmlgen.XmlValidator;

/**
 * Servlet implementation class Main
 */
@WebServlet("/XMLGen")
public class XMLGen extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
	ArrayList<String> liste = new ArrayList<String>();
    public XMLGen() {
    	super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String param = request.getParameter( "table" );
		/* Cr�ation du bean */
		Tables t = new Tables();
			t.setSelectedtable(param);
		/* Stockage du message et du bean dans l'objet request */
		request.setAttribute( "tables", t );
		
		String paramchamp = request.getParameter( "champ" );
		/* Cr�ation du bean */
			t.setSelectedchamp(paramchamp);
		/* Stockage du message et du bean dans l'objet request */
		request.setAttribute( "champs", t );
		
		String paramcondition = request.getParameter( "condition" );
		/* Cr�ation du bean */
			t.setSelectedcondition(paramcondition);
		/* Stockage du message et du bean dans l'objet request */
		request.setAttribute( "condition", t );
		
		if(param!=null)
		{
			this.getServletContext().getRequestDispatcher( "/WEB-INF/ComboJsp.jsp" ).forward( request, response );
		}
		else
		{
			this.getServletContext().getRequestDispatcher( "/WEB-INF/XMLGen.jsp" ).forward( request, response );
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String xml = request.getParameter( "xml" );
		String condition = request.getParameter( "where" );
		XmlValidator xv = new XmlValidator(xml);
		xv.setPath(getServletContext().getRealPath("/"));
		int resultnumber = 0;
		try {
			resultnumber = xv.getData(condition);
		} catch (DAOConfigurationException e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		}

		
		if(resultnumber>1) 
		{
 			String filepath=xv.getFilePath();
			 File fi = new File(filepath);
             response.setContentType("application/octet-stream" );
             response.setHeader("Content-Disposition","attachment; filename="+fi.getName()+";" );
             response.setContentLength ((int)fi.length());
             
             ServletOutputStream out1 = response.getOutputStream();
             BufferedInputStream fif= new BufferedInputStream(new FileInputStream(fi));
             int data;
             while((data = fif.read())!=-1) {
             out1.write(data);
             }
             
             fif.close();
             out1.close();
		}
		else
		{
			if(resultnumber==0)
			{
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('Aucun resultat ne r�pond � cette condition ')</script>");
				out.close();
				this.getServletContext().getRequestDispatcher( "/WEB-INF/XMLGen.jsp" ).forward( request, response );
			}
			else
			{
				 String filepath=xv.getFilePath();
				 DomParser dp = new DomParser();
				 if(dp.Parse(filepath))
				 {
					 File fi = new File(filepath);
		             response.setContentType("application/octet-stream" );
		             response.setHeader("Content-Disposition","attachment; filename="+fi.getName()+";" );
		             response.setContentLength ((int)fi.length());
		             ServletOutputStream out = response.getOutputStream();
		             
		             BufferedInputStream fif= new BufferedInputStream(new FileInputStream(fi));
		             int data;
		             while((data = fif.read())!=-1) {
		             out.write(data);
		             }
		             fif.close();
		             out.close();
		             this.getServletContext().getRequestDispatcher( "/WEB-INF/XMLGen.jsp" ).forward( request, response );
				 }
				 else
				 {
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.println("<script>alert('Document XML mal form� ! : "+filepath+" ')</script>");
						out.close();
						this.getServletContext().getRequestDispatcher( "/WEB-INF/XMLGen.jsp" ).forward( request, response );
				 }
	         }
		}
		
		
	}
}

