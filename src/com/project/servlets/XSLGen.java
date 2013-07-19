package com.project.servlets;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class XSLGen
 */
@WebServlet("/XSLGen")
public class XSLGen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XSLGen() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		//request.getInputStream();
		this.getServletContext().getRequestDispatcher( "/WEB-INF/XSLGen.jsp" ).forward( request, response );
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		byte[] buffer = new byte[512];
		OutputStream os = response.getOutputStream();
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = null;
		String query, data;
		int readBytes = 0;
		
		while (-1 != (readBytes = is.read(buffer)))
		{
			baos.write(buffer, 0, readBytes);
		}
		
		query = new String(baos.toByteArray());
		
		baos.close();
		
		if (query.startsWith("file|"))
		{
			query = query.substring(5);
			
			fis = new FileInputStream(query);
			baos = new ByteArrayOutputStream();
			buffer = new byte[512];
			
			while (-1 != (readBytes = fis.read(buffer)))
			{
				baos.write(buffer, 0, readBytes);
			}
		
			fis.close();
			
			data = new String(baos.toByteArray());
			
			System.out.println(data);
			
			os.write(data.getBytes());
		}
	}
}