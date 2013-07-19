package com.project.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
 * Servlet implementation class Navigation
 */
@WebServlet("/Navigation")
public class Navigation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Navigation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Navigation.jsp" ).forward( request, response );
		
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String xmlajax=request.getParameter("xmlajax");
		String xslajax=request.getParameter("xslajax");
		String temphtmlfile = "temp";

		String path=request.getParameter("file");

		if(path.isEmpty())
		{
			path=temphtmlfile;
			FileWriter fileWriter = null;
	        try {
	            File newTextFile = new File(path+".xml");
	            fileWriter = new FileWriter(newTextFile);
	            fileWriter.write(xmlajax);
	            fileWriter.close();
	        } catch (IOException ex) {
	            
	        } finally {
	            try {
	                fileWriter.close();
	            } catch (IOException ex) {
	                	            }
	        }

	        try {
	            File newTextFile = new File(path+".xsl");
	            fileWriter = new FileWriter(newTextFile);
	            fileWriter.write(xslajax);
	            fileWriter.close();
	        } catch (IOException ex) {
	            
	        } finally {
	            try {
	                fileWriter.close();
	            } catch (IOException ex) {
	                	            }
	        }
	        path+=".xml";
	        System.out.println("path="+path);
		}
		
		if(path.substring(path.lastIndexOf('.')).equals(".xml")||path.substring(path.lastIndexOf('.')).equals(".xsd")||path.substring(path.lastIndexOf('.')).equals(".xsl")||path.substring(path.lastIndexOf('.')).equals(".html"))
		{
		Scanner scanner;
		try {
			scanner = new Scanner(new FileReader(path.substring(0, path.lastIndexOf('.'))+".xml"));
			 String xml = "";
			 while (scanner.hasNextLine()) {
			     xml += '\n'+scanner.nextLine();
			 }
			request.setAttribute("filecontentxml", xml);
		} catch (FileNotFoundException e) {
			request.setAttribute("filecontentxml", null);
		}

		try{
		scanner = new Scanner(new FileReader(path.substring(0, path.lastIndexOf('.'))+".xsl"));
		 String xsl = "";
		 while (scanner.hasNextLine()) {
		     xsl += '\n'+scanner.nextLine();
		 }
		request.setAttribute("filecontentxsl", xsl);
		} catch (FileNotFoundException e) {
			request.setAttribute("filecontentxsl", null);
		}

		//HTML Temporaire suite à actualisation
		if(xmlajax.contains("xml") && xslajax.contains("xml"))
		{
			
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
		Transformer transformer = tFactory.newTransformer(new StreamSource(path.substring(0, path.lastIndexOf('.'))+".xsl"));
			transformer.transform(new StreamSource(path.substring(0, path.lastIndexOf('.'))+".xml"), new StreamResult(temphtmlfile+".html"));
		} catch (TransformerException e1) {
			// TODO Bloc catch g�n�r� automatiquement
			e1.printStackTrace();
		}
		request.setAttribute("filecontenthtml", temphtmlfile+".html");

		}
		else
		{
			//HTML final
			TransformerFactory tFactory = TransformerFactory.newInstance();
			try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(path.substring(0, path.lastIndexOf('.'))+".xsl"));
				transformer.transform(new StreamSource(path.substring(0, path.lastIndexOf('.'))+".xml"), new StreamResult(path.substring(0, path.lastIndexOf('.'))+".html"));
			} catch (TransformerException e1) {
				// TODO Bloc catch g�n�r� automatiquement
				e1.printStackTrace();
			}
			request.setAttribute("filecontenthtml", request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/"))+path.substring(path.lastIndexOf('/'), path.lastIndexOf('.'))+".html");

		}
		try{
		scanner = new Scanner(new FileReader(path.substring(0, path.lastIndexOf('.'))+".xsd"));
		 String xsd = "";
		 while (scanner.hasNextLine()) {
		     xsd += '\n'+scanner.nextLine();
		 }
		request.setAttribute("filecontentxsd", xsd);
		}
		 catch (FileNotFoundException e) {
			request.setAttribute("filecontentxsd", null);
		}
		}
		else
		{
			request.setAttribute("filecontentxml", null);
			request.setAttribute("filecontentxsl", null);
			request.setAttribute("filecontentxsd", null);
			request.setAttribute("filecontenthtml", null);
		}
		try {
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Navigation.jsp" ).forward( request, response );
		} catch (IOException e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		}
		
	}
}
