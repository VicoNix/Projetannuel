package com.project.servlets;

import java.io.ByteArrayInputStream;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.project.utility.HTMLInputNormalizer;

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
		String query, data, filename;
		int readBytes = 0;
		
		while (-1 != (readBytes = is.read(buffer)))
		{
			baos.write(buffer, 0, readBytes);
		}
		
		query = new String(baos.toByteArray());
		
		baos.close();
		
		System.out.println("Query: "+query);
		
		if (query.startsWith("file|"))
		{
			filename = query.substring(5);
			
			fis = new FileInputStream(filename);
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
		else if (query.startsWith("xslt|"))
		{
			query = query.substring(5);
			
			// retrieve filename
			filename = query.substring(0, query.indexOf('|'));
			data = query.substring(query.indexOf('|')+1, query.length());
			
			System.out.println("filename: "+filename);
			System.out.println("Data: "+data);
			
			baos = new ByteArrayOutputStream();
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
						
			try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(HTMLInputNormalizer.normalize(data).getBytes())));
				transformer.transform(new StreamSource(filename), new StreamResult(baos));
			} catch (TransformerException e1) {
				e1.printStackTrace();
			}
			
			os.write(new String(baos.toByteArray()).substring("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".length()).getBytes());
		}
		else if (query.startsWith("xml_columns|"))
		{
			String xsl = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
					+"<xsl:template match=\"/\">"
					+ "<xsl:for-each select=\"*/*/*\"><option><xsl:attribute name=\"value\">*/*/<xsl:value-of select=\"name()\"></xsl:value-of></xsl:attribute>"
					+ "<xsl:value-of select=\"name()\"></xsl:value-of></option></xsl:for-each>"
					+ "</xsl:template></xsl:stylesheet>";
			
			query = query.substring(11);
			
			
			
			// retrieve filename
			filename = query.substring(query.indexOf('|')+1, query.length());
			
			System.out.println("filename: "+filename);
		//	System.out.println("Data: "+data);
			
			baos = new ByteArrayOutputStream();
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
						
			try {
				System.out.println("XSL: "+xsl);
			Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xsl.getBytes())));
				transformer.transform(new StreamSource(filename), new StreamResult(baos));
				os.write(new String(baos.toByteArray()).substring("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".length()).getBytes());
			} catch (TransformerException e1) {
				e1.printStackTrace();
			}
		}
	}
}