package com.project.servlets;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Servlet implementation class FormViewer
 */
@WebServlet("/FormViewer")
public class FormViewer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormViewer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("on entre dans le doGet");
		request.setAttribute("filecontentxml", "");
		request.setAttribute("filecontenthtml", "");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/FormViewer.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("on entre dans le doPost");
		String path=request.getParameter("file");
		
		//Cas ou on a pas encore répondu au formulaire
		if(path!=null){
			System.out.println("Path vaut "+path);
			if(path.substring(path.lastIndexOf('.')).equals(".html"))
			{
				Scanner scanner;
				try {
					scanner = new Scanner(new FileReader(path.substring(0, path.lastIndexOf('.'))+".xml"));
					String xml = "";
					while (scanner.hasNextLine()) {
						xml += '\n'+scanner.nextLine();
					}
					//System.out.println(xml);
					
					request.setAttribute("filecontentxml", xml);
				} catch (FileNotFoundException e) {
					request.setAttribute("filecontentxml", "");
				}
				
				
				String html_file = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"))+path.substring(path.lastIndexOf('/'), path.lastIndexOf('.'))+".html";
				
				try {
					scanner = new Scanner(new FileReader(path));
					String html = "";
					String ligne_courante = "";
					int compteur_id = 1;
					while (scanner.hasNextLine()) {
						ligne_courante = scanner.nextLine();
						//System.out.println(ligne_courante);
						
						ligne_courante = ligne_courante.replace("<form", "<form method=\"post\" action=\"FormViewer\"");
						if(ligne_courante.indexOf("value=")>0 && ligne_courante.indexOf("submit")<0){
							ligne_courante = ligne_courante.replace(" value=", " name=\"element"+compteur_id+"\" value=");
							compteur_id++;
						}
						if(ligne_courante.indexOf("</form>")>=0){
							//html += '\n'+"<input type=\"hidden\" name=\"file\" value=\""+path+"\">";
						}
						
						//System.out.println(ligne_courante);
						html += '\n'+ligne_courante;
						
					}
					request.setAttribute("file", path);
					System.out.println(html);
					request.setAttribute("filecontenthtml", html);
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
					request.setAttribute("filecontenthtml", "");
				}

				
				System.out.println();
				
				//request.setAttribute("filecontenthtml", html_file);

				
			}
			else
			{
				request.setAttribute("filecontentxml", "");
				request.setAttribute("filecontenthtml", "");
			}
			try {
				this.getServletContext().getRequestDispatcher( "/WEB-INF/FormViewer.jsp" ).forward( request, response );
			} catch (IOException e) {
				// TODO Bloc catch g�n�r� automatiquement
				e.printStackTrace();
			}
		}
		
		else{
			ArrayList<String>parametres = new ArrayList<String>();
			int compteur_parametres = 1;
			while(true){
				String parametre = request.getParameter("element"+compteur_parametres);
				if(parametre==null)break;
				else parametres.add(parametre);
				compteur_parametres++;
			}
			for(String param : parametres)System.out.println(param);
			request.setAttribute("filecontenthtml", "");
			this.getServletContext().getRequestDispatcher( "/WEB-INF/FormViewer.jsp" ).forward( request, response );
		}
		
	}

}
