package com.project.formviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.project.dao.DAOConfigurationException;
import com.project.dao.DAOFactory;

public class Parser {

	private String xsl;
	private String xml;
	private String html;
	private String requete_select;
	private ArrayList<String> requetes_insert;
	
	private String table_displayed;

	private ArrayList<String> reponses;


	public Parser() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Parser(String xsl, String xml, String html) {
		super();
		this.xsl = xsl;
		this.xml = xml;
		this.html = html;
	}
	
	
	


	public String getTable_displayed() {
		return table_displayed;
	}


	public void setTable_displayed(String table_displayed) {
		this.table_displayed = table_displayed;
	}


	public String getXsl() {
		return xsl;
	}


	public void setXsl(String xsl) {
		this.xsl = xsl;
	}


	public String getXml() {
		return xml;
	}


	public void setXml(String xml) {
		this.xml = xml;
	}


	public String getHtml() {
		return html;
	}


	public void setHtml(String html) {
		this.html = html;
	}


	public ArrayList<String> getReponses() {
		return reponses;
	}


	public void setReponses(ArrayList<String> reponses) {
		this.reponses = reponses;
	}
	
	public void parse(){
		updateXml();
		formatRequeteInsert();
		executeRequeteInsert();
	}
	
	
	private void executeRequeteInsert(){
		
		for(String requete : requetes_insert){
			try {
				DAOFactory df;
				ResultSet rs = null;
				df = DAOFactory.getInstance();
				Connection con = df.getConnection();
				Statement state = con.createStatement();
				rs = state.executeQuery(requete);
				con.close();


			} catch (DAOConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
			}
		}
		
		
	}
	
