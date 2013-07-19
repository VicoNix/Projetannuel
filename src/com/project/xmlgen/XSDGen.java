package com.project.xmlgen;

import java.io.File;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

public class XSDGen {

	private HashMap<Integer,String> hm = new HashMap<Integer,String>();
	
	private ArrayList<Integer> types = new ArrayList<Integer>();
	private ArrayList<String> colnames = new ArrayList<String>();
	private String table;
	
	public ArrayList<Integer> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<Integer> types) {
		this.types = types;
	}
	
	public void setColnames(ArrayList<String> colnames) {
		this.colnames=colnames;
	}
	
	public void setTable(String from) {
		this.table=from;
		
	}
	
	public void XSDGeneration(String path) throws ParserConfigurationException
	{
		//Mapping JDBC XSD
		
		hm.put(java.sql.Types.BIGINT,"xs:long");
		hm.put(java.sql.Types.BINARY ,	"xs:base64Binary");
		hm.put(java.sql.Types.BIT, 	"xs:short");
		hm.put(java.sql.Types.BLOB, 	"xs:base64Binary");
		hm.put(java.sql.Types.BOOLEAN 	,"xs:boolean");
		hm.put(java.sql.Types.CHAR 	,"xs:string");
		hm.put(java.sql.Types.CLOB 	,"xs:string");
		hm.put(java.sql.Types.DATALINK 	,"xs:anyURI");
		hm.put(java.sql.Types.DATE 	,"xs:date");
		hm.put(java.sql.Types.DECIMAL 	,"xs:decimal");
		hm.put(java.sql.Types.DOUBLE 	,"xs:double");
		hm.put(java.sql.Types.FLOAT 	,"xs:float");
		hm.put(java.sql.Types.INTEGER 	,"xs:int");
		hm.put(java.sql.Types.LONGVARBINARY 	,"xs:base64Binary");
		hm.put(java.sql.Types.LONGVARCHAR 	,"xs:string");
		hm.put(java.sql.Types.NUMERIC 	,"xs:decimal");
		hm.put(java.sql.Types.REAL 	,"xs:float");
		hm.put(java.sql.Types.SMALLINT 	,"xs:short");
		hm.put(java.sql.Types.TIMESTAMP 	,"xs:time");
		hm.put(java.sql.Types.TIMESTAMP 	,"xs:dateTime");
		hm.put(java.sql.Types.TINYINT 	,"xs:short");
		hm.put(java.sql.Types.VARBINARY 	,"xs:base64Binary");
		hm.put(java.sql.Types.VARCHAR 	,"xs:string");
		hm.put(java.sql.Types.ARRAY 	,"xs:string");
		hm.put(java.sql.Types.DISTINCT 	,"xs:string");
		hm.put(java.sql.Types.JAVA_OBJECT 	,"xs:string");
		hm.put(java.sql.Types.NULL 	,"xs:string");
		hm.put(java.sql.Types.OTHER 	,"xs:string");
		hm.put(java.sql.Types.REF 	,"xs:string");
		hm.put(java.sql.Types.STRUCT 	,"xs:string");
		hm.put(100 	,"xs:base64Binary");
		hm.put(2009 	,"xs:anyType");
		//xsd:string
		
		
		/*
<xs:schema attributeFormDefault="unqualified" elementFormDefault="qualified" xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="Result">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="LIEN_VEHICULE_POTENTIEL_VIEW" maxOccurs="unbounded" minOccurs="0">
          <xs:complexType>
            <xs:sequence>
              <xs:element type="xs:int" name="IMMATRICULATION"/>
              <xs:element type="xs:byte" name="VALEUR"/>
              <xs:element type="xs:string" name="NOM_POTENTIEL"/>
              <xs:element type="xs:string" name="DATE_RELEVE"/>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
      <xs:attribute type="xs:string" name="query"/>
    </xs:complexType>
  </xs:element>
</xs:schema>
		*/
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element schema = doc.createElement("xs:schema");
        schema.setAttribute("attributeFormDefault", "unqualified");
        schema.setAttribute("elementFormDefault", "qualified");
        schema.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
        doc.appendChild(schema);
  
        Element resultelement = doc.createElement("xs:element");
        resultelement.setAttribute("name", "Result");
        schema.appendChild(resultelement);
        
        Element complextype = doc.createElement("xs:complexType");
        resultelement.appendChild(complextype);
        
        
        Element sequence = doc.createElement("xs:sequence");
        complextype.appendChild(sequence);
        
        
        Element attribute = doc.createElement("xs:attribute");
        attribute.setAttribute("type", "xs:string");
        attribute.setAttribute("name", "query");
        complextype.appendChild(attribute);
        
        Element viewelement = doc.createElement("xs:element");
        viewelement.setAttribute("name", table);
        viewelement.setAttribute("maxOccurs","unbounded");
        viewelement.setAttribute("minOccurs", "0");
        sequence.appendChild(viewelement);
		
        Element complextype1 = doc.createElement("xs:complexType");
        viewelement.appendChild(complextype1);
        
        Element sequence1 = doc.createElement("xs:sequence");
        complextype1.appendChild(sequence1);
        
        for(int i=0;i<colnames.size();i++)
        {
        	Element element = doc.createElement("xs:element");
            
    		if(hm.containsKey(types.get(i))){
    			element.setAttribute("type", hm.get(types.get(i)));
    		}
    		else
    		{
    			element.setAttribute("type", "xs:string");
    		}
    		element.setAttribute("name", colnames.get(i));
    		sequence1.appendChild(element);
        }

        
        String txtDate=new SimpleDateFormat("YYYYMMDDhhmmss", Locale.FRANCE).format(new Date());
		String adressedufichier = path + "\\resultat"+txtDate+".xsd";
		
		Transformer transformer;
		try { 
			
			transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(adressedufichier));
	    	Source input = new DOMSource(doc);
			transformer.transform(input, output);
		} catch (TransformerConfigurationException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Bloc catch généré automatiquement 
			e.printStackTrace();
		}
	}




	
}
