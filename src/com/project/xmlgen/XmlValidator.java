package com.project.xmlgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.project.dao.DAOConfigurationException;
import com.project.dao.DAOFactory;

public class XmlValidator {
	private Statement state;
	private String xml;
	private String path;


	private ArrayList<String> tables = new ArrayList<String>();
	private ArrayList<String> champs = new ArrayList<String>();
	private ArrayList<String> resultats = new ArrayList<String>();
	private ArrayList<String> from = new ArrayList<String>();
	private Document doc;
	
	public XmlValidator(String xml) {
		this.xml=xml;
	}
	
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	public int getData(String condition) throws DAOConfigurationException, ParserConfigurationException
	{
		int linecount = 0;
		String from = condition.substring(6,condition.indexOf('.'));
		String requete="SELECT "+xml+" FROM "+from+" "+condition;
		ResultSet rs = null;
		try {
			DAOFactory df = DAOFactory.getInstance();
			Connection con = df.getConnection();
			state = con.createStatement();
			
			System.out.println("requete="+requete);
			
			rs = state.executeQuery(requete);
		} catch (SQLException e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();

        Element results = doc.createElement("Result");
        results.setAttribute("query", requete);
        doc.appendChild(results);

        
        ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
		      int colCount = rsmd.getColumnCount();
		      ArrayList<Integer> types = new ArrayList<Integer>();
		      ArrayList<String> colnames = new ArrayList<String>();
		      
		      for (int i = 1; i <= colCount; i++)
	            {

		    	  types.add(rsmd.getColumnType(i));
		    	  colnames.add(rsmd.getColumnName(i));
		    	  System.out.println("colname"+rsmd.getColumnName(i));
	            }
		      
		      XSDGen xsdgen = new XSDGen();
		    xsdgen.setTable(from);
			xsdgen.setTypes(types);
			xsdgen.setColnames(colnames);
			xsdgen.XSDGeneration(path);
			
			System.out.println("creation xsd");
			int j=1;
		        while (rs.next())
		        {
		            Element row = doc.createElement(from);
		            results.appendChild(row);

		            for (int i = 1; i <= colCount; i++)
		            {
		                String columnName = rsmd.getColumnName(i);
		                String strvalue;
		                
		                if(rsmd.getColumnType(i)==java.sql.Types.DATE)
		                {
		                	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		                	Date value = rs.getDate(i);
			                strvalue=dateFormat.format(value);
		                }
		                else
		                {
		                	Object value = rs.getObject(i);
			                strvalue=value.toString();
		                }

		                Element node = doc.createElement(columnName);
		                node.appendChild(doc.createTextNode(strvalue));
		                row.appendChild(node);
		            }
		            
		            linecount++;
		            j++;
		        }
		} catch (SQLException e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		}

        return linecount;
	}

	public String getFilePath() {
		
		String txtDate=new SimpleDateFormat("YYYYMMDDhhmmss", Locale.FRANCE).format(new Date());
		String adressedufichier = path + "//resultat"+txtDate+".xml";
		System.out.println(adressedufichier);
		Transformer transformer;
		try { 
			transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(adressedufichier));
	    	Source input = new DOMSource(doc);
			transformer.transform(input, output);
		} catch (TransformerConfigurationException e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Bloc catch g�n�r� automatiquement
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Bloc catch g�n�r� automatiquement 
			e.printStackTrace();
		}
		
		return adressedufichier;
	}
	
	public static String replace(String originalText,
			 String subStringToFind, String subStringToReplaceWith) {
		int s = 0;
		int e = 0;
		
		StringBuffer newText = new StringBuffer();
		
		while ((e = originalText.indexOf(subStringToFind, s)) >= 0) {
		
		   newText.append(originalText.substring(s, e));
		   newText.append(subStringToReplaceWith);
		   s = e + subStringToFind.length();
		}
		
		newText.append(originalText.substring(s));
		return newText.toString();
	}
	
	 public static boolean isDate(String date) {
		 
		    // some regular expression
		    String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
		        + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time
		 
		    // no check for leap years (Schaltjahr)
		    // and 31.02.2006 will also be correct
		    String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
		    String month = "((1[012])|(0\\d))"; // 01 up to 12
		    String year = "\\d{4}";
		 
		    // define here all date format
		    ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		    patterns.add(Pattern.compile(day + "[-.]" + month + "[-.]" + year + time));
		    patterns.add(Pattern.compile(year + "-" + month + "-" + day + time));
		    // here you can add more date formats if you want
		 
		    // check dates
		    for (Pattern p : patterns)
		      if (p.matcher(date).matches())
		        return true;
		 
		    return false;
		 
		  }
	 
	 public NodeList getNode(String name)
	 {
		 return doc.getElementsByTagName(name);
	 }

}