	private void formatRequeteInsert(){
		requetes_insert = new ArrayList<String>();
		String champs = requete_select.substring(requete_select.indexOf(" "),requete_select.indexOf("FROM"));
		String view = requete_select.substring(requete_select.indexOf("FROM")+5,requete_select.indexOf("WHERE"));
		view = view.replace(" ","");
		String debut_requete = "INSERT INTO "+view+" ("+champs+") VALUES (";
		
		System.out.println("requete insert : "+debut_requete);
		
		DocumentBuilderFactory builderFactory_xml = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder_xml = null;
		
		
			try {
				builder_xml = builderFactory_xml.newDocumentBuilder();
				Document fichier_xml = builder_xml.parse(new FileInputStream(xml));
				fichier_xml.getDocumentElement().normalize();
				
				Node racine = fichier_xml.getDocumentElement();
				Element element_racine = (Element) racine;
				
				NodeList occurences = fichier_xml.getElementsByTagName(view);
				System.out.println("Il y a "+occurences.getLength()+" éléments");
				table_displayed = "";
				for(int compteur_occurence=0;compteur_occurence<occurences.getLength();compteur_occurence++){
					table_displayed = table_displayed + "<tr>";
					NodeList valeurs = occurences.item(compteur_occurence).getChildNodes();
					String requete = debut_requete;
					for(int compteur_de_valeur=0;compteur_de_valeur<valeurs.getLength();compteur_de_valeur++){
						//Element element = (Element)valeurs.item(compteur_de_valeur);
						table_displayed = table_displayed +"<td>";
						if(valeurs.item(compteur_de_valeur).getNodeType()==Node.ELEMENT_NODE){
							table_displayed = table_displayed + valeurs.item(compteur_de_valeur).getTextContent();
							if(valeurs.item(compteur_de_valeur).toString().contains("DATE")){
								requete = requete + "to_date('"+valeurs.item(compteur_de_valeur).getTextContent()+"','RRRR-MM-DD'),";
							}
							else{
								requete = requete + "'"+valeurs.item(compteur_de_valeur).getTextContent()+"',";
							}
							
							
						}
						table_displayed = table_displayed +"</td>";
						//System.out.println(valeurs.item(compteur_de_valeur).getTextContent());
					}
					requete = requete.substring(0,requete.lastIndexOf("")-1);
					requete = requete +")";
					System.out.println(requete);
					requetes_insert.add(requete);
					
					table_displayed = table_displayed + "</tr>";
				}
				
				
				
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
			
			
		
	}


	private void updateXml(){


		DocumentBuilderFactory builderFactory_xsl = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder_xsl = null;
		
		DocumentBuilderFactory builderFactory_xml = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder_xml = null;
		
		try {
			builder_xsl = builderFactory_xsl.newDocumentBuilder();
			Document fichier_xsl = builder_xsl.parse(new FileInputStream(xsl));
			fichier_xsl.getDocumentElement().normalize();
			
			builder_xml = builderFactory_xml.newDocumentBuilder();
			Document fichier_xml = builder_xml.parse(new FileInputStream(xml));
			fichier_xml.getDocumentElement().normalize();
			
			Node racine = fichier_xml.getDocumentElement();
			Element element_racine = (Element) racine;
			requete_select = element_racine.getAttribute("query");
			System.out.println("La requete select vaut : "+requete_select);
			

			NodeList inputs = fichier_xsl.getDocumentElement().getElementsByTagName("input");
			int compteur_de_reponse = 0;

			for (int temp = 0; temp < inputs.getLength(); temp++) {
				Node node = inputs.item(temp);

				Element element = (Element) node;
				
				if(element.getAttribute("type").toString().equalsIgnoreCase("submit")){

					
				}
				
				else{
					
					boolean isInForEach = false;
					Node pere = node;
					while(pere != fichier_xsl.getDocumentElement()){
						if(pere.getNodeName().contains("for-each")){
							isInForEach = true;
						}
						pere = pere.getParentNode();
					}

					if(isInForEach==true){
						if(element.getAttribute("value-of")!=""){
							
						}
						else{
							NodeList enfants = node.getChildNodes();
							for(int compteur_de_fils = 0;compteur_de_fils<enfants.getLength();compteur_de_fils++){
								Node fils =  enfants.item(compteur_de_fils);
								
								if (fils.getNodeType() == Node.ELEMENT_NODE  && fils.getNodeName().contains("attribute") ) {
									NodeList petits_enfants = fils.getChildNodes();
									for(int compteur_de_petits_fils = 0;compteur_de_petits_fils<petits_enfants.getLength();compteur_de_petits_fils++){
										Node petit_fils =  petits_enfants.item(compteur_de_petits_fils);
										
										if (petit_fils.getNodeType() == Node.ELEMENT_NODE  && petit_fils.getNodeName().contains("value-of") ) {
											Element element_petit_fils = (Element)petit_fils;
											
											String champ_recherche = element_petit_fils.getAttribute("select");
											champ_recherche = champ_recherche.toUpperCase();
											
											
											NodeList champs = fichier_xml.getDocumentElement().getElementsByTagName(champ_recherche);
											
											for (int compteur_de_champs = 0; compteur_de_champs < champs.getLength(); compteur_de_champs++) {
												
												Element element_final = (Element)champs.item(compteur_de_champs);
												
												champs.item(compteur_de_champs).setTextContent(reponses.get(compteur_de_reponse));
												compteur_de_reponse++;
												
												
											}
											
										}
									}
									
								}
							}
						}
					}
					else {
						
						
if(element.getAttribute("value-of")!=""){
							
						}
						else{
							NodeList enfants = node.getChildNodes();
							for(int compteur_de_fils = 0;compteur_de_fils<enfants.getLength();compteur_de_fils++){
								Node fils =  enfants.item(compteur_de_fils);
								
								if (fils.getNodeType() == Node.ELEMENT_NODE  && fils.getNodeName().contains("attribute") ) {
									NodeList petits_enfants = fils.getChildNodes();
									for(int compteur_de_petits_fils = 0;compteur_de_petits_fils<petits_enfants.getLength();compteur_de_petits_fils++){
										Node petit_fils =  petits_enfants.item(compteur_de_petits_fils);
										
										if (petit_fils.getNodeType() == Node.ELEMENT_NODE  && petit_fils.getNodeName().contains("value-of") ) {
											Element element_petit_fils = (Element)petit_fils;
											
											String champ_recherche = element_petit_fils.getAttribute("select");
											champ_recherche = champ_recherche.toUpperCase();
											
											
											NodeList champs = fichier_xml.getDocumentElement().getElementsByTagName(champ_recherche);
											
											for (int compteur_de_champs = 0; compteur_de_champs < champs.getLength(); compteur_de_champs++) {
												
												Element element_final = (Element)champs.item(compteur_de_champs);
												
												champs.item(compteur_de_champs).setTextContent(reponses.get(compteur_de_reponse));
												compteur_de_reponse++;
												
												
											}
											
										}
									}
									
								}
							}
						}
						
					}
					
				}

			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(fichier_xml);
	        StreamResult result = new StreamResult(new File(xml));
	        transformer.transform(source, result);
			

		} catch (ParserConfigurationException e) {
			System.out.println("Exception");
			e.printStackTrace();  
		}
		catch (SAXException e) {
			System.out.println("Exception 2 ");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("Exception 3");
			e.printStackTrace();

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}





}